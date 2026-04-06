package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatHud.class)
public class ChatRenderMixin {

    private static long lastChatRender = 0;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void optibest_skipChat(DrawContext context, int currentTick,
            int mouseX, int mouseY, boolean focused, CallbackInfo ci) {
        if (!OptiBestConfig.hudOptimization) return;
        if (!focused) {
            long now = System.currentTimeMillis();
            if (now - lastChatRender < 100) {
                ci.cancel();
            } else {
                lastChatRender = now;
            }
        }
    }
}
