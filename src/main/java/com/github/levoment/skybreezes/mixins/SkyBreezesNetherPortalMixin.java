package com.github.levoment.skybreezes.mixins;

import com.github.levoment.skybreezes.SkyBreezes;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractFireBlock.class)
public class SkyBreezesNetherPortalMixin {
    @Inject(method = "method_30366", at = @At("HEAD"), cancellable = true)
    private static void isSkyBreezesDimension(World world, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (world.getRegistryKey() == SkyBreezes.skyBreezesDimensionRegistryKey) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
//    @Overwrite
//    public static boolean method_30366(World world) {
//        return world.getRegistryKey() == World.OVERWORLD || world.getRegistryKey() == World.NETHER || world.getRegistryKey() == SkyBreezes.skyBreezesDimensionRegistryKey;
//    }
}
