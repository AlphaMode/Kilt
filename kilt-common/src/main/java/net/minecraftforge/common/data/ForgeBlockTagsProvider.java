/*
 * Copyright (c) Forge Development LLC and contributors
 * SPDX-License-Identifier: LGPL-2.1-only
 */

package net.minecraftforge.common.data;

import net.minecraftforge.common.Tags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Locale;
import java.util.function.Consumer;

public final class ForgeBlockTagsProvider extends BlockTagsProvider
{
    public ForgeBlockTagsProvider(DataGenerator gen, ExistingFileHelper existingFileHelper)
    {
        super(gen, "forge", existingFileHelper);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addTags()
    {
        Tags.Blocks.tag(Tags.Blocks.BARRELS).addTag(Tags.Blocks.BARRELS_WOODEN);
        Tags.Blocks.tag(Tags.Blocks.BARRELS_WOODEN).add(Blocks.BARREL);
        Tags.Blocks.tag(Tags.Blocks.BOOKSHELVES).add(Blocks.BOOKSHELF);
        Tags.Blocks.tag(Tags.Blocks.CHESTS).addTags(Tags.Blocks.CHESTS_ENDER, Tags.Blocks.CHESTS_TRAPPED, Tags.Blocks.CHESTS_WOODEN);
        Tags.Blocks.tag(Tags.Blocks.CHESTS_ENDER).add(Blocks.ENDER_CHEST);
        Tags.Blocks.tag(Tags.Blocks.CHESTS_TRAPPED).add(Blocks.TRAPPED_CHEST);
        Tags.Blocks.tag(Tags.Blocks.CHESTS_WOODEN).add(Blocks.CHEST, Blocks.TRAPPED_CHEST);
        Tags.Blocks.tag(Tags.Blocks.COBBLESTONE).addTags(Tags.Blocks.COBBLESTONE_NORMAL, Tags.Blocks.COBBLESTONE_INFESTED, Tags.Blocks.COBBLESTONE_MOSSY, Tags.Blocks.COBBLESTONE_DEEPSLATE);
        Tags.Blocks.tag(Tags.Blocks.COBBLESTONE_NORMAL).add(Blocks.COBBLESTONE);
        Tags.Blocks.tag(Tags.Blocks.COBBLESTONE_INFESTED).add(Blocks.INFESTED_COBBLESTONE);
        Tags.Blocks.tag(Tags.Blocks.COBBLESTONE_MOSSY).add(Blocks.MOSSY_COBBLESTONE);
        Tags.Blocks.tag(Tags.Blocks.COBBLESTONE_DEEPSLATE).add(Blocks.COBBLED_DEEPSLATE);
        Tags.Blocks.tag(Tags.Blocks.END_STONES).add(Blocks.END_STONE);
        Tags.Blocks.tag(Tags.Blocks.ENDERMAN_PLACE_ON_BLACKLIST);
        Tags.Blocks.tag(Tags.Blocks.FENCE_GATES).addTags(Tags.Blocks.FENCE_GATES_WOODEN);
        Tags.Blocks.tag(Tags.Blocks.FENCE_GATES_WOODEN).add(Blocks.OAK_FENCE_GATE, Blocks.SPRUCE_FENCE_GATE, Blocks.BIRCH_FENCE_GATE, Blocks.JUNGLE_FENCE_GATE, Blocks.ACACIA_FENCE_GATE, Blocks.DARK_OAK_FENCE_GATE, Blocks.CRIMSON_FENCE_GATE, Blocks.WARPED_FENCE_GATE, Blocks.MANGROVE_FENCE_GATE);
        Tags.Blocks.tag(Tags.Blocks.FENCES).addTags(Tags.Blocks.FENCES_NETHER_BRICK, Tags.Blocks.FENCES_WOODEN);
        Tags.Blocks.tag(Tags.Blocks.FENCES_NETHER_BRICK).add(Blocks.NETHER_BRICK_FENCE);
        Tags.Blocks.tag(Tags.Blocks.FENCES_WOODEN).addTag(BlockTags.WOODEN_FENCES);
        Tags.Blocks.tag(Tags.Blocks.GLASS).addTags(Tags.Blocks.GLASS_COLORLESS, Tags.Blocks.STAINED_GLASS, Tags.Blocks.GLASS_TINTED);
        Tags.Blocks.tag(Tags.Blocks.GLASS_COLORLESS).add(Blocks.GLASS);
        Tags.Blocks.tag(Tags.Blocks.GLASS_SILICA).add(Blocks.GLASS, Blocks.BLACK_STAINED_GLASS, Blocks.BLUE_STAINED_GLASS, Blocks.BROWN_STAINED_GLASS, Blocks.CYAN_STAINED_GLASS, Blocks.GRAY_STAINED_GLASS, Blocks.GREEN_STAINED_GLASS, Blocks.LIGHT_BLUE_STAINED_GLASS, Blocks.LIGHT_GRAY_STAINED_GLASS, Blocks.LIME_STAINED_GLASS, Blocks.MAGENTA_STAINED_GLASS, Blocks.ORANGE_STAINED_GLASS, Blocks.PINK_STAINED_GLASS, Blocks.PURPLE_STAINED_GLASS, Blocks.RED_STAINED_GLASS, Blocks.WHITE_STAINED_GLASS, Blocks.YELLOW_STAINED_GLASS);
        Tags.Blocks.tag(Tags.Blocks.GLASS_TINTED).add(Blocks.TINTED_GLASS);
        addColored(Tags.Blocks.tag(Tags.Blocks.STAINED_GLASS)::add, Tags.Blocks.GLASS, "{color}_stained_glass");
        Tags.Blocks.tag(Tags.Blocks.GLASS_PANES).addTags(Tags.Blocks.GLASS_PANES_COLORLESS, Tags.Blocks.STAINED_GLASS_PANES);
        Tags.Blocks.tag(Tags.Blocks.GLASS_PANES_COLORLESS).add(Blocks.GLASS_PANE);
        addColored(Tags.Blocks.tag(Tags.Blocks.STAINED_GLASS_PANES)::add, Tags.Blocks.GLASS_PANES, "{color}_stained_glass_pane");
        Tags.Blocks.tag(Tags.Blocks.GRAVEL).add(Blocks.GRAVEL);
        Tags.Blocks.tag(Tags.Blocks.NETHERRACK).add(Blocks.NETHERRACK);
        Tags.Blocks.tag(Tags.Blocks.OBSIDIAN).add(Blocks.OBSIDIAN);
        Tags.Blocks.tag(Tags.Blocks.ORE_BEARING_GROUND_DEEPSLATE).add(Blocks.DEEPSLATE);
        Tags.Blocks.tag(Tags.Blocks.ORE_BEARING_GROUND_NETHERRACK).add(Blocks.NETHERRACK);
        Tags.Blocks.tag(Tags.Blocks.ORE_BEARING_GROUND_STONE).add(Blocks.STONE);
        Tags.Blocks.tag(Tags.Blocks.ORE_RATES_DENSE).add(Blocks.COPPER_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE);
        Tags.Blocks.tag(Tags.Blocks.ORE_RATES_SINGULAR).add(Blocks.ANCIENT_DEBRIS, Blocks.COAL_ORE, Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.NETHER_QUARTZ_ORE);
        Tags.Blocks.tag(Tags.Blocks.ORE_RATES_SPARSE).add(Blocks.NETHER_GOLD_ORE);
        Tags.Blocks.tag(Tags.Blocks.ORES).addTags(Tags.Blocks.ORES_COAL, Tags.Blocks.ORES_COPPER, Tags.Blocks.ORES_DIAMOND, Tags.Blocks.ORES_EMERALD, Tags.Blocks.ORES_GOLD, Tags.Blocks.ORES_IRON, Tags.Blocks.ORES_LAPIS, Tags.Blocks.ORES_REDSTONE, Tags.Blocks.ORES_QUARTZ, Tags.Blocks.ORES_NETHERITE_SCRAP);
        Tags.Blocks.tag(Tags.Blocks.ORES_COAL).addTag(BlockTags.COAL_ORES);
        Tags.Blocks.tag(Tags.Blocks.ORES_COPPER).addTag(BlockTags.COPPER_ORES);
        Tags.Blocks.tag(Tags.Blocks.ORES_DIAMOND).addTag(BlockTags.DIAMOND_ORES);
        Tags.Blocks.tag(Tags.Blocks.ORES_EMERALD).addTag(BlockTags.EMERALD_ORES);
        Tags.Blocks.tag(Tags.Blocks.ORES_GOLD).addTag(BlockTags.GOLD_ORES);
        Tags.Blocks.tag(Tags.Blocks.ORES_IRON).addTag(BlockTags.IRON_ORES);
        Tags.Blocks.tag(Tags.Blocks.ORES_LAPIS).addTag(BlockTags.LAPIS_ORES);
        Tags.Blocks.tag(Tags.Blocks.ORES_QUARTZ).add(Blocks.NETHER_QUARTZ_ORE);
        Tags.Blocks.tag(Tags.Blocks.ORES_REDSTONE).addTag(BlockTags.REDSTONE_ORES);
        Tags.Blocks.tag(Tags.Blocks.ORES_NETHERITE_SCRAP).add(Blocks.ANCIENT_DEBRIS);
        Tags.Blocks.tag(Tags.Blocks.ORES_IN_GROUND_DEEPSLATE).add(Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_COPPER_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_REDSTONE_ORE);
        Tags.Blocks.tag(Tags.Blocks.ORES_IN_GROUND_NETHERRACK).add(Blocks.NETHER_GOLD_ORE, Blocks.NETHER_QUARTZ_ORE);
        Tags.Blocks.tag(Tags.Blocks.ORES_IN_GROUND_STONE).add(Blocks.COAL_ORE, Blocks.COPPER_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE, Blocks.GOLD_ORE, Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE);
        Tags.Blocks.tag(Tags.Blocks.SAND).addTags(Tags.Blocks.SAND_COLORLESS, Tags.Blocks.SAND_RED);
        Tags.Blocks.tag(Tags.Blocks.SAND_COLORLESS).add(Blocks.SAND);
        Tags.Blocks.tag(Tags.Blocks.SAND_RED).add(Blocks.RED_SAND);
        Tags.Blocks.tag(Tags.Blocks.SANDSTONE).add(Blocks.SANDSTONE, Blocks.CUT_SANDSTONE, Blocks.CHISELED_SANDSTONE, Blocks.SMOOTH_SANDSTONE, Blocks.RED_SANDSTONE, Blocks.CUT_RED_SANDSTONE, Blocks.CHISELED_RED_SANDSTONE, Blocks.SMOOTH_RED_SANDSTONE);
        Tags.Blocks.tag(Tags.Blocks.STONE).add(Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.INFESTED_STONE, Blocks.STONE, Blocks.POLISHED_ANDESITE, Blocks.POLISHED_DIORITE, Blocks.POLISHED_GRANITE, Blocks.DEEPSLATE, Blocks.POLISHED_DEEPSLATE, Blocks.INFESTED_DEEPSLATE, Blocks.TUFF);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS).addTags(Tags.Blocks.STORAGE_BLOCKS_AMETHYST, Tags.Blocks.STORAGE_BLOCKS_COAL, Tags.Blocks.STORAGE_BLOCKS_COPPER, Tags.Blocks.STORAGE_BLOCKS_DIAMOND, Tags.Blocks.STORAGE_BLOCKS_EMERALD, Tags.Blocks.STORAGE_BLOCKS_GOLD, Tags.Blocks.STORAGE_BLOCKS_IRON, Tags.Blocks.STORAGE_BLOCKS_LAPIS, Tags.Blocks.STORAGE_BLOCKS_QUARTZ, Tags.Blocks.STORAGE_BLOCKS_RAW_COPPER, Tags.Blocks.STORAGE_BLOCKS_RAW_GOLD, Tags.Blocks.STORAGE_BLOCKS_RAW_IRON, Tags.Blocks.STORAGE_BLOCKS_REDSTONE, Tags.Blocks.STORAGE_BLOCKS_NETHERITE);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_AMETHYST).add(Blocks.AMETHYST_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_COAL).add(Blocks.COAL_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_COPPER).add(Blocks.COPPER_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_DIAMOND).add(Blocks.DIAMOND_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_EMERALD).add(Blocks.EMERALD_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_GOLD).add(Blocks.GOLD_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_IRON).add(Blocks.IRON_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_LAPIS).add(Blocks.LAPIS_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_QUARTZ).add(Blocks.QUARTZ_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_RAW_COPPER).add(Blocks.RAW_COPPER_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_RAW_GOLD).add(Blocks.RAW_GOLD_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_RAW_IRON).add(Blocks.RAW_IRON_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_REDSTONE).add(Blocks.REDSTONE_BLOCK);
        Tags.Blocks.tag(Tags.Blocks.STORAGE_BLOCKS_NETHERITE).add(Blocks.NETHERITE_BLOCK);
    }

    private void addColored(Consumer<Block> consumer, TagKey<Block> group, String pattern)
    {
        String prefix = group.location().getPath().toUpperCase(Locale.ENGLISH) + '_';
        for (DyeColor color  : DyeColor.values())
        {
            ResourceLocation key = new ResourceLocation("minecraft", pattern.replace("{color}",  color.getName()));
            TagKey<Block> tag = getForgeTag(prefix + color.getName());
            Block block = ForgeRegistries.BLOCKS.getValue(key);
            if (block == null || block  == Blocks.AIR)
                throw new IllegalStateException("Unknown vanilla block: " + key.toString());
            Tags.Blocks.tag(tag).add(block);
            consumer.accept(block);
        }
    }

    @SuppressWarnings("unchecked")
    private TagKey<Block> getForgeTag(String name)
    {
        try
        {
            name = name.toUpperCase(Locale.ENGLISH);
            return (TagKey<Block>) Tags.Blocks.class.getDeclaredField(name).get(null);
        }
        catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e)
        {
            throw new IllegalStateException(Tags.Blocks.class.getName() + " is missing tag name: " + name);
        }
    }

    @Override
    public String getName()
    {
        return "Forge Block Tags";
    }
}
