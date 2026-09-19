package me.dustin.jex.event.render;

import me.dustin.events.core.Event;
import net.minecraft.client.render.VertexConsumerProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;

public class EventRenderNametags extends Event {

    private final LivingEntity entity;
    private final PoseStack poseStack;
    private final VertexConsumerProvider multiBufferSource;

    public EventRenderNametags(LivingEntity entity, PoseStack poseStack, VertexConsumerProvider multiBufferSource) {
        this.entity = entity;
        this.poseStack = poseStack;
        this.multiBufferSource = multiBufferSource;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public VertexConsumerProvider getMultiBufferSource() {
        return multiBufferSource;
    }
}
