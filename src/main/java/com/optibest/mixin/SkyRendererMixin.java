package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.render.DimensionEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DimensionEffects.class)
public class SkyRendererMixin {

    @Inject(method = "getSkyAngle", at = @At("HEAD"), cancellable = true)
    private void optibest_skipSkyAngle(float timeOfDay, CallbackInfoReturnable<Float> cir) {
        if (OptiBestConfig.skyRenderOff) cir.setReturnValue(0.0f);
    }
}
