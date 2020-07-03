package com.github.levoment.skybreezes.mixins;

import com.github.levoment.skybreezes.SkyBreezes;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractFireBlock.class)
public class SkyBreezesNetherPortalMixin {
    @Inject(method = "onBlockAdded", at = @At("HEAD"))
    public void onBlockAddedCallback (BlockState state, World world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo info) {
        if (!oldState.isOf(state.getBlock())) {
            // Check if the dimension is the Sky Breezes dimension or if a nether portal can be created at the pos
            if (world.getRegistryKey() != SkyBreezes.skyBreezesDimensionRegistryKey || !NetherPortalBlock.createPortalAt(world, pos)) {
                if (!state.canPlaceAt(world, pos)) {
                    world.removeBlock(pos, false);
                }
            }
        }
    }
}
