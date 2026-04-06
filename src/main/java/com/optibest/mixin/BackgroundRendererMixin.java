package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.render.BackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {

    @Inject(method = "applyFog", at = @At("HEAD"), cancellable = true)
    private static void optibest_disableFog(CallbackInfo ci) {
        if (OptiBestConfig.fogDisabled) ci.cancel();
    }
}
