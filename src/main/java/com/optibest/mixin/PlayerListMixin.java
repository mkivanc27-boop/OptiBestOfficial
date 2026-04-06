package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerListHud.class)
public class PlayerListMixin {

    private static long lastRender = 0;

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void optibest_throttlePlayerList(DrawContext context, int scaledWindowWidth,
            Scoreboard scoreboard, ScoreboardObjective objective, CallbackInfo ci) {
        if (!OptiBestConfig.playerListOptimization) return;
        long now = System.currentTimeMillis();
        if (now - lastRender < 200) {
            ci.cancel();
        } else {
            lastRender = now;
        }
    }
}
