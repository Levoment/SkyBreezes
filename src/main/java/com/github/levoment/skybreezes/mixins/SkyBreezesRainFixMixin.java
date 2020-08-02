package com.github.levoment.skybreezes.mixins;

import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.UnmodifiableLevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(UnmodifiableLevelProperties.class)
public class SkyBreezesRainFixMixin {

    @Shadow
    ServerWorldProperties properties;

    @Overwrite
    public void setRaining(boolean raining) {
        this.properties.setRaining(raining);
    }

    @Overwrite
    public void setRainTime(int rainTime) {
        this.properties.setRainTime(rainTime);
    }

    @Overwrite
    public int getClearWeatherTime() {
        return properties.getClearWeatherTime();
    }

    @Inject(at = @At("HEAD"), method = "setClearWeatherTime")
    public void setClearWeatherTime(int clearWeatherTime, CallbackInfo callbackInfo) {
        properties.setClearWeatherTime(clearWeatherTime);
    }

    @Overwrite
    public boolean isThundering() {
        return properties.isThundering();
    }

    @Inject(at = @At("HEAD"), method = "setThundering")
    public void setThunderingCallback(boolean thundering, CallbackInfo callbackInfo) {
        properties.setThundering(thundering);
    }

    @Overwrite
    public int getThunderTime() {
        return properties.getThunderTime();
    }

    @Inject(at = @At("HEAD"), method = "setThunderTime")
    public void setThunderTime(int thunderTime, CallbackInfo callbackInfo) {
        properties.setThunderTime(thunderTime);
    }
}
