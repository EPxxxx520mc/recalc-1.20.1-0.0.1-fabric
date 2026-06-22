package com.rimeveil.recalc;

import com.rimeveil.recalc.block.Modblock;
import com.rimeveil.recalc.client.ClientEventHandlers;
import com.rimeveil.recalc.client.ClientNetworking;
import com.rimeveil.recalc.client.RecalcBattleHUD;
import com.rimeveil.recalc.client.RecalcHudRenderer;
import com.rimeveil.recalc.keybind.ModKeybinds;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;

public class RecalcClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        registerRenderLayers();

        ModKeybinds.register();
        ClientNetworking.register();
        ClientEventHandlers.register();
        RecalcHudRenderer.register();
        RecalcBattleHUD.register();
    }

    private static void registerRenderLayers() {
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.EXAMPLE_BLOCK5, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.EXAMPLE_BLOCK4, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.SUPER_SUGAR_TRAPDOOR, RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.SUPER_SUGAR_DOOR, RenderLayer.getTranslucent());
    }
}
