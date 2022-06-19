package com.github.levoment.skybreezes.biomesources;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;

public class SkyBreezesBiomeSource extends BiomeSource {

    public static final Codec<SkyBreezesBiomeSource> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                Biome.field_26750.fieldOf("biomes").forGetter((skyBreezesBiomeSource) -> {
            return skyBreezesBiomeSource.biomeArray;
        })).apply(instance, instance.stable(SkyBreezesBiomeSource::new));
    });

    private RegistryEntryList<Biome> biomeArray;

    public SkyBreezesBiomeSource(RegistryEntryList<Biome> registryEntries) {
        super(registryEntries.stream());
        this.biomeArray = biomeArray;
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
    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
        // Get the Chunk position when the calling method is BiomeArray constructor
        BlockPos chunkPos = new BlockPos(x >> 2, 0, z  >> 2);
        if ((chunkPos.getX() % 1024 != 0) && (chunkPos.getZ() % 1024 != 0) && (chunkPos.getX() % 8 == 0) && (chunkPos.getZ() % 8 == 0)) {
            // Return the cold biome
            return this.biomeArray.get(1);
        }

        RegistryEntry<Biome> biome = this.biomeArray.get(0);
        // Return the default biome
        return biome;
    }
//
//public static final Codec<SkyBreezesBiomeSource> SKY_BREEZES_BIOME_SOURCE_CODEC = RecordCodecBuilder.create(skyBreezesBiomeSourceInstance -> {
//    return skyBreezesBiomeSourceInstance.group(
//     RegistryOps.createRegistryCodec(Registry.BIOME_SOURCE_KEY).forGetter(skyBreezesBiomeSource -> {
//         return
//     })
//    ).apply(skyBreezesBiomeSourceInstance, SkyBreezesBiomeSource::new);
//});
//
//    public static final Codec<SkyBreezesBiomeSource> CODEC = RecordCodecBuilder.create((instance) ->
//            instance.group(
//                    RegistryLookupCodec.of(Registry.BIOME_KEY).forGetter((skyBreezesBiomeSource) -> {
//                        return skyBreezesBiomeSource.biomeRegistry;
//                    }))
//                    .apply(instance, instance.stable(SkyBreezesBiomeSource::new))
//    );
//
//
//    public static Registry<Biome> biomeRegistry;
//
//    private static final List<RegistryKey<Biome>> BIOMES = ImmutableList.of(
//            RegistryKey.of(Registry.BIOME_KEY, new Identifier("skbrz", "skybreezes_default_biome")),
//            RegistryKey.of(Registry.BIOME_KEY, new Identifier("skbrz", "skybreezes_cold_biome")));
//
//    protected SkyBreezesBiomeSource(Registry<Biome> constructorBiomeRegistry) {
//        super(BIOMES.stream().map((registryKey) -> {
//            return () -> {
//                return (Biome)constructorBiomeRegistry.getOrThrow(registryKey);
//            };
//        }));
//    }
//
//    @Override
//    protected Codec<? extends BiomeSource> getCodec() {
//        return CODEC;
//    }
//
//    @Override
//    public BiomeSource withSeed(long seed) {
//        return null;
//    }
//
//    @Override
//    public RegistryEntry<Biome> getBiome(int x, int y, int z, MultiNoiseUtil.MultiNoiseSampler noise) {
//        return null;
//    }
//
//    @Override
//    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
//        // Get the Chunk position when the calling method is BiomeArray constructor
//        BlockPos chunkPos = new BlockPos(biomeX >> 2, 0, biomeZ  >> 2);
//        if ((chunkPos.getX() % 1024 != 0) && (chunkPos.getZ() % 1024 != 0) && (chunkPos.getX() % 8 == 0) && (chunkPos.getZ() % 8 == 0)) {
//            // Return the cold biome
//            return this.getBiomes().get(1);
//        }
//
//        Biome biome = this.getBiomes().get(0);
//        // Return the default biome
//        return biome;
//    }
}
