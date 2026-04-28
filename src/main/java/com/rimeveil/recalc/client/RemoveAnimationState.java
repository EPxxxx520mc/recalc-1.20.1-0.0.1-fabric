package com.rimeveil.recalc.client;

/**
 * ================================================
 * 🎬 移除动画状态管理类
 * ================================================
 * 
 * 【功能】
 * - 管理移除框架时的动画状态
 * - 支持倒序进度（从 1.0 到 0.0）
 * - 提供完整的动画进度查询接口
 * 
 * 【引用位置】
 * - ModNetworking.java → 收到移除信号时调用 start()
 * - RecalcHudRenderer.java → 查询状态和进度进行渲染
 * - ClientEventHandlers.java → 每帧调用 update()
 * 
 * 【使用方法】
 * 1. 启动动画：RemoveAnimationState.start();
 * 2. 每帧更新：RemoveAnimationState.update();
 * 3. 查询进度：RemoveAnimationState.getReverseProgress();
 * 4. 查询状态：RemoveAnimationState.isActive()/isComplete()
 * 
 * 【如何扩展】
 * - 修改 DURATION 调整动画时长
 * - 添加新的进度计算方法（如缓动效果）
 * - 添加更多状态查询接口
 * ================================================
 */
public class RemoveAnimationState {
    // ==================== 配置参数 ====================
    private static boolean active = false;          // 动画是否正在播放
    private static long startTime = 0;              // 动画开始时间（毫秒）
    private static boolean complete = false;        // 动画是否已完成
    private static final long DURATION = 3000;      // 动画总时长（3秒）

    // ==================== 核心方法 ====================
    
    /**
     * 【启动动画】
     * 调用位置：ModNetworking.java → 收到移除框架信号时
     * 功能：重置所有状态，开始播放动画
     */
    public static void start() {
        active = true;
        complete = false;
        startTime = System.currentTimeMillis();
    }

    /**
     * 【更新动画】
     * 调用位置：ClientEventHandlers.java → 每帧调用
     * 功能：根据时间更新动画状态，自动判断是否完成
     */
    public static void update() {
        if (active && !complete) {
            long elapsed = System.currentTimeMillis() - startTime;
            if (elapsed >= DURATION) {
                complete = true;
                active = false;
            }
        }
    }

    // ==================== 状态查询方法 ====================
    
    /**
     * 【查询是否正在播放】
     * 调用位置：RecalcHudRenderer.java → 判断是否渲染
     * 返回：true=播放中，false=未播放
     */
    public static boolean isActive() {
        return active;
    }

    /**
     * 【查询是否已完成】
     * 调用位置：任意需要知道动画是否结束的地方
     * 返回：true=已完成，false=进行中
     */
    public static boolean isComplete() {
        return complete;
    }

    // ==================== 进度计算方法 ====================
    
    /**
     * 【获取正向进度】
     * 范围：0.0（开始）~ 1.0（结束）
     * 用途：用于淡入淡出等效果
     */
    public static float getProgress() {
        if (!active && !complete) return 0.0f;
        if (complete) return 1.0f;
        long elapsed = System.currentTimeMillis() - startTime;
        return Math.min((float) elapsed / (float) DURATION, 1.0f);
    }

    /**
     * 【获取倒序进度（核心功能！）】
     * 范围：1.0（开始）~ 0.0（结束）
     * 用途：用于倒着走的进度条！
     * 调用位置：RecalcHudRenderer.java → 绘制倒序进度条
     */
    public static float getReverseProgress() {
        return 1.0f - getProgress();
    }

    /**
     * 【获取淡入进度】
     * 范围：0.0~1.0，在前 10% 时间内完成淡入
     * 用途：文字/图片慢慢出现
     */
    public static float getFadeInProgress() {
        float progress = getProgress();
        if (progress < 0.1f) {
            return progress / 0.1f;
        }
        return 1.0f;
    }

    /**
     * 【获取淡出进度】
     * 范围：0.0~1.0，在后 10% 时间内完成淡出
     * 用途：文字/图片慢慢消失
     */
    public static float getFadeOutProgress() {
        float progress = getProgress();
        if (progress > 0.9f) {
            return (1.0f - progress) / 0.1f;
        }
        return 1.0f;
    }

    /**
     * 【获取发光强度】
     * 范围：0.0~1.0，正弦波循环闪烁
     * 用途：营造炫酷的发光效果
     * 循环次数：整个动画期间闪烁 4 次
     */
    public static float getGlowIntensity() {
        float progress = getProgress();
        return (float) (0.5 + 0.5 * Math.sin(progress * Math.PI * 4));
    }
}
