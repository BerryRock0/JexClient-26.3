package me.dustin.jex.event.render;

import me.dustin.events.core.Event;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;

public class EventRenderHeldItem extends Event {

    private final ItemStack itemStack;
    private final Hand hand;
    private final float partialTicks;
    private final PoseStack poseStack;

    public EventRenderHeldItem(ItemStack itemStack, Hand hand, float partialTicks, PoseStack poseStack) {
        this.itemStack = itemStack;
        this.hand = hand;
        this.partialTicks = partialTicks;
        this.poseStack = poseStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Hand getHand() {
        return hand;
    }

    public float getPartialTicks() {
        return partialTicks;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }
}
