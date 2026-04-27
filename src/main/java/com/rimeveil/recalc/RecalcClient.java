package com.rimeveil.recalc;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import com.rimeveil.recalc.block.Modblock;
import com.rimeveil.recalc.client.ClientEventHandlers;
import com.rimeveil.recalc.keybind.ModKeybinds;

public class RecalcClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.EXAMPLE_BLOCK5, net.minecraft.client.render.RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.EXAMPLE_BLOCK4, net.minecraft.client.render.RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.SUPER_SUGAR_TRAPDOOR, net.minecraft.client.render.RenderLayer.getTranslucent());
        BlockRenderLayerMap.INSTANCE.putBlock(Modblock.SUPER_SUGAR_DOOR, net.minecraft.client.render.RenderLayer.getTranslucent());
        
        ModKeybinds.register();
        ClientEventHandlers.register();
    }
}
