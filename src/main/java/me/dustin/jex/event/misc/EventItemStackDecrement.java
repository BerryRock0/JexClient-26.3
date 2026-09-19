package me.dustin.jex.event.misc;

import me.dustin.events.core.Event;
import net.minecraft.world.item.ItemStack;

public class EventItemStackDecrement extends Event {
    private final Mode mode;
    private final ItemStack itemStack;
    private final int amount;
    private final int stackCount;

    public EventItemStackDecrement(Mode mode, ItemStack itemStack, int amount, int stackCount) {
        this.mode = mode;
        this.itemStack = itemStack;
        this.amount = amount;
        this.stackCount = stackCount;
    }

    public Mode getMode() {
        return mode;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getAmount() {
        return amount;
    }

    public int getStackCount() {
        return stackCount;
    }

    public enum Mode {
        PRE, POST
    }
}
