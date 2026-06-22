package com.rimeveil.recalc.client;

public class BattleHUDManager {
    private static boolean hudVisible;
    private static boolean cursorVisible;

    private static float abilityCurrent = 100.0f;
    private static float abilityMax = 100.0f;

    public static void toggleHUD() {
        hudVisible = !hudVisible;
        if (!hudVisible) {
            cursorVisible = false;
        }
    }

    public static boolean isHUDVisible() {
        return hudVisible;
    }

    public static boolean isCursorVisible() {
        return cursorVisible;
    }

    public static void setCursorVisible(boolean visible) {
        cursorVisible = visible;
    }

    public static float getAbilityCurrent() {
        return abilityCurrent;
    }

    public static void setAbilityCurrent(float value) {
        abilityCurrent = Math.max(0.0f, Math.min(value, abilityMax));
    }

    public static void addAbility(float amount) {
        abilityCurrent = Math.min(abilityCurrent + amount, abilityMax);
    }

    public static void removeAbility(float amount) {
        abilityCurrent = Math.max(abilityCurrent - amount, 0.0f);
    }

    public static float getAbilityMax() {
        return abilityMax;
    }

    public static void setAbilityMax(float value) {
        abilityMax = Math.max(1.0f, value);
        abilityCurrent = Math.min(abilityCurrent, abilityMax);
    }

    public static void reset() {
        hudVisible = false;
        cursorVisible = false;
    }
}
