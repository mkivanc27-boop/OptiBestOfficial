package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Map;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {

    @Shadow private Map<?, ?> particles;

    @Inject(method = "addParticle(Lnet/minecraft/client/particle/Particle;)V",
            at = @At("HEAD"), cancellable = true)
    private void optibest_limitParticles(Particle particle, CallbackInfo ci) {
        if (!OptiBestConfig.particleLimit) return;
        if (particles != null && particles.size() > OptiBestConfig.maxParticles) ci.cancel();
    }
}
