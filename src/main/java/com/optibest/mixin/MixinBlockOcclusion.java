package com.optibest.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.block.Block;

@Mixin(Block.class)
public class MixinBlockOcclusion {
    /**
     * @author Cinar
     * @reason Cizim yukunu azaltmak icin yan yuzey kontrolunu zorlar
     */
    @Overwrite
    public static boolean shouldSideBeRendered(BlockState state, BlockState adjacentState, Direction side) {
        if (state == adjacentState) return false; // Ayni bloklar yan yanaysa arayuzu asla cizme
        return true;
    }
}

