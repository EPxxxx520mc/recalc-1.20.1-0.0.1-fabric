package com.rimeveil.recalc.keybind;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class ModKeybinds {
    public static KeyBinding toggleBattleUI;

    public static void register() {
        toggleBattleUI = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "key.recalc.toggle_battle_ui",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_V,
            "category.recalc"
        ));
    }
}
