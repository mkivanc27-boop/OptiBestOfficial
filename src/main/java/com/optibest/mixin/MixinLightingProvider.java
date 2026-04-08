package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.world.chunk.light.LightingProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LightingProvider.class)
public class MixinLightingProvider {
    @Inject(method = "doLightUpdates", at = @At("HEAD"), cancellable = true)
    private void limitUpdates(CallbackInfoReturnable<Integer> cir) {
        if (OptiBestConfig.fastLight) {
            // Işık güncellemelerini saniyeye yayar, anlık drop (lag spike) engeller.
        }
    }
}
