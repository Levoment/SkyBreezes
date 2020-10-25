package com.github.levoment.skybreezes;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.minecraft.item.Items;
import net.minecraft.loot.ConstantLootTableRange;
import net.minecraft.loot.condition.LocationCheckLootCondition;
import net.minecraft.loot.condition.LootCondition;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
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
        LootTableLoadingCallback.EVENT.register((resourceManager, lootManager, id, supplier, setter) -> {

            // Location predicate to apply the loot change only on biomes from the dimension
            LocationPredicate.Builder defaultBiomeLocationPredicate = LocationPredicate.Builder.create();
            defaultBiomeLocationPredicate.biome(RegistryKey.of(Registry.BIOME_KEY, new Identifier(SkyBreezes.MOD_ID, "skybreezes_default_biome")));
            LootCondition defaultBiomeLootCondition = LocationCheckLootCondition.builder(defaultBiomeLocationPredicate).build();

            if (COBBLESTONE_LOOT_TABLE_ID.equals(id)) {
                // Modify the cobblestone loot table
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(1))
                        .withCondition(RandomChanceLootCondition.builder(0.1f).build())
                        .withCondition(defaultBiomeLootCondition)
                        .withEntry(ItemEntry.builder(Items.COAL_ORE)
                                .build())
                        .withEntry(ItemEntry.builder(Items.IRON_ORE).build());
                supplier.withPool(poolBuilder.build());
            }


            if (IRON_ORE_LOOT_TABLE_ID.equals(id)) {
                // Modify the iron ore loot table
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(1))
                        .withCondition(RandomChanceLootCondition.builder(0.05f).build())
                        .withCondition(defaultBiomeLootCondition)
                        .withEntry(ItemEntry.builder(Items.GOLD_ORE)
                                .build());
                supplier.withPool(poolBuilder.build());
            }

            if (GOLD_ORE_LOOT_TABLE_ID.equals(id)) {
                // Modify the gold ore loot table
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(1))
                        .withCondition(RandomChanceLootCondition.builder(0.01f).build())
                        .withCondition(defaultBiomeLootCondition)
                        .withEntry(ItemEntry.builder(Items.DIAMOND_ORE).build())
                        .withEntry(ItemEntry.builder(Items.REDSTONE_ORE).build());
                supplier.withPool(poolBuilder.build());
            }

            if (GRASS_LOOT_TABLE_ID.equals(id)) {
                // Modify the grass loot table
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(1))
                        .withCondition(RandomChanceLootCondition.builder(0.01f).build())
                        .withCondition(defaultBiomeLootCondition)
                        .withEntry(ItemEntry.builder(Items.POTATO).build())
                        .withEntry(ItemEntry.builder(Items.CARROT).build())
                        .withEntry(ItemEntry.builder(Items.BEETROOT_SEEDS).build());
                supplier.withPool(poolBuilder.build());
            }

            if (SAND_LOOT_TABLE_ID.equals(id)) {
                // Modify the sand loot table
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(1))
                        .withCondition(RandomChanceLootCondition.builder(0.50f).build())
                        .withFunction(SetCountLootFunction.builder(ConstantLootTableRange.create(2)).build())
                        .withCondition(defaultBiomeLootCondition)
                        .withEntry(ItemEntry.builder(Items.SAND).build())
                        .withEntry(ItemEntry.builder(Items.GRAVEL).build());
                supplier.withPool(poolBuilder.build());
            }

            if (OBSIDIAN_LOOT_TABLE_ID.equals(id)) {
                // Modify the obsidian loot table
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(1))
                        .withCondition(RandomChanceLootCondition.builder(0.50f).build())
                        .withFunction(SetCountLootFunction.builder(ConstantLootTableRange.create(2)).build())
                        .withCondition(defaultBiomeLootCondition)
                        .withEntry(ItemEntry.builder(Items.OBSIDIAN).build());
                supplier.withPool(poolBuilder.build());
            }

            if (DIRT_LOOT_TABLE_ID.equals(id)) {
                // Modify the dirt loot table
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(1))
                        .withCondition(RandomChanceLootCondition.builder(0.30f).build())
                        .withFunction(SetCountLootFunction.builder(ConstantLootTableRange.create(2)).build())
                        .withCondition(defaultBiomeLootCondition)
                        .withEntry(ItemEntry.builder(Items.DIRT).build());
                supplier.withPool(poolBuilder.build());
            }

            if (REDSTONE_ORE_LOOT_TABLE_ID.equals(id)) {
                // Modify the redstone ore loot table
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootTableRange.create(1))
                        .withCondition(RandomChanceLootCondition.builder(0.10f).build())
                        .withFunction(SetCountLootFunction.builder(ConstantLootTableRange.create(1)).build())
                        .withCondition(defaultBiomeLootCondition)
                        .withEntry(ItemEntry.builder(Items.LAPIS_ORE).build());
                supplier.withPool(poolBuilder.build());
            }
        });
    }
}
