package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public class EntityCullMixin {

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void optibest_skipDistantEntityTick(CallbackInfo ci) {
        if (!OptiBestConfig.entityTickOptimization) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        Entity self = (Entity)(Object)this;
        if (self == client.player) return;
        double distSq = client.player.getPos().squaredDistanceTo(self.getPos());
        if (distSq > 64 * 64 && System.currentTimeMillis() % 3 != 0) ci.cancel();
    }
}
