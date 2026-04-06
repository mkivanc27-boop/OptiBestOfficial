package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.render.WorldRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldRenderer.class)
public class WorldRendererMixin {

    @Inject(method = "scheduleTerrainUpdate", at = @At("HEAD"), cancellable = true)
    private void optibest_throttleTerrain(CallbackInfo ci) {
        if (!OptiBestConfig.chunkThrottling) return;
        if (System.currentTimeMillis() % 3 != 0) ci.cancel();
    }

    @Inject(method = "renderWeather", at = @At("HEAD"), cancellable = true)
    private void optibest_disableWeather(CallbackInfo ci) {
        if (OptiBestConfig.weatherRenderOff) ci.cancel();
    }
}
