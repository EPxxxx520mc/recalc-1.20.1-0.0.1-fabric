package com.rimeveil.recalc.client;

import com.rimeveil.recalc.util.LogUtil;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AnimationManager {
    public static final String ID_ATTACH = "attach";
    public static final String ID_REMOVE = "remove";
    public static final long DEFAULT_DURATION = 3000;
    private static final long CLEANUP_INTERVAL = 30000;
    private static final long MAX_ANIMATION_AGE = 60000;

    private static final Map<String, AnimationData> ANIMATIONS = new HashMap<>();
    private static long lastCleanupTime;

    public static void start(String id) {
        start(id, DEFAULT_DURATION);
    }

    public static void start(String id, long duration) {
        ANIMATIONS.put(id, new AnimationData(Math.max(1L, duration), System.currentTimeMillis()));
        LogUtil.debug("Started animation: {} (duration: {}ms)", id, duration);
    }

    public static void update(String id) {
        AnimationData data = ANIMATIONS.get(id);
        if (data != null) {
            updateData(id, data, System.currentTimeMillis());
        }
    }

    public static void updateAll() {
        long currentTime = System.currentTimeMillis();

        for (Map.Entry<String, AnimationData> entry : ANIMATIONS.entrySet()) {
            updateData(entry.getKey(), entry.getValue(), currentTime);
        }

        if (currentTime - lastCleanupTime > CLEANUP_INTERVAL) {
            cleanupOldAnimations(currentTime);
            lastCleanupTime = currentTime;
        }
    }

    public static boolean isActive(String id) {
        AnimationData data = ANIMATIONS.get(id);
        return data != null && data.active;
    }

    public static boolean isComplete(String id) {
        AnimationData data = ANIMATIONS.get(id);
        return data != null && data.complete;
    }

    public static void stop(String id) {
        ANIMATIONS.remove(id);
    }

    public static void stopAll() {
        ANIMATIONS.clear();
    }

    public static float getProgress(String id) {
        AnimationData data = ANIMATIONS.get(id);
        if (data == null) {
            return 0.0f;
        }
        if (data.complete) {
            return 1.0f;
        }
        if (!data.active) {
            return 0.0f;
        }

        long elapsed = System.currentTimeMillis() - data.startTime;
        return Math.min((float) elapsed / (float) data.duration, 1.0f);
    }

    public static float getReverseProgress(String id) {
        return 1.0f - getProgress(id);
    }

    public static float getFadeInProgress(String id, float fadePercent) {
        if (fadePercent <= 0.0f) {
            return 1.0f;
        }

        float progress = getProgress(id);
        return progress < fadePercent ? progress / fadePercent : 1.0f;
    }

    public static float getFadeOutProgress(String id, float fadePercent) {
        if (fadePercent <= 0.0f) {
            return 1.0f;
        }

        float progress = getProgress(id);
        return progress > 1.0f - fadePercent ? (1.0f - progress) / fadePercent : 1.0f;
    }

    public static float getGlowIntensity(String id, float cycleCount) {
        float progress = getProgress(id);
        return (float) (0.5 + 0.5 * Math.sin(progress * Math.PI * cycleCount));
    }

    private static void updateData(String id, AnimationData data, long currentTime) {
        if (!data.active || data.complete) {
            return;
        }

        if (currentTime - data.startTime >= data.duration) {
            data.complete = true;
            data.active = false;
            LogUtil.debug("Animation completed: {}", id);
        }
    }

    private static void cleanupOldAnimations(long currentTime) {
        Iterator<Map.Entry<String, AnimationData>> iterator = ANIMATIONS.entrySet().iterator();
        int removedCount = 0;

        while (iterator.hasNext()) {
            AnimationData data = iterator.next().getValue();
            if (data.complete && currentTime - data.startTime > MAX_ANIMATION_AGE) {
                iterator.remove();
                removedCount++;
            }
        }

        if (removedCount > 0) {
            LogUtil.debug("Cleaned up {} old animations", removedCount);
        }
    }

    private static class AnimationData {
        private boolean active = true;
        private boolean complete;
        private final long startTime;
        private final long duration;

        private AnimationData(long duration, long startTime) {
            this.duration = duration;
            this.startTime = startTime;
        }
    }
}
