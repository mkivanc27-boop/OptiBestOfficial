package com.optibest.mixin;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderDispatcher.class)
public class MixinNoShadows {
    @Inject(method = "renderShadow", at = @At("HEAD"), cancellable = true)
    private static void stopShadows(MatrixStack matrices, VertexConsumerProvider vertices, Entity entity, float opacity, float tickDelta, CallbackInfo ci) {
        ci.cancel(); // Golge renderini tamamen iptal eder
    }
}
