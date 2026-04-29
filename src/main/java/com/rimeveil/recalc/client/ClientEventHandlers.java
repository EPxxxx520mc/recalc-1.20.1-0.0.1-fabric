package com.rimeveil.recalc.client;

import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.keybind.ModKeybinds;
import com.rimeveil.recalc.util.LogUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.Screen;

public class ClientEventHandlers {
    private static final long COOLDOWN_TIME = 250;
    
    private static boolean wasKeyDown = false;
    private static boolean wasAltDown = false;
    private static long lastToggleTime = 0;
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
            boolean isAltDown = Screen.hasAltDown();
            
            if (isKeyDown && !wasKeyDown) {
                LogUtil.debug("Toggle key pressed");
                
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastToggleTime > COOLDOWN_TIME) {
                    if (currentFrameAttached) {
                        if (AnimationManager.isComplete(AnimationManager.ID_ATTACH)) {
                            BattleHUDManager.toggleHUD();
                            lastToggleTime = currentTime;
                            LogUtil.debug("Battle HUD toggled: " + BattleHUDManager.isHUDVisible());
                        }
                    } else {
                        client.player.sendMessage(Text.translatable("message.recalc.no_frame"), true);
                    }
                }
            }
            
            if (isAltDown && !wasAltDown) {
                BattleHUDManager.toggleCursor();
                LogUtil.debug("Cursor toggled: " + BattleHUDManager.isCursorVisible());
            }
            
            wasKeyDown = isKeyDown;
            wasAltDown = isAltDown;
        });
    }
}
