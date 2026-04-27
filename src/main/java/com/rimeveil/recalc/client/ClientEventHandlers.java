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
    private static boolean previousFrameAttached = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean currentFrameAttached = PlayerFrameData.hasFrameAttached(client.player);

            // Check if player just attached the frame
            if (currentFrameAttached && !previousFrameAttached) {
                AnimationState.start();
            }
            previousFrameAttached = currentFrameAttached;

            // Update animation state
            AnimationState.update();

            boolean isKeyDown = ModKeybinds.toggleBattleUI.isPressed();
            
            if (isKeyDown && !wasKeyDown) {
                LOGGER.info("V key pressed");
                
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastCloseTime > COOLDOWN_TIME) {
                    if (currentFrameAttached) {
                        if (AnimationState.isComplete()) {
                            if (!(client.currentScreen instanceof RecalcBattleUI)) {
                                client.setScreen(new RecalcBattleUI());
                            }
                        }
                    } else {
                        client.player.sendMessage(Text.translatable("message.recalc.no_frame"), true);
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
