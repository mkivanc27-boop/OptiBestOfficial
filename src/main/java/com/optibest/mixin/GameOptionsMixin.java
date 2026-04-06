package com.optibest.mixin;

import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameOptions.class)
public class GameOptionsMixin {

    @Inject(method = "write", at = @At("HEAD"))
    private void optibest_lockSettings(CallbackInfo ci) {
        GameOptions opts = (GameOptions)(Object)this;
        opts.getMipmapLevels().setValue(0);
        opts.getEntityDistanceScaling().setValue(0.5);
    }
}
