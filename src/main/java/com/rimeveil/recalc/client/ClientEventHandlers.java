package com.rimeveil.recalc.client;

import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.keybind.ModKeybinds;
import com.rimeveil.recalc.ui.RecalcBattleUI;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;

public class ClientEventHandlers {
    private static boolean wasKeyDown = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean isKeyDown = ModKeybinds.toggleBattleUI.isPressed();
            
            if (isKeyDown && !wasKeyDown) {
                if (PlayerFrameData.hasFrameAttached(client.player)) {
                    if (client.currentScreen instanceof RecalcBattleUI) {
                        client.setScreen(null);
                    } else {
                        client.setScreen(new RecalcBattleUI());
                    }
                }
            }
            
            wasKeyDown = isKeyDown;
        });
    }
}
