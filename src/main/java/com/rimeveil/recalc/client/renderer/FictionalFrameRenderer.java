package com.rimeveil.recalc.client.renderer;

import com.rimeveil.recalc.Item.custom.FictionalFrameItem;
import com.rimeveil.recalc.client.model.FictionalFrameModel;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class FictionalFrameRenderer extends GeoItemRenderer<FictionalFrameItem> {
    public FictionalFrameRenderer() {
        super(new FictionalFrameModel());
    }

    @Override
    public void render(ItemStack stack, RenderLayer renderType, MatrixStack poseStack, VertexConsumerProvider buffer, int packedLight, int packedOverlay) {
        // 如果需要自定义渲染，可以在这里修改
        super.render(stack, renderType, poseStack, buffer, packedLight, packedOverlay);
    }
}
