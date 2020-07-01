package com.github.levoment.skybreezes.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;

public class SkyBreezesIslandFeature extends Feature<DefaultFeatureConfig> {


    public SkyBreezesIslandFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }


    @Override
    public boolean generate(ServerWorldAccess world, StructureAccessor structureAccessor, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        Chunk chunk = world.getChunk(pos);

        // If the chunk can be divided by 1024
        if ((chunk.getPos().getStartX() % 1024 == 0) && (chunk.getPos().getStartZ() % 1024 == 0)) {
            // Get the start and end positions of the chunk
            int startX = chunk.getPos().getStartX();
            int startZ = chunk.getPos().getStartZ();
            // Set the base blocks of the island
            chunk.setBlockState(new BlockPos(chunk.getPos().getStartX() + 7, 69, chunk.getPos().getStartX() + 6), Blocks.BEDROCK.getDefaultState(), false);
            chunk.setBlockState(new BlockPos(chunk.getPos().getStartX() + 8, 69, chunk.getPos().getStartX() + 7), Blocks.BEDROCK.getDefaultState(), false);
            chunk.setBlockState(new BlockPos(chunk.getPos().getStartX() + 6, 69, chunk.getPos().getStartX() + 7), Blocks.BEDROCK.getDefaultState(), false);
            chunk.setBlockState(new BlockPos(chunk.getPos().getStartX() + 7, 69, chunk.getPos().getStartX() + 8), Blocks.BEDROCK.getDefaultState(), false);
            // Build the spawn island
            for (int x = 0; x <= 16; x++) {
                for (int z = 0; z <= 16; z++) {
                    for (int y = 0; y <= 2; y++) {
                        if (y == 2) {
                            chunk.setBlockState(new BlockPos(startX + x,  70 + y, startZ + z), Blocks.GRASS_BLOCK.getDefaultState(), false);
                        } else {
                            chunk.setBlockState(new BlockPos(startX + x, 70 + y, startZ + z), Blocks.DIRT.getDefaultState(), false);
                        }
                    }
                }
            }

            // Plant a tree
            chunk.setBlockState(new BlockPos(startX + 8, 73, startZ + 8), Blocks.OAK_SAPLING.getDefaultState(), false);
            // Place a chest
            world.setBlockState(new BlockPos(startX + 2, 73, startZ + 8), Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(Properties.CHEST_TYPE, ChestType.SINGLE), 3);
            // Try to get the chest entity
            BlockEntity chestBlockEntity = world.getBlockEntity(new BlockPos(startX + 2, 73, startZ + 8));
            // If the the entity is not null and is of type ChestBlockEntity
            if (chestBlockEntity != null) {
                // Place 2 water buckets in the chest
                ((ChestBlockEntity)chestBlockEntity).setStack(0, new ItemStack(Items.WATER_BUCKET));
                ((ChestBlockEntity)chestBlockEntity).setStack(1, new ItemStack(Items.WATER_BUCKET));
                // Place 2 lava buckets in the chest
                ((ChestBlockEntity)chestBlockEntity).setStack(2, new ItemStack(Items.LAVA_BUCKET));
                ((ChestBlockEntity)chestBlockEntity).setStack(3, new ItemStack(Items.LAVA_BUCKET));
                // Place 32 of bonemeal in the chest
                ((ChestBlockEntity)chestBlockEntity).setStack(4, new ItemStack(Items.BONE_MEAL, 32));
                // Place another sapling in case the tree doesn't drop any saplings
                ((ChestBlockEntity)chestBlockEntity).setStack(5, new ItemStack(Items.OAK_SAPLING));
                // Place sugar cane
                ((ChestBlockEntity)chestBlockEntity).setStack(6, new ItemStack(Items.SUGAR_CANE));
                // Place kelp
                ((ChestBlockEntity)chestBlockEntity).setStack(7, new ItemStack(Items.KELP));
                // Place bamboo
                ((ChestBlockEntity)chestBlockEntity).setStack(8, new ItemStack(Items.BAMBOO));
                // Place Watermelon
                ((ChestBlockEntity)chestBlockEntity).setStack(9, new ItemStack(Items.MELON_SEEDS));
                // Place Pumpkin
                ((ChestBlockEntity)chestBlockEntity).setStack(10, new ItemStack(Items.PUMPKIN_SEEDS));
            }

            // Spawn an End Portal nearby
            world.setBlockState(new BlockPos(startX + 48, 6, startZ + 48), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 3);
            world.setBlockState(new BlockPos(startX + 49, 6, startZ + 48), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 3);
            world.setBlockState(new BlockPos(startX + 50, 6, startZ + 48), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 3);

            world.setBlockState(new BlockPos(startX + 51, 6, startZ + 49), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST), 3);
            world.setBlockState(new BlockPos(startX + 51, 6, startZ + 50), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST), 3);
            world.setBlockState(new BlockPos(startX + 51, 6, startZ + 51), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST), 3);

            world.setBlockState(new BlockPos(startX + 47, 6, startZ + 49), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST), 3);
            world.setBlockState(new BlockPos(startX + 47, 6, startZ + 50), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST), 3);
            world.setBlockState(new BlockPos(startX + 47, 6, startZ + 51), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST), 3);

            world.setBlockState(new BlockPos(startX + 48, 6, startZ + 52), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH), 3);
            world.setBlockState(new BlockPos(startX + 49, 6, startZ + 52), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH), 3);
            world.setBlockState(new BlockPos(startX + 50, 6, startZ + 52), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH), 3);

            // Spawn a nether portal nearby
            world.setBlockState(new BlockPos(startX + 8, 40, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 9, 40, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 10, 40, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 11, 40, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);

            world.setBlockState(new BlockPos(startX + 9, 40, startZ - 47), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 10, 40, startZ - 47), Blocks.OBSIDIAN.getDefaultState(), 3);

            world.setBlockState(new BlockPos(startX + 9, 40, startZ - 49), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 10, 40, startZ - 49), Blocks.OBSIDIAN.getDefaultState(), 3);

            world.setBlockState(new BlockPos(startX + 8, 41, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 8, 42, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 8, 43, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 8, 44, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);


            world.setBlockState(new BlockPos(startX + 11, 41, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 11, 42, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 11, 43, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 11, 44, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);

            world.setBlockState(new BlockPos(startX + 8, 45, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 9, 45, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 10, 45, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 11, 45, startZ - 48), Blocks.OBSIDIAN.getDefaultState(), 3);

            world.setBlockState(new BlockPos(startX + 9, 41, startZ - 48), Blocks.NETHER_PORTAL.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 10, 41, startZ - 48), Blocks.NETHER_PORTAL.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 9, 42, startZ - 48), Blocks.NETHER_PORTAL.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 10, 42, startZ - 48), Blocks.NETHER_PORTAL.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 9, 43, startZ - 48), Blocks.NETHER_PORTAL.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 10, 43, startZ - 48), Blocks.NETHER_PORTAL.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 9, 44, startZ - 48), Blocks.NETHER_PORTAL.getDefaultState(), 3);
            world.setBlockState(new BlockPos(startX + 10, 44, startZ - 48), Blocks.NETHER_PORTAL.getDefaultState(), 3);

            // Make an island with cactus on it
            int desertIslandStartX = startX - 48;
            int desertIslandStartZ = startZ - 48;
            for (int x = 0; x <= 2; x++) {
                for (int z = 0; z <= 2; z++) {
                    for (int y = 0; y <= 5; y++) {
                        world.setBlockState(new BlockPos(desertIslandStartX + x, 70 + y, desertIslandStartZ + z), Blocks.SAND.getDefaultState(), 0);
                    }
                }
            }
            world.setBlockState(new BlockPos(desertIslandStartX, 70 + 6, desertIslandStartZ), Blocks.CACTUS.getDefaultState(), 0);

            // Make an island with different saplings on it
            int islandWithSaplingsStartX = startX - 48;
            for (int x = 0; x <= 16; x++) {
                for (int z = 0; z <= 16; z++) {
                    for (int y = 0; y <= 1; y++) {
                        if (y == 1) {
                            world.setBlockState(new BlockPos(islandWithSaplingsStartX + x, 71 + y, startZ + z), Blocks.GRASS_BLOCK.getDefaultState(), 0);
                        } else {
                            world.setBlockState(new BlockPos(islandWithSaplingsStartX + x, 71 + y, startZ + z), Blocks.DIRT.getDefaultState(), 0);
                        }
                    }
                }
            }
            // Plant an acacia tree
            world.setBlockState(new BlockPos(islandWithSaplingsStartX, 73, startZ), Blocks.ACACIA_SAPLING.getDefaultState(), 3);
            // Plant a birch tree
            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 16, 73, startZ), Blocks.BIRCH_SAPLING.getDefaultState(), 3);
            // Plant a spruce tree
            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 16, 73, startZ + 16), Blocks.SPRUCE_SAPLING.getDefaultState(), 3);

            // Plant a Dark oak tree
            world.setBlockState(new BlockPos(islandWithSaplingsStartX, 73, startZ + 16), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
            world.setBlockState(new BlockPos(islandWithSaplingsStartX, 73, startZ + 15), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 1, 73, startZ + 16), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 1, 73, startZ + 15), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);

            // Plant a jungle tree
            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 8, 73, startZ + 8), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 9, 73, startZ + 8), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 8, 73, startZ + 9), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 9, 73, startZ + 9), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
        }

        return true;
    }


}
