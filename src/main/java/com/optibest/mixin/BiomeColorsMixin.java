package com.optibest.mixin;

import com.optibest.config.OptiBestConfig;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockRenderView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeColors.class)
public class BiomeColorsMixin {

    @Inject(method = "getGrassColor", at = @At("HEAD"), cancellable = true)
    private static void optibest_flatGrass(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (OptiBestConfig.biomeBlendsOff) cir.setReturnValue(0x5aad3a);
    }

    @Inject(method = "getFoliageColor", at = @At("HEAD"), cancellable = true)
    private static void optibest_flatFoliage(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (OptiBestConfig.biomeBlendsOff) cir.setReturnValue(0x48ae2e);
    }

    @Inject(method = "getWaterColor", at = @At("HEAD"), cancellable = true)
    private static void optibest_flatWater(BlockRenderView world, BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        if (OptiBestConfig.biomeBlendsOff) cir.setReturnValue(0x3f76e4);
    }
}
