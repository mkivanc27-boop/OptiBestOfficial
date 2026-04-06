package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.client.sound.SoundInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public class SoundManagerMixin {

    private static int activeSounds = 0;
    private static long lastReset = 0;

    @Inject(method = "play(Lnet/minecraft/client/sound/SoundInstance;)V",
            at = @At("HEAD"), cancellable = true)
    private void optibest_limitSounds(SoundInstance sound, CallbackInfo ci) {
        if (!OptiBestConfig.soundThrottle) return;
        long now = System.currentTimeMillis();
        if (now - lastReset > 1000) {
            activeSounds = 0;
            lastReset = now;
        }
        if (activeSounds >= 5) {
            ci.cancel();
        } else {
            activeSounds++;
        }
    }
}
