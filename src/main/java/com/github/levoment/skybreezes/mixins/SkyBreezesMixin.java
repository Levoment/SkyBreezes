package com.github.levoment.skybreezes.mixins;

import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.UnmodifiableLevelProperties;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(UnmodifiableLevelProperties.class)
public class SkyBreezesMixin {

    @Shadow
    @Final
    private ServerWorldProperties properties;

    @Inject(method = "method_29035", at = @At("RETURN"))
    private void injectMethod(long l, CallbackInfo info) {
        System.out.println("Please tell me this is working");
        properties.method_29035(l);
    }
}
