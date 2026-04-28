package com.rimeveil.recalc.client;

import com.rimeveil.recalc.Recalc;
import java.util.HashMap;
import java.util.Map;

/**
 * ================================================
 * 🎬 统一动画管理器
 * ================================================
 * 
 * 【功能】
 * - 用 ID 管理多个不同的动画
 * - 支持自定义时长
 * - 提供各种进度查询（正向、倒序、淡入、淡出、发光等）
 * 
 * 【使用示例】
 * 1. 启动动画：AnimationManager.start("my_animation", 3000);
 * 2. 更新动画：AnimationManager.update("my_animation");
 * 3. 查询进度：AnimationManager.getProgress("my_animation");
 * 
 * 【预定义动画 ID】
 * - "attach"  → 附着动画（原来的 AnimationState）
 * - "remove"  → 移除动画（原来的 RemoveAnimationState）
 * ================================================
 */
public class AnimationManager {
    // ==========================================
    // 🏷️ 预定义动画 ID（方便使用）
    // ==========================================
    public static final String ATTACH = "attach";  // 附着动画
    public static final String REMOVE = "remove";  // 移除动画
    
    // ==========================================
    // 📦 内部类：单个动画的状态
    // ==========================================
    private static class AnimationData {
        boolean active = false;
        boolean complete = false;
        long startTime = 0;
        long duration = 3000;  // 默认 3 秒
        
        AnimationData(long duration) {
            this.duration = duration;
        }
    }
    
    // ==========================================
    // 🗃️ 存储所有动画状态
    // ==========================================
    private static final Map<String, AnimationData> animations = new HashMap<>();
    
    // ==========================================
    // 🚀 核心方法：启动动画
    // ==========================================
    /**
     * 启动动画（使用默认 3 秒时长）
     * 
     * @param id 动画 ID（例如 "attach"、"remove"、"my_anim"）
     */
    public static void start(String id) {
        start(id, 3000);  // 默认 3 秒
    }
    
    /**
     * 启动动画（自定义时长）
     * 
     * @param id        动画 ID
     * @param duration  动画时长（毫秒，1000 = 1秒）
     */
    public static void start(String id, long duration) {
        AnimationData data = new AnimationData(duration);
        data.active = true;
        data.complete = false;
        data.startTime = System.currentTimeMillis();
        animations.put(id, data);
        Recalc.LOGGER.info("🎬 启动动画: " + id + " (时长: " + duration + "ms)");
    }
    
    // ==========================================
    // 🔄 核心方法：更新动画
    // ==========================================
    /**
     * 更新单个动画
     * 
     * @param id 动画 ID
     */
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
    
    /**
     * 更新所有正在播放的动画
     * ⚡ 推荐：在 ClientEventHandlers 里用这个，不用一个个 update
     */
    public static void updateAll() {
        for (String id : animations.keySet()) {
            update(id);
        }
    }
    
    // ==========================================
    // 👀 核心方法：状态查询
    // ==========================================
    /**
     * 查询动画是否正在播放
     */
    public static boolean isActive(String id) {
        AnimationData data = animations.get(id);
        return data != null && data.active;
    }
    
    /**
     * 查询动画是否已完成
     */
    public static boolean isComplete(String id) {
        AnimationData data = animations.get(id);
        return data != null && data.complete;
    }
    
    /**
     * 停止并重置动画
     */
    public static void stop(String id) {
        animations.remove(id);
    }
    
    /**
     * 停止所有动画
     */
    public static void stopAll() {
        animations.clear();
    }
    
    // ==========================================
    // 📊 进度查询方法（各种都有！）
    // ==========================================
    /**
     * 获取正向进度（0.0 → 1.0）
     */
    public static float getProgress(String id) {
        AnimationData data = animations.get(id);
        if (data == null) return 0.0f;
        if (data.complete) return 1.0f;
        if (!data.active) return 0.0f;
        
        long elapsed = System.currentTimeMillis() - data.startTime;
        return Math.min((float) elapsed / (float) data.duration, 1.0f);
    }
    
    /**
     * 获取倒序进度（1.0 → 0.0）✅
     */
    public static float getReverseProgress(String id) {
        return 1.0f - getProgress(id);
    }
    
    /**
     * 获取淡入进度（前 fadePercent 时间从 0→1）
     * 
     * @param id           动画 ID
     * @param fadePercent  淡入占比（例如 0.1f = 前 10%）
     */
    public static float getFadeInProgress(String id, float fadePercent) {
        float progress = getProgress(id);
        if (progress < fadePercent) {
            return progress / fadePercent;
        }
        return 1.0f;
    }
    
    /**
     * 获取淡出进度（后 fadePercent 时间从 1→0）
     */
    public static float getFadeOutProgress(String id, float fadePercent) {
        float progress = getProgress(id);
        if (progress > 1.0f - fadePercent) {
            return (1.0f - progress) / fadePercent;
        }
        return 1.0f;
    }
    
    /**
     * 获取发光强度（正弦闪烁）
     * 
     * @param id          动画 ID
     * @param cycleCount  整个动画期间闪烁多少次
     */
    public static float getGlowIntensity(String id, float cycleCount) {
        float progress = getProgress(id);
        return (float) (0.5 + 0.5 * Math.sin(progress * Math.PI * cycleCount));
    }
    
    // ==========================================
    // 🎯 快捷方法（针对预定义动画）
    // ==========================================
    // 附着动画快捷方式
    public static void startAttach() { start(ATTACH, 3000); }
    public static boolean isAttachActive() { return isActive(ATTACH); }
    public static float getAttachProgress() { return getProgress(ATTACH); }
    public static float getAttachFadeIn() { return getFadeInProgress(ATTACH, 0.1f); }
    public static float getAttachFadeOut() { return getFadeOutProgress(ATTACH, 0.1f); }
    public static float getAttachGlow() { return getGlowIntensity(ATTACH, 4.0f); }
    
    // 移除动画快捷方式
    public static void startRemove() { start(REMOVE, 3000); }
    public static boolean isRemoveActive() { return isActive(REMOVE); }
    public static float getRemoveReverseProgress() { return getReverseProgress(REMOVE); }
    public static float getRemoveFadeIn() { return getFadeInProgress(REMOVE, 0.1f); }
    public static float getRemoveFadeOut() { return getFadeOutProgress(REMOVE, 0.1f); }
    public static float getRemoveGlow() { return getGlowIntensity(REMOVE, 4.0f); }
}
