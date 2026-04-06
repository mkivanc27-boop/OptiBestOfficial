package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityRenderer.class)
public class EntityRendererMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private <T extends Entity> void optibest_cullEntities(T entity, float yaw, float tickDelta,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, CallbackInfo ci) {
        if (!OptiBestConfig.entityCulling) return;
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        Vec3d playerPos = client.player.getPos();
        Vec3d entityPos = entity.getPos();
        if (playerPos.squaredDistanceTo(entityPos) > 48 * 48) {
            ci.cancel();
            return;
        }
        if (!OptiBestConfig.frustumCulling) return;
        float yawRad = (float) Math.toRadians(client.player.getYaw());
        double lookX = -Math.sin(yawRad);
        double lookZ = Math.cos(yawRad);
        double dx = entityPos.x - playerPos.x;
        double dz = entityPos.z - playerPos.z;
        double len = Math.sqrt(dx * dx + dz * dz);
        if (len < 1.0) return;
        double dot = (lookX * dx + lookZ * dz) / len;
        if (dot < -0.2) ci.cancel();
    }
}
