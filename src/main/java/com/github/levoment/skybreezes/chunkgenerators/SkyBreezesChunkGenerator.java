package com.github.levoment.skybreezes.chunkgenerators;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.BlockView;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.Heightmap;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.StructuresConfig;

public class SkyBreezesChunkGenerator extends ChunkGenerator {

    public static final Codec<SkyBreezesChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(
                    BiomeSource.CODEC.fieldOf("biome_source").forGetter((surfaceChunkGenerator) -> surfaceChunkGenerator.biomeSource),
                    StructuresConfig.CODEC.fieldOf("structures").forGetter((ChunkGenerator::getStructuresConfig))).
                    apply(instance, instance.stable(SkyBreezesChunkGenerator::new))
    );

    public SkyBreezesChunkGenerator(BiomeSource biomeSource, StructuresConfig structuresConfig) {
        super(biomeSource, structuresConfig);
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
    public void buildSurface(ChunkRegion region, Chunk chunk) {

    }

    @Override
    public void populateNoise(WorldAccess world, StructureAccessor accessor, Chunk chunk) {

    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        return 0;
    }

    @Override
    public BlockView getColumnSample(int x, int z) {
        return null;
    }
}
