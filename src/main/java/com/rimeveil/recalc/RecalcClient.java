package com.rimeveil.recalc;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import com.rimeveil.recalc.block.Modblock;
import com.rimeveil.recalc.client.ClientEventHandlers;
import com.rimeveil.recalc.client.RecalcHudRenderer;
import com.rimeveil.recalc.keybind.ModKeybinds;

/**
 * ================================================
 * 🖥️ 客户端初始化类
 * ================================================
 * 
 * 【功能】
 * - 注册客户端渲染相关内容
 * - 注册按键绑定
 * - 注册 HUD 渲染器
 * - GeckoLib 3D 模型渲染器通过 GeoItem 接口自动注册
 * 
 * 【引用位置】
 * - fabric.mod.json: 作为 client 入口点
 * ================================================
 */
public class RecalcClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // ==========================================
        // 🧊 方块渲染层注册
        // ==========================================
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.EXAMPLE_BLOCK5, net.minecraft.client.render.RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.EXAMPLE_BLOCK4, net.minecraft.client.render.RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.SUPER_SUGAR_TRAPDOOR, net.minecraft.client.render.RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.SUPER_SUGAR_DOOR, net.minecraft.client.render.RenderLayer.getTranslucent());
        
        // ==========================================
        // ⌨️ 按键绑定注册
        // ==========================================
        ModKeybinds.register();
        
        // ==========================================
        // 🎮 客户端事件处理器注册
        // ==========================================
        ClientEventHandlers.register();
        
        // ==========================================
        // 🎨 HUD 渲染器注册
        // ==========================================
        RecalcHudRenderer.register();
        
        // ==========================================
        // 🎬 GeckoLib 3D 模型渲染器
        // ==========================================
        // 【注意】虚构框架物品的渲染器通过 GeoItem 接口自动注册
        // 不需要在这里手动注册！
        // 详见: FictionalFrameItem.java 中的 createRenderer() 方法
    }
}
