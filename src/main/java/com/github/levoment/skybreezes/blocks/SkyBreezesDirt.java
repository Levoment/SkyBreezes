package com.github.levoment.skybreezes.blocks;


import com.github.levoment.skybreezes.SkyBreezes;
import com.github.levoment.skybreezes.components.SkyBreezesComponents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Map;
import java.util.Objects;

public class SkyBreezesDirt extends Block {

    public SkyBreezesDirt() {
        super(FabricBlockSettings.of(Material.SOIL).hardness(0.5f).sounds(BlockSoundGroup.GRASS));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if (!world.isClient()) {
            ServerWorld destination = (ServerWorld)world;
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
            // If we are not on our sky breezes dimension
            if (!destination.getRegistryKey().equals(SkyBreezes.skyBreezesDimensionRegistryKey)) {
                // Try to get the sky breezes dimension world
                try {
                    ServerWorld skyBreezesDimensionWorld;
                    skyBreezesDimensionWorld = Objects.requireNonNull(entity.getServer()).getWorld(SkyBreezes.skyBreezesDimensionRegistryKey);

                    BlockPos playerSpawnPoint;
                    // Get the player spawn point dimension
                    RegistryKey<World> playerSpawnDimensionRegistryKey = serverPlayerEntity.getSpawnPointDimension();
                    if (!playerSpawnDimensionRegistryKey.equals(SkyBreezes.skyBreezesDimensionRegistryKey)) {
                        // Get a map of SpawnPoints in the SkyBreezes World
                        Map<String, BlockPos> spawnPointsInSkyBreezes = SkyBreezesComponents.getWorldSpawnPositions(skyBreezesDimensionWorld);
                        // Try to get the island spawn point in the Sky Breezes dimension
                        playerSpawnPoint = spawnPointsInSkyBreezes.get(serverPlayerEntity.getUuidAsString());
                        // If the player doesn't have an island set to them
                        if (playerSpawnPoint == null) {
                            // Give the player an island
                            SkyBreezesComponents.setWorldSpawnPointForPlayer(skyBreezesDimensionWorld, serverPlayerEntity);
                            // Get the list of island spawn points in Sky Breezes again
                            spawnPointsInSkyBreezes = SkyBreezesComponents.getWorldSpawnPositions(skyBreezesDimensionWorld);
                            // Set the player spawn point to their island spawn point
                            playerSpawnPoint = spawnPointsInSkyBreezes.get(serverPlayerEntity.getUuidAsString());
                        }
                    } else {
                        // Set the player spawn point variable to hold their spawn point in the SkyBreezes dimension
                        playerSpawnPoint = serverPlayerEntity.getSpawnPointPosition();
                    }

                    // Set the Sky Breezes dimension spawn point
                    skyBreezesDimensionWorld.setSpawnPos(new BlockPos(5, 73, 5), 0.0f);
                    // Teleport the player to the dimension spawn point or to their spawn point in Sky Breezes if it is set
                    serverPlayerEntity.teleport(skyBreezesDimensionWorld, playerSpawnPoint.getX(), playerSpawnPoint.getY(), playerSpawnPoint.getZ(), 0.0f, 0.0f);
                } catch (NullPointerException nullPointerException) {
                    // Show an error on the console or to the player
                }
            } else {
                // Try to get the overworld dimension world
                try {
                    ServerWorld overworld;
                    overworld = Objects.requireNonNull(serverPlayerEntity.getServer()).getOverworld();
                    // Get the spawn point of the overworld
                    BlockPos spawnPointPos = Objects.requireNonNull(overworld).getSpawnPos();
                    // Get the player spawn point dimension
//                    RegistryKey<World> playerSpawnDimensionRegistryKey = serverPlayerEntity.getSpawnPointDimension();
//                    if (playerSpawnDimensionRegistryKey.equals(RegistryKey.of(Registry.DIMENSION, new Identifier("minecraft", "overworld")))) {
//                        // Set the player spawn point position to their spawn point in the Overworld
//                        spawnPointPos = serverPlayerEntity.getSpawnPointPosition();
//                    }
                    // Teleport the player to the overworld spawn point or to their spawn point in the Overworld if it is set
                    serverPlayerEntity.teleport(overworld, spawnPointPos.getX(), spawnPointPos.getY(), spawnPointPos.getZ(), 0.0f, 0.0f);
                } catch (NullPointerException nullPointerException) {
                    // Show an error on the console or to the player
                }
            }

//            ServerWorld destination = (ServerWorld)world;
//            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)entity;
//            // If we are not on our sky breezes dimension
//            if (!destination.getRegistryKey().equals(SkyBreezes.skyBreezesDimensionRegistryKey)) {
//                // Try to get the sky breezes dimension world
//                try {
//                    ServerWorld skyBreezesDimensionWorld;
//                    skyBreezesDimensionWorld = Objects.requireNonNull(entity.getServer()).getWorld(SkyBreezes.skyBreezesDimensionRegistryKey);
//                    // Set the Sky Breezes dimension spawn point
//                    skyBreezesDimensionWorld.setSpawnPos(new BlockPos(5, 73, 5), 0.0f);
//                    // Get the spawn point of the Sky Breezes dimension world
//                    BlockPos spawnPointPos = skyBreezesDimensionWorld.getSpawnPos();
//                    System.out.println(spawnPointPos);
//                    // Teleport the player to the dimension spawn point. This is the Sky Breezes dimension world spawn point not the player spawn point in it
//                    serverPlayerEntity.teleport(skyBreezesDimensionWorld, spawnPointPos.getX(), spawnPointPos.getY(), spawnPointPos.getZ(), 0.0f, 0.0f);
//                } catch (NullPointerException nullPointerException) {
//                    // Show an error on the console or to the player
//                }
//            } else {
//                // Try to get the overworld dimension world
//                try {
//                    ServerWorld overworld;
//                    overworld = Objects.requireNonNull(serverPlayerEntity.getServer()).getOverworld();
//                    // Get the spawn point of the overworld
//                    BlockPos spawnPointPos = Objects.requireNonNull(overworld).getSpawnPos();
//                    // Teleport the player to the overworld spawn point. This is the overworld spawn point not the player spawn point in it
//                    serverPlayerEntity.teleport(overworld, spawnPointPos.getX(), spawnPointPos.getY(), spawnPointPos.getZ(), 0.0f, 0.0f);
//                } catch (NullPointerException nullPointerException) {
//                    // Show an error on the console or to the player
//                }
//            }
        }
    }
}
