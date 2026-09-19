package me.dustin.jex.helper.addon.pegleg;

import me.dustin.jex.helper.addon.AddonHelper;
import me.dustin.jex.helper.addon.ears.EarsHelper;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.Identifier;

public class JexPeglegFeatureRenderer extends FeatureRenderer<Player, PlayerEntityModel<Player>> {
    public JexPeglegFeatureRenderer(FeatureRendererContext<Player, PlayerEntityModel<Player>> featureRendererContext) {
        super(featureRendererContext);
    }

    @Override
    public void render(PoseStack matrices, VertexConsumerProvider vertexConsumers, int light, Player playerEntity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        String uuid = playerEntity.getUuidAsString().replace("-", "");
        if (PeglegHelper.INSTANCE.hasPegleg(uuid))
            this.render(matrices, vertexConsumers, light, playerEntity, PeglegHelper.INSTANCE.getPeglegTexture(uuid), PeglegHelper.INSTANCE.getType(playerEntity), AddonHelper.INSTANCE.getResponse(uuid).enchantedleg());
    }

    public void render(PoseStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, Player playerEntity, Identifier texture, PeglegHelper.PeglegType peglegType, boolean enchanted) {
        if (playerEntity.isInvisible())
            return;
        matrixStack.push();
        PeglegHelper.INSTANCE.getCut_leg().copyTransform(this.getContextModel().leftLeg);
        PeglegHelper.INSTANCE.getCut_Pants().copyTransform(this.getContextModel().leftPants);
        PeglegHelper.INSTANCE.getCut_Pants().visible = this.getContextModel().leftPants.visible;
        this.getContextModel().leftLeg.rotate(matrixStack);
        matrixStack.translate(-0.12f, 0, 0);
        VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(texture), false, enchanted);
        PeglegHelper.INSTANCE.renderPegleg(matrixStack, vertexConsumer, light, LivingEntityRenderer.getOverlay(playerEntity, 0.0f), peglegType);
        matrixStack.pop();
    }
}
