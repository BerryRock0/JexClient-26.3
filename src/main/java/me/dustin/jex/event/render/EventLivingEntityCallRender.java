package me.dustin.jex.event.render;

import me.dustin.events.core.Event;
import net.minecraft.world.entity.LivingEntity;

public class EventLivingEntityCallRender extends Event {
    private final LivingEntity livingEntity;

    public EventLivingEntityCallRender(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }
}
