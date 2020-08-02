package com.github.levoment.skybreezes.mixins;

import net.minecraft.world.level.ServerWorldProperties;
import net.minecraft.world.level.UnmodifiableLevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(UnmodifiableLevelProperties.class)
public class SleepMixin {

    @Shadow
    ServerWorldProperties properties;

    @Overwrite
    public void method_29034(long l) {
        properties.method_29034(l);
    }
    @Overwrite
    public void method_29035(long l) {
        properties.method_29035(l);
    }

    @Overwrite
    public long getTime() {
        return properties.getTime();
    }

    @Overwrite
    public long getTimeOfDay() {
        return properties.getTimeOfDay();
    }
}
