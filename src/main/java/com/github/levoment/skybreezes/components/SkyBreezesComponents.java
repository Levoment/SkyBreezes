package com.github.levoment.skybreezes.components;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.world.WorldComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.world.WorldComponentInitializer;
import nerdhub.cardinal.components.api.util.RespawnCopyStrategy;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class SkyBreezesComponents implements WorldComponentInitializer, EntityComponentInitializer {
    // Key containing the WorldSpawnPosComponent
    public static final ComponentKey<WorldSpawnPosComponent> WORLD_SPAWN_POS_COMPONENT_KEY =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("skbrz:spawn_component"), WorldSpawnPosComponent.class);
    // Key containing the PlayerSpawnPosComponent
    public static final ComponentKey<PlayerSpawnPosComponent> PLAYER_SPAWN_POS_COMPONENT_KEY =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("skbrz:player_spawn_component"), PlayerSpawnPosComponent.class);

    // Method to get the Map containing the world spawn positions
    public static Map<String, BlockPos> getWorldSpawnPositions(World world) {
        // Return the Map containing the world spawn positions
       return WORLD_SPAWN_POS_COMPONENT_KEY.get(world).getValue();
    }

    @Override
    public void registerWorldComponentFactories(WorldComponentFactoryRegistry worldComponentFactoryRegistry) {
        // Create a new Map
        Map<String, BlockPos> defaultPlayerSpawnPos = new HashMap<>();
        // Register the WorldSpawnPosComponent for the given world and pass the empty map to the constructor
        worldComponentFactoryRegistry.register(WORLD_SPAWN_POS_COMPONENT_KEY, world -> new WorldSpawnPosComponent(defaultPlayerSpawnPos));
    }

    // Method to set the spawn position of a player on the world
    public static void setWorldSpawnPointForPlayer(World world, ServerPlayerEntity serverPlayerEntity) {
        // Get the Map containing the world spawn positions
        Map<String, BlockPos> mapOfSpawns = WORLD_SPAWN_POS_COMPONENT_KEY.get(world).getValue();
        // Get the player UUID as a string
        String playerUUID = serverPlayerEntity.getUuidAsString();

        // Get the current entries in the Map containing the world spawn positions
        Set<String> setOfKeys = mapOfSpawns.keySet();
        // Variable to know if the player has a spawn in the given world
        boolean playerHasSpawnInWorld = false;
        // For all the keys in the Map containing the world spawn positions
        for (String key : setOfKeys) {
            // If there is a key containing the given player
            if (key.equals(playerUUID)) {
                // The player has a spawn already set in the given world
                playerHasSpawnInWorld = true;
            }
        }

        // If the player has not been set to have a spawn in the given world
        if (!playerHasSpawnInWorld) {
            // Lists for x and z coordinates already used for other players in the world
            List<Integer> xCoordinatesInUse = new ArrayList<>();
            List<Integer> zCoordinatesInUse = new ArrayList<>();
            // For all the keys in the Map containing the world spawn positions
            for (String key : setOfKeys) {
                // Get the spawn position for the given key
                BlockPos blockPos = mapOfSpawns.get(key);
                // Add the x and z spawn positions for the given key to the list containing coordinates already
                // used in the world
                xCoordinatesInUse.add(blockPos.getX());
                zCoordinatesInUse.add(blockPos.getZ());
            }

            // Variable to know what spawn position to give to the player
            int positionMultiplier = 0;
            // Variable holding whether an empty island was found for the player
            boolean emptyIslandFound = false;

            // While no empty island is found
            do {
                // If the used coordinates do not contain the calculated spawn position
                if (!xCoordinatesInUse.contains((1024 * 16 * positionMultiplier) + 5) && !zCoordinatesInUse.contains((1024 * 16 * positionMultiplier) + 5)) {
                    // An empty island was found for the player
                    emptyIslandFound = true;
                } else {
                    // Increase the multiplier
                    positionMultiplier++;
                }
            } while (!emptyIslandFound);

            // Set the spawn position on the given world for the given player
            WORLD_SPAWN_POS_COMPONENT_KEY.get(world).getValue().put(playerUUID, new BlockPos((1024 * 16 * positionMultiplier) + 5, 73, (1024 * 16 * positionMultiplier) + 5));
        }
    }

    public static void setLastTeleportPositionForPlayer(ServerPlayerEntity serverPlayerEntity) {
        // Set the player UUID string
        PLAYER_SPAWN_POS_COMPONENT_KEY.get(serverPlayerEntity).setPlayerUUID(serverPlayerEntity.getUuidAsString());
        // Set the player last teleport position
        PLAYER_SPAWN_POS_COMPONENT_KEY.get(serverPlayerEntity).setLastTeleportPosition(new int[]{serverPlayerEntity.getBlockPos().getX(),serverPlayerEntity.getBlockPos().getY(), serverPlayerEntity.getBlockPos().getZ()});
        // Set that the player is teleporting to the Nether from the SkyBreezes dimension
        PLAYER_SPAWN_POS_COMPONENT_KEY.get(serverPlayerEntity).setLastVisitedSkyBreezes(true);
    }

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.registerForPlayers(PLAYER_SPAWN_POS_COMPONENT_KEY, playerEntity -> new PlayerSpawnPosComponent(playerEntity.getUuidAsString(), new int[]{-1000000, -1000000, -1000000}, false), RespawnCopyStrategy.INVENTORY);
    }
}
