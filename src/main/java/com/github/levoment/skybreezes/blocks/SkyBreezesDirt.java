package com.github.levoment.skybreezes.blocks;

import com.github.levoment.skybreezes.SkyBreezes;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SkyBreezesDirt extends Block {

    public SkyBreezesDirt() {
        super(FabricBlockSettings.of(Material.SOIL).breakByHand(true).hardness(0.5f).sounds(BlockSoundGroup.GRASS));
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, Entity entity) {
        if (!world.isClient()) {
            System.out.println("World is not client");
            ServerWorld destination = entity.getServer().getWorld(SkyBreezes.skyBreezesDimensionRegistryKey);
            if (world.getRegistryKey().equals(SkyBreezes.skyBreezesDimensionRegistryKey)) {
                destination = entity.getServer().getWorld(World.OVERWORLD);
                BlockPos spawnPosition = ((ServerPlayerEntity)entity).getSpawnPointPosition();
                if (spawnPosition == null) spawnPosition = destination.getSpawnPos();
                // Teleport to the Overworld
                entity.changeDimension(destination);
            } else {
                // Teleport to the Sky Breezes Dimension
                entity.changeDimension(destination);
            }
        }
    }
}
