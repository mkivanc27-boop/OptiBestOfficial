package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.render.entity.EntityRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class ShadowRendererMixin {

    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    private static void optibest_disableShadow(CallbackInfo ci) {
        if (OptiBestConfig.entityShadowsDisabled) ci.cancel();
    }
}
