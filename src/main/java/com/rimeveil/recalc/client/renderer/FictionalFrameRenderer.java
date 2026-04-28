package com.rimeveil.recalc.client.renderer;

import com.rimeveil.recalc.Item.custom.FictionalFrameItem;
import com.rimeveil.recalc.Recalc;
import net.minecraft.util.Identifier;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoItemRenderer;

/**
 * ================================================
 * 🎨 虚构框架物品渲染器
 * ================================================
 *
 * 【功能】
 * - 使用 GeckoLib 渲染 3D 动画模型
 * - 定义模型、纹理、动画的资源位置
 *
 * 【引用位置】
 * - RecalcClient.java 注册渲染器
 * - FictionalFrameItem.java 被渲染的物品
 *
 * 【资源文件位置】
 * - 模型: assets/recalc/geo/fictional_frame.json
 * - 纹理: assets/recalc/textures/item/fictional_frame.png
 * - 动画: assets/recalc/animations/fictional_frame.animation.json
 * ================================================
 */
public class FictionalFrameRenderer extends GeoItemRenderer<FictionalFrameItem> {

    /**
     * 【构造函数】
     * 创建渲染器并指定模型
     */
    public FictionalFrameRenderer() {
        super(new GeoModel<>() {

            /**
             * 【获取模型资源】
             * 返回 BlockBench 导出的模型 JSON 文件位置
             *
             * 【资源位置】
             * assets/recalc/geo/fictional_frame.json
             */
            @Override
            public Identifier getModelResource(FictionalFrameItem animatable) {
                return new Identifier(Recalc.MOD_ID, "geo/fictional_frame.json");
            }

            /**
             * 【获取纹理资源】
             * 返回物品的纹理图片位置
             *
             * 【资源位置】
             * assets/recalc/textures/item/fictional_frame.png
             */
            @Override
            public Identifier getTextureResource(FictionalFrameItem animatable) {
                return new Identifier(Recalc.MOD_ID, "textures/item/fictional_frame.png");
            }

            /**
             * 【获取动画资源】
             * 返回 BlockBench 导出的动画 JSON 文件位置
             *
             * 【资源位置】
             * assets/recalc/animations/fictional_frame.animation.json
             */
            @Override
            public Identifier getAnimationResource(FictionalFrameItem animatable) {
                return new Identifier(Recalc.MOD_ID, "animations/fictional_frame.animation.json");
            }
        });
    }
}
