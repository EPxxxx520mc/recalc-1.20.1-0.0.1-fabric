package com.rimeveil.recalc.client;

import com.rimeveil.recalc.util.LogUtil;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class AnimationManager {
    public static final String ID_ATTACH = "attach";
    public static final String ID_REMOVE = "remove";
    public static final long DEFAULT_DURATION = 3000;
    public static final long CLEANUP_INTERVAL = 30000;
    public static final long MAX_ANIMATION_AGE = 60000;
    
    private static class AnimationData {
        boolean active = false;
        boolean complete = false;
        long startTime = 0;
        long duration = DEFAULT_DURATION;
        
        AnimationData(long duration) {
            this.duration = duration;
        }
    }
    
    private static final Map<String, AnimationData> animations = new HashMap<>();
    private static long lastCleanupTime = 0;
    
    public static void start(String id) {
        start(id, DEFAULT_DURATION);
    }
    
    public static void start(String id, long duration) {
        AnimationData data = new AnimationData(duration);
        data.active = true;
        data.complete = false;
        data.startTime = System.currentTimeMillis();
        animations.put(id, data);
        LogUtil.debug("Started animation: {} (duration: {}ms)", id, duration);
    }
    
    public static void update(String id) {
        AnimationData data = animations.get(id);
        if (data == null) return;
        
        if (data.active && !data.complete) {
            long elapsed = System.currentTimeMillis() - data.startTime;
            if (elapsed >= data.duration) {
                data.complete = true;
                data.active = false;
                LogUtil.debug("Animation completed: {}", id);
            }
        }
    }
    
    public static void updateAll() {
        long currentTime = System.currentTimeMillis();
        
        for (String id : animations.keySet()) {
            update(id);
        }
        
        if (currentTime - lastCleanupTime > CLEANUP_INTERVAL) {
            cleanupOldAnimations(currentTime);
            lastCleanupTime = currentTime;
        }
    }
    
    private static void cleanupOldAnimations(long currentTime) {
        Iterator<Map.Entry<String, AnimationData>> iterator = animations.entrySet().iterator();
        int removedCount = 0;
        
        while (iterator.hasNext()) {
            Map.Entry<String, AnimationData> entry = iterator.next();
            AnimationData data = entry.getValue();
            
            if (data.complete && (currentTime - data.startTime) > MAX_ANIMATION_AGE) {
                iterator.remove();
                removedCount++;
            }
        }
        
        if (removedCount > 0) {
            LogUtil.debug("Cleaned up {} old animations", removedCount);
        }
    }
    
    // ==========================================
    // 👀 状态查询
    // ==========================================
    public static boolean isActive(String id) {
        AnimationData data = animations.get(id);
        return data != null && data.active;
    }
    
    public static boolean isComplete(String id) {
        AnimationData data = animations.get(id);
        return data != null && data.complete;
    }
    
    public static void stop(String id) {
        animations.remove(id);
    }
    
    public static void stopAll() {
        animations.clear();
    }
    
    // ==========================================
    // 📊 进度查询
    // ==========================================
    public static float getProgress(String id) {
        AnimationData data = animations.get(id);
        if (data == null) return 0.0f;
        if (data.complete) return 1.0f;
        if (!data.active) return 0.0f;
        
        long elapsed = System.currentTimeMillis() - data.startTime;
        return Math.min((float) elapsed / (float) data.duration, 1.0f);
    }
    
    public static float getReverseProgress(String id) {
        return 1.0f - getProgress(id);
    }
    
    public static float getFadeInProgress(String id, float fadePercent) {
        float progress = getProgress(id);
        if (progress < fadePercent) {
            return progress / fadePercent;
        }
        return 1.0f;
    }
    
    public static float getFadeOutProgress(String id, float fadePercent) {
        float progress = getProgress(id);
        if (progress > 1.0f - fadePercent) {
            return (1.0f - progress) / fadePercent;
        }
        return 1.0f;
    }
    
    public static float getGlowIntensity(String id, float cycleCount) {
        float progress = getProgress(id);
        return (float) (0.5 + 0.5 * Math.sin(progress * Math.PI * cycleCount));
    }
}