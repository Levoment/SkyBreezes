package com.github.levoment.skybreezes.chunkgenerators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureSet;
import net.minecraft.util.dynamic.RegistryOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.*;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.HeightLimitView;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.util.MultiNoiseUtil;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class SkyBreezesChunkGenerator extends ChunkGenerator {

    //    public static final Codec<SkyBreezesChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
//            instance.group(
//                    BiomeSource.CODEC.fieldOf("biome_source").forGetter((surfaceChunkGenerator) -> surfaceChunkGenerator.biomeSource),
//                    StructuresConfig.CODEC.fieldOf("structures").forGetter((ChunkGenerator::getStructuresConfig))).
//                    apply(instance, instance.stable(SkyBreezesChunkGenerator::new))
//    );

    public static final Codec<SkyBreezesChunkGenerator> CODEC = RecordCodecBuilder.create((instance) -> {
        return instance.group(
                RegistryOps.createRegistryCodec(Registry.STRUCTURE_SET_KEY).forGetter(skyBreezesChunkGenerator -> {
                    return skyBreezesChunkGenerator.structureSetRegistry;
                }),
                BiomeSource.CODEC.fieldOf("biome_source").forGetter((generator) -> {
                    return generator.getBiomeSource();
                })
        ).apply(instance, instance.stable(SkyBreezesChunkGenerator::new));
    });

    public Registry<StructureSet> structureSetRegistry;


    public SkyBreezesChunkGenerator(Registry<StructureSet> registry, Optional<RegistryEntryList<StructureSet>> optional, BiomeSource biomeSource) {
        super(registry, optional, biomeSource);
    }


    public SkyBreezesChunkGenerator(Registry<StructureSet> structureSets, BiomeSource biomeSource) {
        this(structureSets, Optional.empty(), biomeSource);
    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public ChunkGenerator withSeed(long seed) {
        return this;
    }

    @Override
    public MultiNoiseUtil.MultiNoiseSampler getMultiNoiseSampler() {
        return null;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, BiomeAccess biomeAccess, StructureAccessor structureAccessor, Chunk chunk, GenerationStep.Carver generationStep) {

    }

    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, Chunk chunk) {

    }

    @Override
    public void populateEntities(ChunkRegion region) {

    }

    @Override
    public int getWorldHeight() {
        return 0;
    }

    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, StructureAccessor structureAccessor, Chunk chunk) {
        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return 0;
    }

    @Override
    public int getMinimumY() {
        return 0;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
        return 0;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
        return null;
    }

    @Override
    public void getDebugHudText(List<String> text, BlockPos pos) {

    }

//    public static final Codec<SkyBreezesChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
//            instance.group(
//                    BiomeSource.CODEC.fieldOf("biome_source").forGetter((surfaceChunkGenerator) -> surfaceChunkGenerator.biomeSource),
//                    StructuresConfig.CODEC.fieldOf("structures").forGetter((ChunkGenerator::getStructuresConfig))).
//                    apply(instance, instance.stable(SkyBreezesChunkGenerator::new))
//    );
//
//    public SkyBreezesChunkGenerator(BiomeSource biomeSource, StructuresConfig structuresConfig) {
//        super(biomeSource, structuresConfig);
//    }
//
//    @Override
//    protected Codec<? extends ChunkGenerator> getCodec() {
//        return CODEC;
//    }
//
//    @Override
//    public ChunkGenerator withSeed(long seed) {
//        return this;
//    }
//
//    @Override
//    public void buildSurface(ChunkRegion region, Chunk chunk) {
//
//    }
//
//    @Override
//    public CompletableFuture<Chunk> populateNoise(Executor executor, StructureAccessor accessor, Chunk chunk) {
//        return CompletableFuture.completedFuture(chunk);
//    }
//
//    @Override
//    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world) {
//        return 0;
//    }
//
//    @Override
//    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world) {
//        return null;
//    }


}
