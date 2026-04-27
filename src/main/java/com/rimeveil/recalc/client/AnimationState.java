package com.rimeveil.recalc.client;

public class AnimationState {
    private static boolean active = false;
    private static long startTime = 0;
    private static boolean complete = false;
    private static final long DURATION = 3000; // 3 seconds total

    public static void start() {
        active = true;
        startTime = System.currentTimeMillis();
        complete = false;
    }

    public static void update() {
        if (active && !complete) {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= DURATION) {
                complete = true;
                active = false;
            }
        }
    }

    public static boolean isActive() {
        return active;
    }

    public static boolean isComplete() {
        return complete;
    }

    public static float getProgress() {
        if (!active && !complete) return 0.0f;
        if (complete) return 1.0f;
        long elapsed = System.currentTimeMillis() - startTime;
        return Math.min((float) elapsed / (float) DURATION, 1.0f);
    }

    public static float getFadeInProgress() {
        float progress = getProgress();
        if (progress < 0.1f) {
            return progress / 0.1f;
        }
        return 1.0f;
    }

    public static float getFadeOutProgress() {
        float progress = getProgress();
        if (progress > 0.9f) {
            return (1.0f - progress) / 0.1f;
        }
        return 1.0f;
    }

    public static float getGlowIntensity() {
        float progress = getProgress();
        return (float) (0.5 + 0.5 * Math.sin(progress * Math.PI * 4));
    }
}
