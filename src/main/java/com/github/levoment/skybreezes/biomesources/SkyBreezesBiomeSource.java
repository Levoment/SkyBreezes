package com.github.levoment.skybreezes.biomesources;

import com.github.levoment.skybreezes.SkyBreezes;
import com.github.levoment.skybreezes.chunkgenerators.SkyBreezesChunkGenerator;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.ListCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import jdk.internal.reflect.Reflection;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.RegistryCodec;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.MultiNoiseBiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class SkyBreezesBiomeSource extends BiomeSource {

    public static final Codec<SkyBreezesBiomeSource> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((skyBreezesBiomeSource) -> {
                        return skyBreezesBiomeSource.biomeRegistry;
                    }))
                    .apply(instance, instance.stable(SkyBreezesBiomeSource::new))
    );


    public static Registry<Biome> biomeRegistry;

    private static final List<RegistryKey<Biome>> BIOMES = ImmutableList.of(
            RegistryKey.of(Registry.BIOME_KEY, new Identifier("skbrz", "skybreezes_default_biome")),
            RegistryKey.of(Registry.BIOME_KEY, new Identifier("skbrz", "skybreezes_cold_biome")));

    protected SkyBreezesBiomeSource(Registry<Biome> constructorBiomeRegistry) {
        super(BIOMES.stream().map((registryKey) -> {
            return () -> {
                return (Biome)constructorBiomeRegistry.getOrThrow(registryKey);
            };
        }));
    }

    @Override
    protected Codec<? extends BiomeSource> getCodec() {
        return CODEC;
    }

    @Override
    public BiomeSource withSeed(long seed) {
        return null;
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        // Get the Chunk position when the calling method is BiomeArray constructor
        BlockPos chunkPos = new BlockPos(biomeX >> 2, 0, biomeZ  >> 2);
        if ((chunkPos.getX() % 1024 != 0) && (chunkPos.getZ() % 1024 != 0) && (chunkPos.getX() % 8 == 0) && (chunkPos.getZ() % 8 == 0)) {
            // Return the cold biome
            return this.getBiomes().get(1);
        }

        Biome biome = this.getBiomes().get(0);
        // Return the default biome
        return biome;
    }
}
