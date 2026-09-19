package me.dustin.jex.event.world;

import me.dustin.events.core.Event;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;

public class EventInteractBlock extends Event {
    private final BlockPos pos;
    private final BlockHitResult blockHitResult;
    private final Mode mode;

    public EventInteractBlock(BlockPos blockPos, BlockHitResult blockHitResult, Mode mode) {
        this.pos = blockPos;
        this.blockHitResult = blockHitResult;
        this.mode = mode;
    }

    public BlockPos getPos() {
        return pos;
    }

    public BlockHitResult getBlockHitResult() {
        return this.blockHitResult;
    }

    public Mode getMode() {
        return this.mode;
    }

    public enum Mode {
        PRE, POST
    }
}
