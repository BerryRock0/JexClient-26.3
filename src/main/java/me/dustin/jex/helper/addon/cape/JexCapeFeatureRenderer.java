package me.dustin.jex.helper.addon.cape;

import me.dustin.jex.helper.addon.AddonHelper;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.world.phys.Vec3;

public class JexCapeFeatureRenderer extends FeatureRenderer<PlayerEntity, PlayerEntityModel<PlayerEntity>> {
    public JexCapeFeatureRenderer(FeatureRendererContext<PlayerEntity, PlayerEntityModel<PlayerEntity>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, Player playerEntity, float limbAngle, float g, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        String uuid = playerEntity.getUuidAsString().replace("-", "");
        if (!((AbstractClientPlayerEntity)playerEntity).canRenderCapeTexture() || playerEntity.isInvisible() || !playerEntity.isPartVisible(PlayerModelPart.CAPE) || !CapeHelper.INSTANCE.hasCape(uuid)) {
            return;
        }
        ItemStack itemStack = playerEntity.getEquippedStack(EquipmentSlot.CHEST);
        if (itemStack.isOf(Items.ELYTRA)) {
            return;
        }
        AddonHelper.AddonResponse addonResponse = AddonHelper.INSTANCE.getResponse(uuid);
        render(matrixStack, vertexConsumerProvider, light, playerEntity, tickDelta, CapeHelper.INSTANCE.getCape(uuid), addonResponse.enchantedcape());
    }
    public void render(PoseStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, Player playerEntity, float tickDelta, Identifier texture, boolean enchanted) {
        matrixStack.push();
        matrixStack.translate(0.0, 0.0, 0.125);
        double d = Math.lerp(tickDelta, playerEntity.prevCapeX, playerEntity.capeX) - Math.lerp(tickDelta, playerEntity.prevX, playerEntity.getX());
        double e = Math.lerp(tickDelta, playerEntity.prevCapeY, playerEntity.capeY) - Math.lerp(tickDelta, playerEntity.prevY, playerEntity.getY());
        double m = Math.lerp(tickDelta, playerEntity.prevCapeZ, playerEntity.capeZ) - Math.lerp(tickDelta, playerEntity.prevZ, playerEntity.getZ());
        float n = playerEntity.prevBodyYaw + (playerEntity.bodyYaw - playerEntity.prevBodyYaw);
        double o = Math.sin(n * ((float)Math.PI / 180));
        double p = -Math.cos(n * ((float)Math.PI / 180));
        float q = (float)e * 10.0f;
        q = Math.clamp(q, -6.0f, 32.0f);
        float r = (float)(d * o + m * p) * 100.0f;
        r = Math.clamp(r, 0.0f, 150.0f);
        float s = (float)(d * p - m * o) * 100.0f;
        s = Math.clamp(s, -20.0f, 20.0f);
        if (r < 0.0f) {
            r = 0.0f;
        }
        float t = Math.lerp(tickDelta, playerEntity.prevStrideDistance, playerEntity.strideDistance);
        q += Math.sin(Math.lerp(tickDelta, playerEntity.prevHorizontalSpeed, playerEntity.horizontalSpeed) * 6.0f) * 32.0f * t;
        if (playerEntity.isInSneakingPose()) {
            q += 25.0f;
        }
        matrixStack.multiply(Vec3.POSITIVE_X.getDegreesQuaternion(6.0f + r / 2.0f + q));
        matrixStack.multiply(Vec3.POSITIVE_Z.getDegreesQuaternion(s / 2.0f));
        matrixStack.multiply(Vec3.POSITIVE_Y.getDegreesQuaternion(180.0f - s / 2.0f));
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(texture), false, enchanted);
        this.getContextModel().renderCape(matrixStack, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        matrixStack.pop();
    }
}

