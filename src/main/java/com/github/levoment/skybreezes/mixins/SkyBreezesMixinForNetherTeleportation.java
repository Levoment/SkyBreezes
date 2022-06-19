package com.github.levoment.skybreezes.mixins;

import com.github.levoment.skybreezes.SkyBreezes;
import com.github.levoment.skybreezes.components.SkyBreezesComponents;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class SkyBreezesMixinForNetherTeleportation {
    @Inject(method = "moveToWorld(Lnet/minecraft/server/world/ServerWorld;)Lnet/minecraft/entity/Entity;", at = @At("HEAD"), cancellable = true)
    public void moveToWorldCallback(ServerWorld destination, CallbackInfoReturnable<Entity> callbackInfoReturnable) {
        if (!((Entity) (Object)this).world.isClient()) {
            if (destination.getRegistryKey() == World.OVERWORLD) {
                // Check if the last dimension was the Sky Breezes dimension
                if (SkyBreezesComponents.PLAYER_SPAWN_POS_COMPONENT_KEY.get(((Entity) (Object)this)).lastVisitedSkyBreezes()) {
                    // Last visited dimension was the Sky Breezes dimension
                    // Get the player last visited dimension position
                    int[] lastPosition = SkyBreezesComponents.PLAYER_SPAWN_POS_COMPONENT_KEY.get(((Entity) (Object)this)).getLastTeleportPosition();
                    // Get the server player entity
                    ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity)((Entity) (Object)this);
                    // Get the current world
                    ServerWorld targetWorld = serverPlayerEntity.getServer().getWorld(SkyBreezes.skyBreezesDimensionRegistryKey);
                    // Teleport to the player to the Sky Breezes last teleport position
                    serverPlayerEntity.teleport(targetWorld, lastPosition[0], lastPosition[1], lastPosition[2], 0.0f, 0.0f);
                    // Set that the player didn't last visit the Sky Breezes dimension
                    SkyBreezesComponents.PLAYER_SPAWN_POS_COMPONENT_KEY.get(((Entity) (Object)this)).setLastVisitedSkyBreezes(false);
                    // Return the player entity
                    callbackInfoReturnable.setReturnValue(((Entity) (Object)this));
                }
            }
        }
    }
}
