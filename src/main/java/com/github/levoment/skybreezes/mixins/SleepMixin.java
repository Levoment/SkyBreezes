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
    public void setTime(long l) {
        properties.setTime(l);
    }
    @Overwrite
    public void setTimeOfDay(long l) {
        properties.setTimeOfDay(l);
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
