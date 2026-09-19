package me.dustin.jex.event.player;

import me.dustin.events.core.Event;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.Identifier;

public class EventGetSkinTexture extends Event {
    private final PlayerEntity playerEntity;
    private Identifier skin;

    public EventGetSkinTexture(PlayerEntity playerEntity, Identifier skin) {
        this.playerEntity = playerEntity;
        this.skin = skin;
    }

    public PlayerEntity getPlayerEntity() {
        return playerEntity;
    }

    public Identifier getSkin() {
        return skin;
    }

    public void setSkin(Identifier skin) {
        this.skin = skin;
    }
}
