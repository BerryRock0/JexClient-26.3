package me.dustin.jex.event.render;

import me.dustin.events.core.Event;
import net.minecraft.client.render.VertexConsumerProvider;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;

public class EventWorldRenderEntity extends Event {
    private final Entity entity;
    private final PoseStack matrixStack;
    private final VertexConsumerProvider vertexConsumerProvider;
    private final float tickDelta;

    public EventWorldRenderEntity(Entity entity, PoseStack matrixStack, VertexConsumerProvider vertexConsumerProvider, float tickDelta) {
        this.entity = entity;
        this.matrixStack = matrixStack;
        this.vertexConsumerProvider = vertexConsumerProvider;
        this.tickDelta = tickDelta;
    }

    public Entity getEntity() {
        return entity;
    }

    public PoseStack getMatrixStack() {
        return matrixStack;
    }

    public VertexConsumerProvider getVertexConsumerProvider() {
        return vertexConsumerProvider;
    }

    public float getTickDelta() {
        return tickDelta;
    }
}
