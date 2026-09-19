package me.dustin.jex.event.render;

import me.dustin.events.core.Event;
import com.mojang.blaze3d.vertex.PoseStack;

public class EventRenderCrosshair extends Event {

    private final PoseStack poseStack;

    public EventRenderCrosshair(PoseStack poseStack) {
        this.poseStack = poseStack;
    }

    public PoseStack getPoseStack() {
        return this.poseStack;
    }

}
