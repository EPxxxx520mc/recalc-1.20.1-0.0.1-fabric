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
    private static final long COOLDOWN_TIME = 250;
    private static boolean previousFrameAttached = false;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;

            boolean currentFrameAttached = PlayerFrameData.hasFrameAttached(client.player);

            // 检查玩家是否刚刚附着了框架
            if (currentFrameAttached && !previousFrameAttached) {
                AnimationManager.start(AnimationManager.ID_ATTACH);  // ✅ 纯ID模式
            }
            previousFrameAttached = currentFrameAttached;

            // 更新所有动画（一行代码搞定）
            AnimationManager.updateAll();  // ✅ 这个本来就是通用的

            boolean isKeyDown = ModKeybinds.toggleBattleUI.isPressed();
            
            if (isKeyDown && !wasKeyDown) {
                LOGGER.info("V key pressed");
                
                long currentTime = System.currentTimeMillis();
                if (currentTime - lastCloseTime > COOLDOWN_TIME) {
                    if (currentFrameAttached) {
                        if (AnimationManager.isComplete(AnimationManager.ID_ATTACH)) {  // ✅ 纯ID模式
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
