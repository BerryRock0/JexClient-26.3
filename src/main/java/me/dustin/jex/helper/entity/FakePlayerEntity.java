package me.dustin.jex.helper.entity;

import com.mojang.authlib.GameProfile;
import me.dustin.jex.feature.mod.impl.player.Freecam;
import me.dustin.jex.helper.misc.Wrapper;
import me.dustin.jex.helper.network.MCAPIHelper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.Identifier;
import java.util.UUID;

public class FakePlayer extends AbstractClientPlayer {

    public FakePlayerEntity(ClientWorld world, GameProfile profile) {
        super(world, profile, null);
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public Identifier getSkinTexture() {
        if (hasSkinTexture())
            return super.getSkinTexture();
        else
            return MCAPIHelper.INSTANCE.getPlayerSkin(this == Freecam.Player ? Wrapper.INSTANCE.getMinecraft().getSession().getProfile().getId() : this.uuid);
    }
}
