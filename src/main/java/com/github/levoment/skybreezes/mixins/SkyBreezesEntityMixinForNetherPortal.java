package com.github.levoment.skybreezes.mixins;

import com.github.levoment.skybreezes.SkyBreezes;
import com.github.levoment.skybreezes.components.SkyBreezesComponents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Entity.class)
public class SkyBreezesEntityMixinForNetherPortal {
    @ModifyArg(method = "tickNetherPortal()V",
            at = @At(value = "INVOKE",
                    target = "net/minecraft/server/MinecraftServer.getWorld (Lnet/minecraft/util/registry/RegistryKey;)Lnet/minecraft/server/world/ServerWorld;"),
            index = 0)
    private RegistryKey<World> dimensionToTeleportTo(RegistryKey<World> registryKey) {
        if (!((Entity) (Object)this).world.isClient()) {
            // If the current dimension is Sky Breezes
            if (((Entity) (Object)this).world.getRegistryKey() == SkyBreezes.skyBreezesDimensionRegistryKey) {
                // Teleporting to the Nether from within the SkyBreezes dimension
                // Set the user position on the player component
                SkyBreezesComponents.setLastTeleportPositionForPlayer((ServerPlayerEntity)(Entity) (Object)this);
            }
        }
        // Return the dimension to teleport to
        return ((Entity) (Object)this).world.getRegistryKey() == World.NETHER ? World.OVERWORLD : World.NETHER;
    }
}
