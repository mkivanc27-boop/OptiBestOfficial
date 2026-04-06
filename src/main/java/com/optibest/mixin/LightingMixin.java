package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightmapTextureManager.class)
public class LightingMixin {

    private static long lastLightUpdate = 0;

    @Inject(method = "update", at = @At("HEAD"), cancellable = true)
    private void optibest_throttleLighting(float tickDelta, CallbackInfo ci) {
        if (!OptiBestConfig.memoryOptimization) return;
        long now = System.currentTimeMillis();
        if (now - lastLightUpdate < 100) {
            ci.cancel();
        } else {
            lastLightUpdate = now;
        }
    }
}
