package xyz.bluspring.kilt.loader.remap

import net.fabricmc.loader.api.FabricLoader
import net.fabricmc.loader.impl.launch.FabricLauncherBase
import net.fabricmc.loader.impl.util.SystemProperties
import net.fabricmc.loader.impl.util.mappings.TinyRemapperMappingsHelper
import net.fabricmc.mapping.tree.TinyMappingFactory
import net.fabricmc.mapping.tree.TinyTree
import net.fabricmc.tinyremapper.IMappingProvider
import net.fabricmc.tinyremapper.NonClassCopyMode
import net.fabricmc.tinyremapper.OutputConsumerPath
import net.fabricmc.tinyremapper.TinyRemapper
import net.minecraftforge.srgutils.IMappingFile
import org.apache.commons.codec.digest.DigestUtils
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import xyz.bluspring.kilt.Kilt
import xyz.bluspring.kilt.loader.ForgeMod
import xyz.bluspring.kilt.loader.KiltLoader
import java.io.File
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.*
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.jar.JarFile
import java.util.stream.Collectors
import kotlin.io.path.name

object KiltRemapper {
    private val logger = Kilt.logger
    // This is created automatically using https://github.com/BluSpring/srg2intermediary
    private val srgIntermediaryTree = TinyMappingFactory.load(this::class.java.getResourceAsStream("/srg_intermediary.tiny")!!.bufferedReader())

    fun remapMods(modLoadingQueue: ConcurrentLinkedQueue<ForgeMod>, remappedModsDir: File): List<Exception> {
        val launcher = FabricLauncherBase.getLauncher()

        val srgMappedMinecraft = remapMinecraft()
        val gameClassPath = getGameClassPath()

        val exceptions = mutableListOf<Exception>()

        logger.info("Remapping Forge mods to Intermediary...")
        // SRG to Intermediary
        val remapperBuilder = createRemapper(createMappings(srgIntermediaryTree, "srg", "intermediary"))

        // First iteration, for normal mod loading.
        modLoadingQueue.forEach { mod ->
            try {
                remapMod(mod.modFile, remapperBuilder, mod, remappedModsDir, arrayOf(srgMappedMinecraft, *gameClassPath))
                logger.info("Remapped ${mod.modInfo.mod.displayName} (${mod.modInfo.mod.modId}) from SRG to Intermediary")
            } catch (e: Exception) {
                exceptions.add(e)
                e.printStackTrace()
            }
        }

        logger.info("Finished remapping mods to Intermediary!")

        // Second iteration, for developer environments.
        // This is so the mods actually use the proper class names.
        if (FabricLoader.getInstance().isDevelopmentEnvironment) {
            // Intermediary to Named
            val devRemapperBuilder = createRemapper(createMappings(
                launcher.mappingConfiguration.mappings,
                "intermediary",
                launcher.mappingConfiguration.targetNamespace
            ))

            logger.info("Remapping Forge mods from Intermediary to the \"${launcher.mappingConfiguration.targetNamespace}\" namespace...")

            modLoadingQueue.forEach { mod ->
                try {
                    remapMod(mod.remappedModFile, devRemapperBuilder, mod, remappedModsDir, gameClassPath)
                    logger.info("Remapped ${mod.modInfo.mod.displayName} (${mod.modInfo.mod.modId}) from Intermediary to \"${launcher.mappingConfiguration.targetNamespace}\" namespace")
                } catch (e: Exception) {
                    exceptions.add(e)
                    e.printStackTrace()
                }
            }

            logger.info("Finished remapping Forge mods from Intermediary to the \"${launcher.mappingConfiguration.targetNamespace}\" namespace!")
        }

        return exceptions
    }

    private fun remapMod(file: File, remapperBuilder: TinyRemapper.Builder, mod: ForgeMod, remappedModsDir: File, gameClassPath: Array<out Path>) {
        val hash = DigestUtils.md5Hex(file.inputStream())
        val remappedModFile = File(remappedModsDir, "$hash.jar")

        mod.remappedModFile = remappedModFile

        if (remappedModFile.exists())
            return

        // I should not have to rebuild this every single fucking time.
        val remapper = remapperBuilder.build()

        remapper.readClassPath(*gameClassPath)

        remapper.readInputs(file.toPath())
        val outputConsumer = OutputConsumerPath.Builder(remappedModFile.toPath()).apply {
            assumeArchive(true)
        }.build()

        outputConsumer.addNonClassFiles(file.toPath(), NonClassCopyMode.FIX_META_INF, remapper)
        remapper.apply(outputConsumer)

        remapper.finish()
        outputConsumer.close()
    }

    private fun getGameClassPath(): Array<out Path> {
        return if (!FabricLoader.getInstance().isDevelopmentEnvironment)
            arrayOf(FabricLoader.getInstance().objectShare.get("fabric-loader:inputGameJar") as Path)
        else
            mutableListOf<Path>().apply {
                val remapClasspathFile = System.getProperty(SystemProperties.REMAP_CLASSPATH_FILE)
                    ?: throw RuntimeException("No remapClasspathFile provided")

                val content = String(Files.readAllBytes(Paths.get(remapClasspathFile)), StandardCharsets.UTF_8)

                this.addAll(Arrays.stream(content.split(File.pathSeparator.toRegex()).dropLastWhile { it.isEmpty() }
                    .toTypedArray())
                    .map { first ->
                        Paths.get(first)
                    }
                    .collect(Collectors.toList()))
            }.toTypedArray()
    }

    private fun createMappings(tree: TinyTree, from: String, to: String): IMappingProvider {
        return IMappingProvider { out ->
            tree.classes.forEach { classDef ->
                out.acceptClass(classDef.getName(from), classDef.getName(to))

                classDef.fields.forEach { fieldDef ->
                    out.acceptField(IMappingProvider.Member(classDef.getName(from), fieldDef.getName(from), fieldDef.getDescriptor(to)), fieldDef.getName(to))
                }

                classDef.methods.forEach { methodDef ->
                    out.acceptMethod(IMappingProvider.Member(classDef.getName(from), methodDef.getName(from), methodDef.getDescriptor(to)), methodDef.getName(to))
                }
            }
        }
    }

    private fun createRemapper(provider: IMappingProvider): TinyRemapper.Builder {
        return TinyRemapper.newRemapper().apply {
            skipLocalVariableMapping(true)
            renameInvalidLocals(true)
            rebuildSourceFilenames(true)

            withMappings(provider)
        }
    }

    fun remapMinecraft(): Path {
        val launcher = FabricLauncherBase.getLauncher()
        val srgFile = File(KiltLoader.kiltCacheDir, "minecraft_${launcher.mappingConfiguration.gameVersion}-srg.jar")

        if (srgFile.exists())
            return srgFile.toPath()

        logger.info("Creating SRG-mapped Minecraft JAR for remapping Forge mods...")
        //val srgIntermediaryMappings = IMappingFile.load(this::class.java.getResourceAsStream("/srg_intermediary.tiny")!!)

        val minecraftPath = FabricLoader.getInstance().objectShare.get("fabric-loader:inputGameJar") as Path
        val intermediaryPath = if (FabricLoader.getInstance().isDevelopmentEnvironment) { // named
            /*val file = File(KiltLoader.kiltCacheDir, "minecraft_${launcher.mappingConfiguration.gameVersion}-intermediary.jar")

            // This is probably not required, because an Intermediary-mapped Minecraft
            // does exist. But I'm not sure how to get it, so this is easier.
            if (!file.exists()) {
                val intermediaryDevTree = launcher.mappingConfiguration.mappings
                val devRemapper = createRemapper(
                    createMappings(
                        intermediaryDevTree,
                        launcher.mappingConfiguration.targetNamespace,
                        "intermediary"
                    )
                ).build()

                devRemapper.readInputs(minecraftPath)
                val outputConsumer = OutputConsumerPath.Builder(file.toPath()).apply {
                    assumeArchive(true)
                }.build()

                devRemapper.apply(outputConsumer)

                devRemapper.finish()
                outputConsumer.close()

                logger.info("Remapped Minecraft from ${launcher.mappingConfiguration.targetNamespace} to Intermediary")
            }

            file.toPath()*/

            // I think this is the best bet I have to finding an Intermediary jar in dev
            getGameClassPath().first { it.name.contains("intermediary") }
        } else minecraftPath // intermediary, or well should be.

        val srgRemapper = createRemapper(createMappings(srgIntermediaryTree, "intermediary", "srg")).build()

        srgRemapper.readInputs(intermediaryPath)
        val outputConsumer = OutputConsumerPath.Builder(srgFile.toPath()).apply {
            assumeArchive(true)
        }.build()

        srgRemapper.apply(outputConsumer)

        srgRemapper.finish()
        outputConsumer.close()

        logger.info("Remapped Minecraft from Intermediary to SRG.")

        return srgFile.toPath()
    }
}