package com.rimeveil.recalc.client;

import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.keybind.ModKeybinds;
import com.rimeveil.recalc.ui.RecalcBattleUI;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientEventHandlers {
    private static final Logger LOGGER = LoggerFactory.getLogger("recalc");
    private static boolean wasKeyDown = false;
    private static long lastCloseTime = 0;
    private static final long COOLDOWN_TIME = 250; // 250ms cooldown

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean isKeyDown = ModKeybinds.toggleBattleUI.isPressed();
            
            if (isKeyDown && !wasKeyDown) {
                LOGGER.info("V key pressed");
                
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastCloseTime > COOLDOWN_TIME) {
                    if (PlayerFrameData.hasFrameAttached(client.player)) {
                        if (!(client.currentScreen instanceof RecalcBattleUI)) {
                            client.setScreen(new RecalcBattleUI());
                        }
                    } else {
                        client.player.sendMessage(Text.literal("No frame attached! Use Fictional Frame first."), true);
                    }
                }
            }
            
            wasKeyDown = isKeyDown;
        });
    }
    
    public static void uiClosed() {
        lastCloseTime = System.currentTimeMillis();
    }
}
