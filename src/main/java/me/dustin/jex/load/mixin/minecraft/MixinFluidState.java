package me.dustin.jex.load.mixin.minecraft;

import me.dustin.jex.event.world.EventWaterVelocity;
import net.minecraft.fluid.FluidState;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidState.class)
public class MixinFluidState {

    @Inject(method = "getVelocity", at = @At("HEAD"), cancellable = true)
    public void getVelocity(BlockView world, BlockPos pos, CallbackInfoReturnable<Vec3> cir) {
        if (((EventWaterVelocity) new EventWaterVelocity().run()).isCancelled())
            cir.setReturnValue(Vec3.ZERO);
    }

}
