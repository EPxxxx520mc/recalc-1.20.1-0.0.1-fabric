package com.rimeveil.recalc;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import com.rimeveil.recalc.block.Modblock;
import com.rimeveil.recalc.client.ClientEventHandlers;
import com.rimeveil.recalc.client.RecalcHudRenderer;
import com.rimeveil.recalc.client.renderer.FictionalFrameRenderer;
import com.rimeveil.recalc.keybind.ModKeybinds;
import com.rimeveil.recalc.Item.Moditem;

public class RecalcClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.EXAMPLE_BLOCK5, net.minecraft.client.render.RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.EXAMPLE_BLOCK4, net.minecraft.client.render.RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.SUPER_SUGAR_TRAPDOOR, net.minecraft.client.render.RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.SUPER_SUGAR_DOOR, net.minecraft.client.render.RenderLayer.getTranslucent());

        ModKeybinds.register();
        ClientEventHandlers.register();
        RecalcHudRenderer.register();

        // ==========================================
        // 🎬 注册虚构框架的 GeckoLib 渲染器
        // ==========================================
        // 【功能】让虚构框架物品使用 3D 动画模型渲染
        // 【引用】FictionalFrameRenderer.java, FictionalFrameItem.java
        // ==========================================
        BuiltinItemRendererRegistry.INSTANCE.register(
                Moditem.FICTIONAL_FRAME,
                new FictionalFrameRenderer()
        );
    }
}
