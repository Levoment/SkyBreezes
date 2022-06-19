package com.github.levoment.skybreezes;

import com.github.levoment.skybreezes.biomesources.SkyBreezesBiomeSource;
import com.github.levoment.skybreezes.blocks.SkyBreezesDirt;
import com.github.levoment.skybreezes.chunkgenerators.SkyBreezesChunkGenerator;
import com.github.levoment.skybreezes.components.SkyBreezesComponents;
import com.github.levoment.skybreezes.features.SkyBreezesIslandFeature;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.entity.Entity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import java.util.Map;
import java.util.Objects;

public class SkyBreezes implements ModInitializer {
    public static String MOD_ID = "skbrz";
	public static RegistryKey<World> skyBreezesDimensionRegistryKey;
	// Sky Breezes island feature
	private static SkyBreezesIslandFeature SKY_BREEZES_ISLAND_FEATURE;

	public static final SkyBreezesDirt SKY_BREEZES_DIRT = new SkyBreezesDirt();
	@Override
	public void onInitialize() {
		// Store the raw feature in a variable
        SkyBreezesIslandFeature skyBreezesIslandFeature = new SkyBreezesIslandFeature(DefaultFeatureConfig.CODEC);
        // Register the raw feature
        SKY_BREEZES_ISLAND_FEATURE = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "island_feature"), skyBreezesIslandFeature);
        // Configure the raw feature and store the configured result in a variable
        // ConfiguredFeature<?, ?> skyBreezesconfiguredIslandFeature = skyBreezesIslandFeature.configure(DefaultFeatureConfig.DEFAULT);

        // Register the configured feature in order to be able to use it in the biomes' jsons
        // Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier(MOD_ID, "island_feature"), skyBreezesconfiguredIslandFeature);
        // Register the Sky Breezes dimension
		skyBreezesDimensionRegistryKey = RegistryKey.of(Registry.WORLD_KEY, new Identifier(MOD_ID, "sky_breezes_dimension"));
        // Register the Sky Breezes BiomeSource
		Registry.register(Registry.BIOME_SOURCE, new Identifier(MOD_ID, "sky_breezes_biome_source"), SkyBreezesBiomeSource.CODEC);
		// Register the Sky Breezes ChunkGenerator
		// Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MOD_ID, "sky_breezes_chunk_generator"), SkyBreezesChunkGenerator.CODEC);

		// Register the command to teleport to the SkyBreezes dimension
		CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
					dispatcher.register(CommandManager.literal("teleport_to_sky_breezes_dimension").executes(SkyBreezes.this::executeTeleportToSkyBreezesDimensionCommand));
				}
		);

		// Modify the loot tables
		SkyBreezesLootTableModifier.ModifyLootTables();

		// Register the Sky Breezes Dirt block
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "sky_breezes_dirt"), SKY_BREEZES_DIRT);
		Registry.register(Registry.ITEM, new Identifier(MOD_ID, "sky_breezes_dirt"), new BlockItem(SKY_BREEZES_DIRT, new Item.Settings().group(ItemGroup.MISC)));
	}

	private int executeTeleportToSkyBreezesDimensionCommand(CommandContext<ServerCommandSource> objectCommandContext) throws CommandSyntaxException {
		// Get the player that executed the command
		Entity entity = objectCommandContext.getSource().getEntity();
		ServerWorld destination = (ServerWorld)entity.getEntityWorld();
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
				if (!playerSpawnDimensionRegistryKey.equals(skyBreezesDimensionRegistryKey)) {
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
//				RegistryKey<World> playerSpawnDimensionRegistryKey = serverPlayerEntity.getSpawnPointDimension();
//				if (playerSpawnDimensionRegistryKey.equals(RegistryKey.of(Registry.DIMENSION, new Identifier("minecraft", "overworld")))) {
//					// Set the player spawn point position to their spawn point in the Overworld
//					spawnPointPos = serverPlayerEntity.getSpawnPointPosition();
//				}
				// Teleport the player to the overworld spawn point or to their spawn point in the Overworld if it is set
				serverPlayerEntity.teleport(overworld, spawnPointPos.getX(), spawnPointPos.getY(), spawnPointPos.getZ(), 0.0f, 0.0f);
			} catch (NullPointerException nullPointerException) {
				// Show an error on the console or to the player
			}
		}

		return 1;
	}
}
