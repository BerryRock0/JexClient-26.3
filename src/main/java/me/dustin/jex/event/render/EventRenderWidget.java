package me.dustin.jex.event.render;

import me.dustin.events.core.Event;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;

public class EventRenderWidget extends Event {

    private final ClickableWidget abstractWidget;
    private final GuiGraphics guiGraphics;

    public EventRenderWidget(ClickableWidget abstractWidget, GuiGraphics guiGraphics) {
        this.abstractWidget = abstractWidget;
        this.guiGraphics = guiGraphics;
    }

    public ClickableWidget getAbstractWidget() {
        return abstractWidget;
    }

    public GuiGraphics getGuiGraphics() {
        return poseStack;
    }
}
