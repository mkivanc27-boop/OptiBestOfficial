package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.render.chunk.ChunkBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChunkBuilder.class)
public class ChunkBuilderMixin {

    private static long lastBuild = 0;

    @Inject(method = "scheduleRebuild", at = @At("HEAD"), cancellable = true)
    private void optibest_limitChunkRebuilds(CallbackInfo ci) {
        if (!OptiBestConfig.chunkCacheOptimization) return;
        long now = System.currentTimeMillis();
        if (now - lastBuild < 50) {
            ci.cancel();
        } else {
            lastBuild = now;
        }
    }
}
