package com.rimeveil.recalc.client;

import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.keybind.ModKeybinds;
import com.rimeveil.recalc.ui.BattleHudInteractionScreen;
import com.rimeveil.recalc.util.LogUtil;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ClientEventHandlers {
    private static final long COOLDOWN_TIME = 250;

    private static boolean wasKeyDown;
    private static boolean previousFrameAttached;
    private static long lastToggleTime;

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(ClientEventHandlers::onClientTick);
    }

    private static void onClientTick(MinecraftClient client) {
        if (client.player == null) {
            resetInputState();
            return;
        }

        boolean frameAttached = PlayerFrameData.hasFrameAttached(client.player);
        updateFrameAnimations(frameAttached);
        AnimationManager.updateAll();

        handleHudToggle(client, frameAttached);
        handleCursorHold(client);
    }

    private static void updateFrameAnimations(boolean frameAttached) {
        if (frameAttached && !previousFrameAttached) {
            AnimationManager.start(AnimationManager.ID_ATTACH);
        }
        previousFrameAttached = frameAttached;
    }

    private static void handleHudToggle(MinecraftClient client, boolean frameAttached) {
        boolean keyDown = ModKeybinds.toggleBattleUI.isPressed();
        if (keyDown && !wasKeyDown && canToggleNow()) {
            if (frameAttached && !AnimationManager.isActive(AnimationManager.ID_ATTACH)) {
                BattleHUDManager.toggleHUD();
                if (!BattleHUDManager.isHUDVisible() && client.currentScreen instanceof BattleHudInteractionScreen) {
                    client.setScreen(null);
                }
                lastToggleTime = System.currentTimeMillis();
                LogUtil.debug("Battle HUD toggled: {}", BattleHUDManager.isHUDVisible());
            } else if (!frameAttached) {
                client.player.sendMessage(Text.translatable("message.recalc.no_frame"), true);
            }
        }
        wasKeyDown = keyDown;
    }

    private static void handleCursorHold(MinecraftClient client) {
        boolean leftAltDown = GLFW.glfwGetKey(client.getWindow().getHandle(), GLFW.GLFW_KEY_LEFT_ALT) == GLFW.GLFW_PRESS;
        boolean shouldInteract = BattleHUDManager.isHUDVisible() && leftAltDown;

        BattleHUDManager.setCursorVisible(shouldInteract);

        if (shouldInteract && client.currentScreen == null) {
            client.setScreen(new BattleHudInteractionScreen());
        } else if (!shouldInteract && client.currentScreen instanceof BattleHudInteractionScreen) {
            client.setScreen(null);
        }
    }

    private static boolean canToggleNow() {
        return System.currentTimeMillis() - lastToggleTime > COOLDOWN_TIME;
    }

    private static void resetInputState() {
        wasKeyDown = false;
        previousFrameAttached = false;
        BattleHUDManager.reset();
    }
}
