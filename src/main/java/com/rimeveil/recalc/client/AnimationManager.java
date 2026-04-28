package com.rimeveil.recalc.client;

import com.rimeveil.recalc.Recalc;
import java.util.HashMap;
import java.util.Map;

/**
 * ================================================
 * 🎬 统一动画管理器（纯ID模式）
 * ================================================
 * 
 * 【功能】
 * - 用 ID 管理多个不同的动画
 * - 支持自定义时长
 * - 提供各种进度查询（正向、倒序、淡入、淡出、发光等）
 * 
 * 【使用示例】
 * 1. 启动动画：AnimationManager.start("attach", 3000);
 * 2. 更新动画：AnimationManager.updateAll();
 * 3. 查询进度：AnimationManager.getProgress("attach");
 * 
 * 【常用动画ID】
 * - "attach"  → 附着动画（右下角白色）
 * - "remove"  → 移除动画（左下角红色）
 * ================================================
 */
public class AnimationManager {
    // ==========================================
    // 🏷️ 常用动画 ID 常量（推荐使用这些）
    // ==========================================
    public static final String ID_ATTACH = "attach";
    public static final String ID_REMOVE = "remove";
    
    // ==========================================
    // 📦 内部类：单个动画的状态
    // ==========================================
    private static class AnimationData {
        boolean active = false;
        boolean complete = false;
        long startTime = 0;
        long duration = 3000;
        
        AnimationData(long duration) {
            this.duration = duration;
        }
    }
    
    // ==========================================
    // 🗃️ 存储所有动画状态
    // ==========================================
    private static final Map<String, AnimationData> animations = new HashMap<>();
    
    // ==========================================
    // 🚀 启动动画
    // ==========================================
    public static void start(String id) {
        start(id, 3000);
    }
    
    public static void start(String id, long duration) {
        AnimationData data = new AnimationData(duration);
        data.active = true;
        data.complete = false;
        data.startTime = System.currentTimeMillis();
        animations.put(id, data);
        Recalc.LOGGER.info("🎬 启动动画: " + id + " (时长: " + duration + "ms)");
    }
    
    // ==========================================
    // 🔄 更新动画
    // ==========================================
    public static void update(String id) {
        AnimationData data = animations.get(id);
        if (data == null) return;
        
        if (data.active && !data.complete) {
            long elapsed = System.currentTimeMillis() - data.startTime;
            if (elapsed >= data.duration) {
                data.complete = true;
                data.active = false;
                Recalc.LOGGER.info("✅ 动画完成: " + id);
            }
        }
    }
    
    public static void updateAll() {
        for (String id : animations.keySet()) {
            update(id);
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