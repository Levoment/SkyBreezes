package com.github.levoment.skybreezes.biomesources;

import com.mojang.datafixers.util.Pair;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SkyBreezesBiomeSource extends MultiNoiseBiomeSource {
    public SkyBreezesBiomeSource(long seed, List<Pair<Biome.MixedNoisePoint, Biome>> list, Optional<Preset> optional) {
        super(seed, list, optional);
    }

    @Override
    public Set<Biome> getBiomesInArea(int x, int y, int z, int radius) {
        return super.getBiomesInArea(x, y, z, radius);
    }
}
