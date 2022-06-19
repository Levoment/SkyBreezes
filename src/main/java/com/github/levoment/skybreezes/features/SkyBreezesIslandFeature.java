package com.github.levoment.skybreezes.features;

import com.mojang.serialization.Codec;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.enums.ChestType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;
import com.github.levoment.skybreezes.*;

public class SkyBreezesIslandFeature extends Feature<DefaultFeatureConfig> {


    public SkyBreezesIslandFeature(Codec<DefaultFeatureConfig> configCodec) {
        super(configCodec);
    }


    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        Chunk chunk = world.getChunk(context.getOrigin());

        if (chunk.getStatus() == ChunkStatus.LIQUID_CARVERS) {
            // Get the start and end positions of the chunk
            int startX = chunk.getPos().getStartX();
            int startZ = chunk.getPos().getStartZ();

            boolean mainIsland = false;
            boolean endPortal = false;
            boolean cactusIslandChunk = false;
            boolean secondIsland = false;

            boolean chunksHaveSameValuesWithoutSigns = Math.abs(startX) == Math.abs(startZ);

            if (chunksHaveSameValuesWithoutSigns && (startX & 1023) == 0) {
                mainIsland = true;
            }

            if ((chunksHaveSameValuesWithoutSigns && ((startX % 1026) == 0) && startX != 0) || (startX == 32 && startZ == 32)) {
                System.out.println(startX);
                endPortal = true;
            }

            if (chunksHaveSameValuesWithoutSigns && (((startX % 1021) == 0 && startX != 0) || (startX == -48 && startZ == -48))) {
                cactusIslandChunk = true;
            }

            if (((startX % 1022) == 0 && (startZ & 1023) == 0 && startX != 0) || (startX == -32 && startZ == 0)) {
                secondIsland = true;
            }



            if (mainIsland) {
                // Build the spawn island
                for (int y = 0; y < 8; y++) {
                    for (int x = 0 + y; x < 17 - y - (y > 0 ? 1 : 0) ; x++) {
                        for (int z = 0 + y; z < 17 - y - (y > 0 ? 1 : 0) ; z++) {
                            if (y == 0) {
                                chunk.setBlockState(new BlockPos((startX + x), 70 - y, (startZ + z)), Blocks.GRASS_BLOCK.getDefaultState(), false);
                            } else if (y == 7) {
                                chunk.setBlockState(new BlockPos((startX + x), 70 - y, (startZ + z)), Blocks.BEDROCK.getDefaultState(), false);
                            } else {
                                chunk.setBlockState(new BlockPos((startX + x), 70 - y, (startZ + z)), Blocks.DIRT.getDefaultState(), false);
                            }
                        }
                    }
                }

                // Plant a tree
                chunk.setBlockState(new BlockPos(startX + 8, 71, startZ + 8), Blocks.OAK_SAPLING.getDefaultState(), false);
                // Place a chest
                world.setBlockState(new BlockPos(startX + 2, 71, startZ + 8), Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(Properties.CHEST_TYPE, ChestType.SINGLE), 3);
                // Try to get the chest entity
                BlockEntity chestBlockEntity = world.getBlockEntity(new BlockPos(startX + 2, 71, startZ + 8));
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
                }
            }

            if(endPortal) {
                // Spawn an End Portal nearby
                Block theBlock = Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH).getBlock();

                world.setBlockState(new BlockPos(startX + 7, 6, startZ + 6), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 3);
                world.setBlockState(new BlockPos(startX + 8, 6, startZ + 6), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 3);
                world.setBlockState(new BlockPos(startX + 9, 6, startZ + 6), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 3);

                world.setBlockState(new BlockPos(startX + 7, 6, startZ + 10), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH), 3);
                world.setBlockState(new BlockPos(startX + 8, 6, startZ + 10), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH), 3);
                world.setBlockState(new BlockPos(startX + 9, 6, startZ + 10), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH), 3);

                world.setBlockState(new BlockPos(startX + 6, 6, startZ + 7), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST), 3);
                world.setBlockState(new BlockPos(startX + 6, 6, startZ + 8), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST), 3);
                world.setBlockState(new BlockPos(startX + 6, 6, startZ + 9), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST), 3);

                world.setBlockState(new BlockPos(startX + 10, 6, startZ + 7), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST), 3);
                world.setBlockState(new BlockPos(startX + 10, 6, startZ + 8), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST), 3);
                world.setBlockState(new BlockPos(startX + 10, 6, startZ + 9), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST), 3);
            }

            if (cactusIslandChunk) {
                // Make an island with cactus on it
                int desertIslandStartX = startX ;
                int desertIslandStartZ = startZ;
                for (int x = 0; x <= 2; x++) {
                    for (int z = 0; z <= 2; z++) {
                        for (int y = 0; y <= 5; y++) {
                            world.setBlockState(new BlockPos(desertIslandStartX + x, 70 + y, desertIslandStartZ + z), Blocks.SAND.getDefaultState(), 0);
                        }
                    }
                }
                world.setBlockState(new BlockPos(desertIslandStartX, 70 + 6, desertIslandStartZ), Blocks.CACTUS.getDefaultState(), 0);
            }

            if (secondIsland) {
                // Make an island with different saplings on it
                for (int y = 0; y < 8; y++) {
                    for (int x = 0 + y; x < 17 - y - (y > 0 ? 1 : 0) ; x++) {
                        for (int z = 0 + y; z < 17 - y - (y > 0 ? 1 : 0) ; z++) {
                            if (y == 0) {
                                world.setBlockState(new BlockPos((startX + x), 70 - y, (startZ + z)), Blocks.GRASS_BLOCK.getDefaultState(), 0);
                            } else {
                                world.setBlockState(new BlockPos((startX + x), 70 - y, (startZ + z)), Blocks.DIRT.getDefaultState(), 0);
                            }

                        }
                    }
                }
                // Plant an acacia tree
                world.setBlockState(new BlockPos(startX, 71, startZ), Blocks.ACACIA_SAPLING.getDefaultState(), 3);
                // Plant a birch tree
                world.setBlockState(new BlockPos(startX + 16, 71, startZ), Blocks.BIRCH_SAPLING.getDefaultState(), 3);
                // Plant a spruce tree
                world.setBlockState(new BlockPos(startX + 16, 71, startZ + 16), Blocks.SPRUCE_SAPLING.getDefaultState(), 3);

                // Plant a Dark oak tree
                world.setBlockState(new BlockPos(startX, 71, startZ + 16), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
                world.setBlockState(new BlockPos(startX, 71, startZ + 15), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
                world.setBlockState(new BlockPos(startX + 1, 71, startZ + 16), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
                world.setBlockState(new BlockPos(startX + 1, 71, startZ + 15), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);

                // Plant a jungle tree
                world.setBlockState(new BlockPos(startX + 8, 71, startZ + 8), Blocks.JUNGLE_SAPLING.getDefaultState(), 3);
                world.setBlockState(new BlockPos(startX + 9, 71, startZ + 8), Blocks.JUNGLE_SAPLING.getDefaultState(), 3);
                world.setBlockState(new BlockPos(startX + 8, 71, startZ + 9), Blocks.JUNGLE_SAPLING.getDefaultState(), 3);
                world.setBlockState(new BlockPos(startX + 9, 71, startZ + 9), Blocks.JUNGLE_SAPLING.getDefaultState(), 3);

                // Place a chest
                world.setBlockState(new BlockPos(startX + 2, 71, startZ + 4), Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(Properties.CHEST_TYPE, ChestType.SINGLE), 3);
                // Try to get the chest entity
                BlockEntity chestWithSeedsAndPlants = world.getBlockEntity(new BlockPos(startX + 2, 71, startZ + 4));
                // If the the entity is not null and is of type ChestBlockEntity
                if (chestWithSeedsAndPlants != null) {
                    // Place sugar cane
                    ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(1, new ItemStack(Items.SUGAR_CANE));
                    // Place kelp
                    ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(2, new ItemStack(Items.KELP));
                    // Place Watermelon
                    ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(6, new ItemStack(Items.MELON_SEEDS));
                    // Place Pumpkin
                    ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(7, new ItemStack(Items.PUMPKIN_SEEDS));
                    // Place cocoa beans
                    ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(8, new ItemStack(Items.COCOA_BEANS));
                    // Place one sapling of each tree in case they don't drop any saplings
                    ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(9, new ItemStack(Items.ACACIA_SAPLING));
                    ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(12, new ItemStack(Items.BIRCH_SAPLING));
                    ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(13, new ItemStack(Items.SPRUCE_SAPLING));
                    ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(19, new ItemStack(Items.DARK_OAK_SAPLING));
                    ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(26, new ItemStack(Items.JUNGLE_SAPLING));

                }
            }
        }


//        // If the chunk can be divided by 1024
//        if ((chunk.getPos().getStartX() % 1024 == 0) && (chunk.getPos().getStartZ() % 1024 == 0)) {
//            // Get the start and end positions of the chunk
//            int startX = chunk.getPos().getStartX();
//            int startZ = chunk.getPos().getStartZ();
//
//            // Build the spawn island
//            for (int y = 0; y < 8; y++) {
//                for (int x = 0 + y; x < 17 - y - (y > 0 ? 1 : 0) ; x++) {
//                    for (int z = 0 + y; z < 17 - y - (y > 0 ? 1 : 0) ; z++) {
//                        if (y == 0) {
//                            chunk.setBlockState(new BlockPos((startX + x), 70 - y, (startZ + z)), Blocks.GRASS_BLOCK.getDefaultState(), false);
//                        } else if (y == 7) {
//                            chunk.setBlockState(new BlockPos((startX + x), 70 - y, (startZ + z)), Blocks.BEDROCK.getDefaultState(), false);
//                        } else {
//                            chunk.setBlockState(new BlockPos((startX + x), 70 - y, (startZ + z)), Blocks.DIRT.getDefaultState(), false);
//                        }
//                    }
//                }
//            }
//
//            // Plant a tree
//            chunk.setBlockState(new BlockPos(startX + 8, 71, startZ + 8), Blocks.OAK_SAPLING.getDefaultState(), false);
//            // Place a chest
//            world.setBlockState(new BlockPos(startX + 2, 71, startZ + 8), Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(Properties.CHEST_TYPE, ChestType.SINGLE), 3);
//            // Try to get the chest entity
//            BlockEntity chestBlockEntity = world.getBlockEntity(new BlockPos(startX + 2, 71, startZ + 8));
//            // If the the entity is not null and is of type ChestBlockEntity
//            if (chestBlockEntity != null) {
//                // Place 2 water buckets in the chest
//                ((ChestBlockEntity)chestBlockEntity).setStack(0, new ItemStack(Items.WATER_BUCKET));
//                ((ChestBlockEntity)chestBlockEntity).setStack(1, new ItemStack(Items.WATER_BUCKET));
//                // Place 2 lava buckets in the chest
//                ((ChestBlockEntity)chestBlockEntity).setStack(2, new ItemStack(Items.LAVA_BUCKET));
//                ((ChestBlockEntity)chestBlockEntity).setStack(3, new ItemStack(Items.LAVA_BUCKET));
//                // Place 32 of bonemeal in the chest
//                ((ChestBlockEntity)chestBlockEntity).setStack(4, new ItemStack(Items.BONE_MEAL, 32));
//                // Place another sapling in case the tree doesn't drop any saplings
//                ((ChestBlockEntity)chestBlockEntity).setStack(5, new ItemStack(Items.OAK_SAPLING));
//            }
//
//            // Spawn an End Portal nearby
//            Block theBlock = Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH).getBlock();
//
//            world.setBlockState(new BlockPos(startX + 48, 6, startZ + 48), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 3);
//            world.setBlockState(new BlockPos(startX + 49, 6, startZ + 48), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 3);
//            world.setBlockState(new BlockPos(startX + 50, 6, startZ + 48), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 3);
//
//            world.setBlockState(new BlockPos(startX + 51, 6, startZ + 49), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST), 3);
//            world.setBlockState(new BlockPos(startX + 51, 6, startZ + 50), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST), 3);
//            world.setBlockState(new BlockPos(startX + 51, 6, startZ + 51), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST), 3);
//
//            world.setBlockState(new BlockPos(startX + 47, 6, startZ + 49), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST), 3);
//            world.setBlockState(new BlockPos(startX + 47, 6, startZ + 50), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST), 3);
//            world.setBlockState(new BlockPos(startX + 47, 6, startZ + 51), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST), 3);
//
//            world.setBlockState(new BlockPos(startX + 48, 6, startZ + 52), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH), 3);
//            world.setBlockState(new BlockPos(startX + 49, 6, startZ + 52), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH), 3);
//            world.setBlockState(new BlockPos(startX + 50, 6, startZ + 52), Blocks.END_PORTAL_FRAME.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH), 3);
//
//            // Make an island with cactus on it
//            int desertIslandStartX = startX - 48;
//            int desertIslandStartZ = startZ - 48;
//            for (int x = 0; x <= 2; x++) {
//                for (int z = 0; z <= 2; z++) {
//                    for (int y = 0; y <= 5; y++) {
//                        world.setBlockState(new BlockPos(desertIslandStartX + x, 70 + y, desertIslandStartZ + z), Blocks.SAND.getDefaultState(), 0);
//                    }
//                }
//            }
//            world.setBlockState(new BlockPos(desertIslandStartX, 70 + 6, desertIslandStartZ), Blocks.CACTUS.getDefaultState(), 0);
//
//            // Make an island with different saplings on it
//            int islandWithSaplingsStartX = startX - 48;
//            for (int y = 0; y < 8; y++) {
//                for (int x = 0 + y; x < 17 - y - (y > 0 ? 1 : 0) ; x++) {
//                    for (int z = 0 + y; z < 17 - y - (y > 0 ? 1 : 0) ; z++) {
//                        if (y == 0) {
//                            world.setBlockState(new BlockPos((islandWithSaplingsStartX + x), 70 - y, (startZ + z)), Blocks.GRASS_BLOCK.getDefaultState(), 0);
//                        } else {
//                            world.setBlockState(new BlockPos((islandWithSaplingsStartX + x), 70 - y, (startZ + z)), Blocks.DIRT.getDefaultState(), 0);
//                        }
//
//                    }
//                }
//            }
//            // Plant an acacia tree
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX, 71, startZ), Blocks.ACACIA_SAPLING.getDefaultState(), 3);
//            // Plant a birch tree
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 16, 71, startZ), Blocks.BIRCH_SAPLING.getDefaultState(), 3);
//            // Plant a spruce tree
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 16, 71, startZ + 16), Blocks.SPRUCE_SAPLING.getDefaultState(), 3);
//
//            // Plant a Dark oak tree
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX, 71, startZ + 16), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX, 71, startZ + 15), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 1, 71, startZ + 16), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 1, 71, startZ + 15), Blocks.DARK_OAK_SAPLING.getDefaultState(), 3);
//
//            // Plant a jungle tree
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 8, 71, startZ + 8), Blocks.JUNGLE_SAPLING.getDefaultState(), 3);
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 9, 71, startZ + 8), Blocks.JUNGLE_SAPLING.getDefaultState(), 3);
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 8, 71, startZ + 9), Blocks.JUNGLE_SAPLING.getDefaultState(), 3);
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 9, 71, startZ + 9), Blocks.JUNGLE_SAPLING.getDefaultState(), 3);
//
//            // Place a chest
//            world.setBlockState(new BlockPos(islandWithSaplingsStartX + 2, 71, startZ + 4), Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH).with(Properties.CHEST_TYPE, ChestType.SINGLE), 3);
//            // Try to get the chest entity
//            BlockEntity chestWithSeedsAndPlants = world.getBlockEntity(new BlockPos(islandWithSaplingsStartX + 2, 71, startZ + 4));
//            // If the the entity is not null and is of type ChestBlockEntity
//            if (chestWithSeedsAndPlants != null) {
//                // Place sugar cane
//                ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(1, new ItemStack(Items.SUGAR_CANE));
//                // Place kelp
//                ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(2, new ItemStack(Items.KELP));
//                // Place Watermelon
//                ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(6, new ItemStack(Items.MELON_SEEDS));
//                // Place Pumpkin
//                ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(7, new ItemStack(Items.PUMPKIN_SEEDS));
//                // Place cocoa beans
//                ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(8, new ItemStack(Items.COCOA_BEANS));
//                // Place one sapling of each tree in case they don't drop any saplings
//                ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(9, new ItemStack(Items.ACACIA_SAPLING));
//                ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(12, new ItemStack(Items.BIRCH_SAPLING));
//                ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(13, new ItemStack(Items.SPRUCE_SAPLING));
//                ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(19, new ItemStack(Items.DARK_OAK_SAPLING));
//                ((ChestBlockEntity)chestWithSeedsAndPlants).setStack(26, new ItemStack(Items.JUNGLE_SAPLING));
//
//            }
//        }

        return true;
    }
}
