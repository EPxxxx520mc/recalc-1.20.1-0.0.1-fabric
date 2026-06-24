package com.rimeveil.recalc.client;

import net.minecraft.text.Text;

public class BattleHUDManager {
    public enum AbilityType {
        NONE,
        FIRE,
        LIGHTNING,
        WIND,
        TELEPORT,
        VECTOR
    }

    private static final long HUD_OPEN_DURATION_MS = 700L;
    private static final long HUD_CLOSE_DURATION_MS = 700L;
    private static final Text NO_ABILITY_PROMPT = Text.literal(
        "\u672a\u68c0\u6d4b\u5230\u53ef\u7528\u80fd\u529b\uff0c\u8bf7\u4f7f\u7528\u80fd\u529b\u5f00\u53d1\u673a\u6fc0\u6d3b"
    );

    private static boolean hudVisible;
    private static boolean cursorVisible;
    private static float transitionStartProgress;
    private static long transitionStartTime;
    private static boolean transitionActive;

    private static float abilityCurrent = 100.0f;
    private static float abilityMax = 100.0f;
    private static Text abilityPromptLabel;
    private static AbilityType abilityType = AbilityType.NONE;

    public static void toggleHUD() {
        transitionStartProgress = getHudAnimationProgress();
        hudVisible = !hudVisible;
        transitionStartTime = System.currentTimeMillis();
        transitionActive = true;

        if (!hudVisible) {
            cursorVisible = false;
        }
    }

    public static boolean isHUDVisible() {
        return hudVisible;
    }

    public static boolean shouldRenderHUD() {
        return hudVisible || getHudAnimationProgress() > 0.001f;
    }

    public static boolean isHUDInteractive() {
        return hudVisible && getHudAnimationProgress() >= 0.98f;
    }

    public static float getHudAnimationProgress() {
        if (!transitionActive) {
            return hudVisible ? 1.0f : 0.0f;
        }

        long duration = hudVisible ? HUD_OPEN_DURATION_MS : HUD_CLOSE_DURATION_MS;
        float elapsed = (float)(System.currentTimeMillis() - transitionStartTime) / duration;
        float normalized = Math.max(0.0f, Math.min(elapsed, 1.0f));
        float eased = smoothStep(normalized);
        float target = hudVisible ? 1.0f : 0.0f;
        float progress = transitionStartProgress + (target - transitionStartProgress) * eased;

        if (normalized >= 1.0f) {
            transitionActive = false;
            return target;
        }
        return progress;
    }

    public static boolean isCursorVisible() {
        return cursorVisible;
    }

    public static void setCursorVisible(boolean visible) {
        cursorVisible = visible;
    }

    public static void setAbilityPromptLabel(Text label) {
        abilityPromptLabel = label;
    }

    public static void clearAbilityPromptLabel() {
        abilityPromptLabel = null;
    }

    public static Text getAbilityPromptLabel() {
        if (abilityPromptLabel == null && !hasActiveAbility()) {
            return NO_ABILITY_PROMPT;
        }
        return abilityPromptLabel;
    }

    public static void setAbilityType(AbilityType type) {
        abilityType = type == null ? AbilityType.NONE : type;
    }

    public static void clearAbilityType() {
        abilityType = AbilityType.NONE;
    }

    public static AbilityType getAbilityType() {
        return abilityType;
    }

    public static boolean hasActiveAbility() {
        return abilityType != AbilityType.NONE;
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
        transitionStartProgress = 0.0f;
        transitionStartTime = 0L;
        transitionActive = false;
        abilityPromptLabel = null;
        abilityType = AbilityType.NONE;
    }

    private static float smoothStep(float value) {
        return value * value * (3.0f - 2.0f * value);
    }
}
