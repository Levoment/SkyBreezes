package com.github.levoment.skybreezes.biomes;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public class SkyBreezesColdBiome extends Biome {
    public  SkyBreezesColdBiome() {
        super(new Settings().configureSurfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.DIRT_CONFIG).precipitation(Precipitation.SNOW).category(Category.EXTREME_HILLS).depth(0.24F).scale(0.2F).temperature(0.0F).downfall(1.0F).effects((new BiomeEffects.Builder())
                .waterColor(4159204)
                .waterFogColor(329011)
                .fogColor(12638463).build()).parent((String)null));

        DefaultBiomeFeatures.addDefaultLakes(this);
        DefaultBiomeFeatures.addDefaultFlowers(this);
        DefaultBiomeFeatures.addForestFlowers(this);
        DefaultBiomeFeatures.addSweetBerryBushesSnowy(this);
        DefaultBiomeFeatures.addExtraDefaultFlowers(this);
        DefaultBiomeFeatures.addDefaultGrass(this);
        DefaultBiomeFeatures.addDefaultVegetation(this);
        DefaultBiomeFeatures.addSprings(this);
        DefaultBiomeFeatures.addBamboo(this);
        DefaultBiomeFeatures.addFrozenTopLayer(this);

        this.addSpawn(SpawnGroup.CREATURE, new SpawnEntry(EntityType.FOX, 80, 2, 4));
        this.addSpawn(SpawnGroup.CREATURE, new SpawnEntry(EntityType.POLAR_BEAR, 95, 2, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.VILLAGER, 30, 1, 1));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SKELETON, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.CREEPER, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.SLIME, 100, 4, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.ENDERMAN, 50, 1, 4));
        this.addSpawn(SpawnGroup.MONSTER, new SpawnEntry(EntityType.STRAY, 5, 1, 1));
    }
}
