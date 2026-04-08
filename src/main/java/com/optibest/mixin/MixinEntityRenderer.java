package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
public class MixinEntityRenderer<T extends Entity> {
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private void onShouldRender(T entity, Frustum frustum, double x, double y, double z, CallbackInfoReturnable<Boolean> cir) {
        if (!OptiBestConfig.aggressiveCulling) return;

        double distanceSq = entity.squaredDistanceTo(MinecraftClient.getInstance().player);
        
        // 1. Kural: Görüş açısında değilse sil (Entity Culling)
        if (!frustum.isVisible(entity.getBoundingBoxForCulling())) {
            cir.setReturnValue(false);
            return;
        }

        // 2. Kural: 25 blok (25*25 = 625) ötesindeki dekoratif veya küçük varlıkları renderlama
        if (distanceSq > 625) {
            if (entity.isInvisible() || entity.isSneaking()) {
                cir.setReturnValue(false);
            }
        }
    }
}

