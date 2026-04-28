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
 * - 继承 GeoItemRenderer，用于渲染 GeckoLib 3D 动画模型
 * - 指定模型、纹理、动画的资源位置
 * 
 * 【引用位置】
 * - RecalcClient.java: 注册渲染器
 * 
 * 【资源文件位置】
 * - 模型: assets/recalc/geo/fictional_frame.json
 * - 动画: assets/recalc/animations/fictional_frame.animation.json
 * - 纹理: assets/recalc/textures/item/fictional_frame.png
 * ================================================
 */
public class FictionalFrameRenderer extends GeoItemRenderer<FictionalFrameItem> {
    
    /**
     * 【构造函数】
     * 创建渲染器并指定模型资源
     */
    public FictionalFrameRenderer() {
        super(new GeoModel<>() {
            
            /**
             * 【获取模型文件路径】
             * @return 模型 JSON 文件的 Identifier
             * 
             * 文件位置: assets/recalc/geo/fictional_frame.json
             */
            @Override
            public Identifier getModelResource(FictionalFrameItem animatable) {
                return new Identifier(Recalc.MOD_ID, "geo/fictional_frame.json");
            }
            
            /**
             * 【获取纹理文件路径】
             * @return 纹理 PNG 文件的 Identifier
             * 
             * 文件位置: assets/recalc/textures/item/fictional_frame.png
             * 
             * 【如何更换纹理】
             * 1. 准备新的 PNG 纹理文件
             * 2. 放入 assets/recalc/textures/item/ 目录
             * 3. 修改下面的路径
             */
            @Override
            public Identifier getTextureResource(FictionalFrameItem animatable) {
                return new Identifier(Recalc.MOD_ID, "textures/item/fictional_frame.png");
            }
            
            /**
             * 【获取动画文件路径】
             * @return 动画 JSON 文件的 Identifier
             * 
             * 文件位置: assets/recalc/animations/fictional_frame.animation.json
             * 
             * 【如何添加新动画】
             * 1. 在 BlockBench 中创建新动画
             * 2. 导出为 fictional_frame.animation.json
             * 3. 放入 assets/recalc/animations/ 目录
             */
            @Override
            public Identifier getAnimationResource(FictionalFrameItem animatable) {
                return new Identifier(Recalc.MOD_ID, "animations/fictional_frame.animation.json");
            }
        });
    }
}
