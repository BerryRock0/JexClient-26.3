package me.dustin.jex.event.player;

import me.dustin.events.core.Event;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.Identifier;

public class EventGetSkinTexture extends Event {
    private final Player playerEntity;
    private Identifier skin;

    public EventGetSkinTexture(Player playerEntity, Identifier skin) {
        this.Player = playerEntity;
        this.skin = skin;
    }

    public Player getPlayerEntity() {
        return playerEntity;
    }

    public Identifier getSkin() {
        return skin;
    }

    public void setSkin(Identifier skin) {
        this.skin = skin;
    }
}
