package me.dustin.jex.event.render;

import me.dustin.events.core.Event;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.math.Matrix4f;

public class EventWorldRender extends Event {

    private final float partialTicks;
    private final PoseStack poseStack;
    private final Matrix4f posMatrix;
    private final Mode mode;

    public EventWorldRender(PoseStack poseStack, float partialTicks2, Matrix4f posMatrix, Mode mode) {
        this.partialTicks = partialTicks2;
        this.poseStack = poseStack;
        this.posMatrix = posMatrix;
        this.mode = mode;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public Mode getMode() {
        return mode;
    }

    public Matrix4f getPosMatrix() {
        return posMatrix;
    }

    public enum Mode {
        PRE, POST
    }
}
