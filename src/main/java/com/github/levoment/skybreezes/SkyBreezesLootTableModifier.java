package com.github.levoment.skybreezes;

import net.fabricmc.fabric.api.loot.v2.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.item.Items;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.predicate.entity.LocationPredicate;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;

public class SkyBreezesLootTableModifier {

    private static final Identifier COBBLESTONE_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/cobblestone");
    private static final Identifier IRON_ORE_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/iron_ore");
    private static final Identifier GOLD_ORE_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/gold_ore");
    private static final Identifier REDSTONE_ORE_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/redstone_ore");
    private static final Identifier GRASS_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/grass");
    private static final Identifier SAND_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/sand");
    private static final Identifier OBSIDIAN_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/obsidian");
    private static final Identifier DIRT_LOOT_TABLE_ID = new Identifier("minecraft", "blocks/dirt");

    public static void ModifyLootTables() {
        LootTableEvents.MODIFY.register((resourceManager, lootManager, id, tableBuilder, source) -> {

            // Location predicate to apply the loot change only on biomes from the dimension
            LocationPredicate.Builder defaultBiomeLocationPredicate = LocationPredicate.Builder.create();
            defaultBiomeLocationPredicate.biome(RegistryKey.of(Registry.BIOME_KEY, new Identifier(SkyBreezes.MOD_ID, "skybreezes_default_biome")));
            LootCondition defaultBiomeLootCondition = LocationCheckLootCondition.builder(defaultBiomeLocationPredicate).build();

            LootPool currentLootPool = LootPool.builder().build();

            if (COBBLESTONE_LOOT_TABLE_ID.equals(id)) {

                // Modify the cobblestone loot table
                LootPool poolBuilder = FabricLootPoolBuilder.copyOf(currentLootPool)
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.1f).build())
                        .conditionally(defaultBiomeLootCondition)
                        .with(ItemEntry.builder(Items.COAL_ORE)
                                .build())
                        .with(ItemEntry.builder(Items.IRON_ORE).build()).build();

                tableBuilder.pool(poolBuilder).build();
            }


            if (IRON_ORE_LOOT_TABLE_ID.equals(id)) {
                // Modify the iron ore loot table
                LootPool poolBuilder = FabricLootPoolBuilder.copyOf(currentLootPool)
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.05f).build())
                        .conditionally(defaultBiomeLootCondition)
                        .with(ItemEntry.builder(Items.GOLD_ORE)
                                .build()).build();
                tableBuilder.pool(poolBuilder).build();
            }

            if (GOLD_ORE_LOOT_TABLE_ID.equals(id)) {
                // Modify the gold ore loot table
                LootPool poolBuilder = FabricLootPoolBuilder.copyOf(currentLootPool)
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.01f).build())
                        .conditionally(defaultBiomeLootCondition)
                        .with(ItemEntry.builder(Items.DIAMOND_ORE).build())
                        .with(ItemEntry.builder(Items.REDSTONE_ORE).build()).build();
                tableBuilder.pool(poolBuilder).build();
            }

            if (GRASS_LOOT_TABLE_ID.equals(id)) {
                // Modify the grass loot table
                LootPool poolBuilder = FabricLootPoolBuilder.copyOf(currentLootPool)
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.01f).build())
                        .conditionally(defaultBiomeLootCondition)
                        .with(ItemEntry.builder(Items.POTATO).build())
                        .with(ItemEntry.builder(Items.CARROT).build())
                        .with(ItemEntry.builder(Items.BEETROOT_SEEDS).build()).build();
                tableBuilder.pool(poolBuilder).build();
            }

            if (SAND_LOOT_TABLE_ID.equals(id)) {
                // Modify the sand loot table
                LootPool poolBuilder = FabricLootPoolBuilder.copyOf(currentLootPool)
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.50f).build())
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2)).build())
                        .conditionally(defaultBiomeLootCondition)
                        .with(ItemEntry.builder(Items.SAND).build())
                        .with(ItemEntry.builder(Items.GRAVEL).build()).build();
                tableBuilder.pool(poolBuilder).build();
            }

            if (OBSIDIAN_LOOT_TABLE_ID.equals(id)) {
                // Modify the obsidian loot table
                LootPool poolBuilder = FabricLootPoolBuilder.copyOf(currentLootPool)
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.50f).build())
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2)).build())
                        .conditionally(defaultBiomeLootCondition)
                        .with(ItemEntry.builder(Items.OBSIDIAN).build()).build();
                tableBuilder.pool(poolBuilder).build();
            }

            if (DIRT_LOOT_TABLE_ID.equals(id)) {
                // Modify the dirt loot table
                LootPool poolBuilder = FabricLootPoolBuilder.copyOf(currentLootPool)
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.30f).build())
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(2)).build())
                        .conditionally(defaultBiomeLootCondition)
                        .with(ItemEntry.builder(Items.DIRT).build()).build();
                tableBuilder.pool(poolBuilder).build();
            }

            if (REDSTONE_ORE_LOOT_TABLE_ID.equals(id)) {
                // Modify the redstone ore loot table
                LootPool poolBuilder = FabricLootPoolBuilder.copyOf(currentLootPool)
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.10f).build())
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1)).build())
                        .conditionally(defaultBiomeLootCondition)
                        .with(ItemEntry.builder(Items.LAPIS_ORE).build()).build();
                tableBuilder.pool(poolBuilder).build();
            }
        });
    }
}
