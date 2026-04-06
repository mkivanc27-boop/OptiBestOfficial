package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class PacketHandlerMixin {

    private static long lastEntityUpdate = 0;

    @Inject(method = "onEntityTrackerUpdate", at = @At("HEAD"), cancellable = true)
    private void optibest_throttleEntityUpdates(EntityTrackerUpdateS2CPacket packet, CallbackInfo ci) {
        if (!OptiBestConfig.packetThrottle) return;
        long now = System.currentTimeMillis();
        if (now - lastEntityUpdate < 50) {
            ci.cancel();
        } else {
            lastEntityUpdate = now;
        }
    }
}
