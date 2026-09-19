package me.dustin.jex.event.render;

import me.dustin.events.core.Event;
import net.minecraft.client.gui.hud.ChatHud;
import com.mojang.blaze3d.vertex.PoseStack;

public class EventRenderChatHud extends Event {
    private final ChatHud chatHud;
    private final PoseStack poseStack;
    private final int tickDelta;

    public EventRenderChatHud(ChatHud chatHud, PoseStack poseStack, int tickDelta) {
        this.chatHud = chatHud;
        this.poseStack = poseStack;
        this.tickDelta = tickDelta;
    }

    public ChatHud getChatHud() {
        return chatHud;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public int getTickDelta() {
        return tickDelta;
    }
}
