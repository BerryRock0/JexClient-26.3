package me.dustin.jex.event.render;

import me.dustin.events.core.Event;
import net.minecraft.client.gui.widget.ClickableWidget;
import com.mojang.blaze3d.vertex.PoseStack;

public class EventRenderWidget extends Event {

    private final ClickableWidget abstractWidget;
    private final PoseStack poseStack;

    public EventRenderWidget(ClickableWidget abstractWidget, PoseStack poseStack) {
        this.abstractWidget = abstractWidget;
        this.poseStack = poseStack;
    }

    public ClickableWidget getAbstractWidget() {
        return abstractWidget;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }
}
