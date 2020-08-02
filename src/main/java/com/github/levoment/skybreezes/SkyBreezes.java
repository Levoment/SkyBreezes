package com.github.levoment.skybreezes;

import com.github.levoment.skybreezes.biomes.SkyBreezesColdBiome;
import com.github.levoment.skybreezes.biomes.SkyBreezesDefaultBiome;
import com.github.levoment.skybreezes.blocks.SkyBreezesDirt;
import com.github.levoment.skybreezes.chunkgenerators.SkyBreezesChunkGenerator;
import com.github.levoment.skybreezes.features.SkyBreezesIslandFeature;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.block.pattern.BlockPattern;
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
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

public class SkyBreezes implements ModInitializer {
    private static String MOD_ID = "skbrz";
	public static RegistryKey<World> skyBreezesDimensionRegistryKey;
	// Register the default biome
	public static final Biome SKY_BREEZES_DEFAULT_BIOME = Registry.register(Registry.BIOME, new Identifier(MOD_ID, "sky_breezes_default_biome"), new SkyBreezesDefaultBiome());
	// Register the cold biome
	public static final Biome SKY_BREEZES_COLD_BIOME = Registry.register(Registry.BIOME, new Identifier(MOD_ID, "sky_breezes_cold_biome"), new SkyBreezesColdBiome());
	// Registed the Sky Breezes island feature
	private static final SkyBreezesIslandFeature SKY_BREEZES_ISLAND_FEATURE = Registry.register(
			Registry.FEATURE,
			new Identifier(MOD_ID, "island_feature"),
			new SkyBreezesIslandFeature(DefaultFeatureConfig.CODEC)
	);

	public static final SkyBreezesDirt SKY_BREEZES_DIRT = new SkyBreezesDirt();

	@Override
	public void onInitialize() {
		// Add the biomes feature generators
		Registry.BIOME.get(new Identifier(MOD_ID, "sky_breezes_default_biome")).addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, SKY_BREEZES_ISLAND_FEATURE.configure(new DefaultFeatureConfig()));
		Registry.BIOME.get(new Identifier(MOD_ID, "sky_breezes_cold_biome")).addFeature(GenerationStep.Feature.SURFACE_STRUCTURES, SKY_BREEZES_ISLAND_FEATURE.configure(new DefaultFeatureConfig()));
		// Register the Sky Breezes dimension
		skyBreezesDimensionRegistryKey = RegistryKey.of(Registry.DIMENSION, new Identifier(MOD_ID, "sky_breezes_dimension"));
		Registry.register(Registry.CHUNK_GENERATOR, new Identifier(MOD_ID, "sky_breezes_chunk_generator"), SkyBreezesChunkGenerator.CODEC);

		// Register default placer for our dimension
		FabricDimensions.registerDefaultPlacer(skyBreezesDimensionRegistryKey, SkyBreezes::placeEntityInSkyBreezesDimension);

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
		ServerPlayerEntity serverPlayerEntity = objectCommandContext.getSource().getPlayer();
		ServerWorld serverWorld = serverPlayerEntity.getServerWorld();
		if (!serverWorld.getRegistryKey().equals(skyBreezesDimensionRegistryKey)) {
			serverPlayerEntity.changeDimension(objectCommandContext.getSource().getMinecraftServer().getWorld(skyBreezesDimensionRegistryKey));
		} else {
			FabricDimensions.teleport(serverPlayerEntity, objectCommandContext.getSource().getMinecraftServer().getWorld(World.OVERWORLD), SkyBreezes::placeEntityOnOverworld);
		}
		return 1;
	}

	private static BlockPattern.TeleportTarget placeEntityOnOverworld(Entity teleported, ServerWorld destination, Direction portalDir, double horizontalOffset, double verticalOffset) {
		BlockPos spawnPosition = ((ServerPlayerEntity)teleported).getSpawnPointPosition();
		if (spawnPosition == null) spawnPosition = destination.getSpawnPos();
		return new BlockPattern.TeleportTarget(new Vec3d(spawnPosition.getX(), spawnPosition.getY(), spawnPosition.getZ()), Vec3d.ZERO, 0);
	}

	private static BlockPattern.TeleportTarget placeEntityInSkyBreezesDimension(Entity teleported, ServerWorld destination, Direction portalDir, double horizontalOffset, double verticalOffset) {
		return new BlockPattern.TeleportTarget(new Vec3d(5.5, 73, 5.5), Vec3d.ZERO, 0);
	}

}
