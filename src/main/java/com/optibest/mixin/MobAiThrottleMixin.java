package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MobEntity.class)
public class MobAiThrottleMixin {

    @Inject(method = "tickNewAi", at = @At("HEAD"), cancellable = true)
    private void optibest_throttleMobAi(CallbackInfo ci) {
        if (!OptiBestConfig.entityTickOptimization) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        MobEntity self = (MobEntity)(Object)this;
        double distSq = client.player.getPos().squaredDistanceTo(self.getPos());
        if (distSq > 32 * 32 && System.currentTimeMillis() % 4 != 0) {
            ci.cancel();
            return;
        }
        if (distSq > 48 * 48) {
            ci.cancel();
        }
    }
}
