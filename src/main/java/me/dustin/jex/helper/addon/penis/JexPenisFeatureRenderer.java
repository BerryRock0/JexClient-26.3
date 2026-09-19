package me.dustin.jex.helper.addon.penis;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaterniond;
import net.minecraft.world.phys.Vec3;

public class JexPenisFeatureRenderer extends FeatureRenderer<Player, PlayerEntityModel<Player>> {
    public JexPenisFeatureRenderer(FeatureRendererContext<Player, PlayerEntityModel<Player>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrices, VertexConsumerProvider vertexConsumers, int light, Player playerEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        String uuid = playerEntity.getUuidAsString().replace("-", "");
        if (playerEntity.isInvisible() || !PenisHelper.INSTANCE.hasPenis(uuid))
            return;
        matrices.push();
        matrices.multiply(new Quaterniond(new Vec3(1, 0, 0), 90, true));
        boolean sneak = playerEntity.isInSneakingPose();
        matrices.translate(0, sneak ? 0.14 : -0.12, -0.8);
        PenisHelper.INSTANCE.getPenis().pitch = this.getContextModel().leftArm.pitch / 4;
        PenisHelper.INSTANCE.renderPenis(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(PenisHelper.INSTANCE.getPenis(uuid))), light, LivingEntityRenderer.getOverlay(playerEntity, 0.0f));
        matrices.pop();
    }
}
