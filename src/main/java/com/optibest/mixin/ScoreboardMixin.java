package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.client.gui.DrawContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class ScoreboardMixin {

    private static long lastScoreboardRender = 0;

    @Inject(method = "renderScoreboard", at = @At("HEAD"), cancellable = true)
    private void optibest_throttleScoreboard(ScoreboardObjective objective,
            DrawContext context, CallbackInfo ci) {
        if (!OptiBestConfig.hudOptimization) return;
        long now = System.currentTimeMillis();
        if (now - lastScoreboardRender < 100) {
            ci.cancel();
        } else {
            lastScoreboardRender = now;
        }
    }
}
