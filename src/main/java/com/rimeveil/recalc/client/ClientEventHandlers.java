package com.rimeveil.recalc.client;

import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.keybind.ModKeybinds;
import com.rimeveil.recalc.ui.RecalcBattleUI;
import com.rimeveil.recalc.util.LogUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;

public class ClientEventHandlers {
    private static final long COOLDOWN_TIME = 250;
    
    private static boolean wasKeyDown = false;
    private static long lastCloseTime = 0;
    private static boolean previousFrameAttached = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean currentFrameAttached = PlayerFrameData.hasFrameAttached(client.player);

            if (currentFrameAttached && !previousFrameAttached) {
                AnimationManager.start(AnimationManager.ID_ATTACH);
            }
            previousFrameAttached = currentFrameAttached;

            AnimationManager.updateAll();

            boolean isKeyDown = ModKeybinds.toggleBattleUI.isPressed();
            
            if (isKeyDown && !wasKeyDown) {
                LogUtil.debug("Toggle key pressed");
                
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastCloseTime > COOLDOWN_TIME) {
                    if (currentFrameAttached) {
                        if (AnimationManager.isComplete(AnimationManager.ID_ATTACH)) {
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
