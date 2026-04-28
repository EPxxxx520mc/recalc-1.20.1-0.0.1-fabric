package com.rimeveil.recalc.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.client.util.math.MatrixStack;

/**
 * ================================================
 * 🎨 HUD 渲染器
 * ================================================
 * 
 * 【功能】
 * 1. 渲染右下角白色附着动画（Recalc 文字 + 进度条）
 * 2. 渲染左下角红色移除动画（Recalc 文字 + 倒序进度条）
 * 
 * 【引用位置】
 * - RecalcClient.java → 客户端初始化时调用 register()
 * 
 * 【如何添加新动画/图片】
 * 1. 创建新的动画状态类（参考 RemoveAnimationState.java）
 * 2. 在这个类里添加新的渲染方法
 * 3. 在 render() 里调用新方法
 * 4. 在 ClientEventHandlers 里添加 update() 调用
 * 5. 在 ModNetworking 里添加触发方式（可选）
 * 
 * 【如何添加图片】
 * 1. 把图片放在：src/main/resources/assets/recalc/textures/gui/xxx.png
 * 2. 使用 context.drawTexture() 绘制（见下文示例）
 * ================================================
 */
public class RecalcHudRenderer {
    /**
     * 【注册渲染回调】
     * 调用位置：RecalcClient.java → onInitializeClient()
     */
    public static void register() {
        HudRenderCallback.EVENT.register(RecalcHudRenderer::render);
    }

    /**
     * 【主渲染方法】
     * 每帧调用一次，按顺序渲染各个动画
     */
    private static void render(DrawContext context, float tickDelta) {
        // ========== 1. 渲染附着动画（右下角白色） ==========
        renderAttachAnimation(context);
        
        // ========== 2. 渲染移除动画（左下角红色）✅ ==========
        renderRemoveAnimation(context);
    }

    // ========================================================================
    // 🎬 方法1：渲染附着动画（右下角白色）
    // ========================================================================
    private static void renderAttachAnimation(DrawContext context) {
        if (!AnimationState.isActive()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) {
            return;
        }

        // 计算屏幕坐标（右下角）
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        
        float alpha = AnimationState.getFadeInProgress() * AnimationState.getFadeOutProgress();
        float glowIntensity = AnimationState.getGlowIntensity();
        float progress = AnimationState.getProgress();

        int logoX = screenWidth - 150;
        int logoY = screenHeight - 100;
        int barWidth = 100;

        String logoText = "Recalc";
        int textWidth = client.textRenderer.getWidth(logoText);
        float scale = (float) barWidth / textWidth;
        int textX = logoX + barWidth / 2;
        int textY = logoY;

        // 颜色：白色（0xFFFFFF）
        int glowColor = 0xFFFFFF | ((int) (glowIntensity * 80 + 175) << 24);
        int textColor = 0xFFFFFF | ((int) (alpha * 255) << 24);

        // 绘制文字（带缩放）
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(textX, textY, 0);
        matrices.scale(scale, scale, 1);
        matrices.translate(-textWidth / 2f, 0, 0);

        // 绘制发光效果（多层叠加）
        for (int i = 0; i < 3; i++) {
            int offset = (3 - i) * 2;
            int glowAlpha = (int) (alpha * (0.3f - i * 0.1f) * 255);
            if (glowAlpha > 0) {
                context.drawText(
                    client.textRenderer,
                    Text.literal(logoText),
                    offset,
                    offset,
                    0xFFFFFF | (glowAlpha << 24),
                    false
                );
                context.drawText(
                    client.textRenderer,
                    Text.literal(logoText),
                    -offset,
                    -offset,
                    0xFFFFFF | (glowAlpha << 24),
                    false
                );
            }
        }

        // 绘制主文字
        context.drawText(
            client.textRenderer,
            Text.literal(logoText),
            0,
            0,
            textColor,
            false
        );

        matrices.pop();

        // ========== 绘制进度条 ==========
        int barX = logoX;
        int barY = logoY + (int) (client.textRenderer.fontHeight * scale) + 10;
        int barHeight = 8;
        
        // 背景条
        context.fill(
            barX, barY,
            barX + barWidth, barY + barHeight,
            (int) (alpha * 100) << 24 | 0x333333
        );

        // 前景条（正向）
        int filledWidth = (int) (progress * barWidth);
        context.fill(
            barX, barY,
            barX + filledWidth, barY + barHeight,
            (int) (alpha * 200) << 24 | 0xFFFFFF
        );

        // 边框
        drawBorder(context, barX, barY, barWidth, barHeight, alpha, 0xFFFFFF);
    }

    // ========================================================================
    // 🔴 方法2：渲染移除动画（左下角红色）✅ 新增！
    // ========================================================================
    private static void renderRemoveAnimation(DrawContext context) {
        if (!RemoveAnimationState.isActive()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) {
            return;
        }

        // 计算屏幕坐标（左下角）
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        
        float alpha = RemoveAnimationState.getFadeInProgress() * RemoveAnimationState.getFadeOutProgress();
        float glowIntensity = RemoveAnimationState.getGlowIntensity();
        float reverseProgress = RemoveAnimationState.getReverseProgress(); // ✅ 倒序进度！

        int logoX = 50;           // 左边
        int logoY = screenHeight - 100;  // 底部
        int barWidth = 100;

        String logoText = "Recalc";
        int textWidth = client.textRenderer.getWidth(logoText);
        float scale = (float) barWidth / textWidth;
        int textX = logoX + barWidth / 2;
        int textY = logoY;

        // 颜色：红色（0xFF3333）✅
        int glowColor = 0xFF3333 | ((int) (glowIntensity * 80 + 175) << 24);
        int textColor = 0xFF3333 | ((int) (alpha * 255) << 24);

        // 绘制文字（带缩放）
        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(textX, textY, 0);
        matrices.scale(scale, scale, 1);
        matrices.translate(-textWidth / 2f, 0, 0);

        // 绘制发光效果（多层叠加，红色）
        for (int i = 0; i < 3; i++) {
            int offset = (3 - i) * 2;
            int glowAlpha = (int) (alpha * (0.3f - i * 0.1f) * 255);
            if (glowAlpha > 0) {
                context.drawText(
                    client.textRenderer,
                    Text.literal(logoText),
                    offset,
                    offset,
                    0xFF3333 | (glowAlpha << 24), // 红色
                    false
                );
                context.drawText(
                    client.textRenderer,
                    Text.literal(logoText),
                    -offset,
                    -offset,
                    0xFF3333 | (glowAlpha << 24), // 红色
                    false
                );
            }
        }

        // 绘制主文字（红色）
        context.drawText(
            client.textRenderer,
            Text.literal(logoText),
            0,
            0,
            textColor,
            false
        );

        matrices.pop();

        // ========== 绘制倒序进度条（红色）✅ ==========
        int barX = logoX;
        int barY = logoY + (int) (client.textRenderer.fontHeight * scale) + 10;
        int barHeight = 8;
        
        // 背景条
        context.fill(
            barX, barY,
            barX + barWidth, barY + barHeight,
            (int) (alpha * 100) << 24 | 0x333333
        );

        // 前景条（倒序！）✅
        int filledWidth = (int) (reverseProgress * barWidth);
        context.fill(
            barX, barY,
            barX + filledWidth, barY + barHeight,
            (int) (alpha * 200) << 24 | 0xFF3333 // 红色
        );

        // 边框（红色）
        drawBorder(context, barX, barY, barWidth, barHeight, alpha, 0xFF3333);
    }

    // ========================================================================
    // 🛠️ 工具方法：绘制边框
    // ========================================================================
    private static void drawBorder(DrawContext context, int x, int y, int width, int height, float alpha, int color) {
        int alphaInt = (int) (alpha * 150) << 24;
        // 上边框
        context.fill(x - 1, y - 1, x + width + 1, y, alphaInt | color);
        // 下边框
        context.fill(x - 1, y + height, x + width + 1, y + height + 1, alphaInt | color);
        // 左边框
        context.fill(x - 1, y, x, y + height, alphaInt | color);
        // 右边框
        context.fill(x + width, y, x + width + 1, y + height, alphaInt | color);
    }

    // ========================================================================
    // 📖 示例：如何添加图片渲染
    // ========================================================================
    /*
     * 【步骤】
     * 1. 放图片：src/main/resources/assets/recalc/textures/gui/my_image.png
     * 2. 写代码：
     *
     * private static void renderMyImage(DrawContext context) {
     *     MinecraftClient client = MinecraftClient.getInstance();
     *     Identifier texture = new Identifier(Recalc.MOD_ID, "textures/gui/my_image.png");
     *     
     *     // 绑定纹理
     *     RenderSystem.setShader(GameRenderer::getPositionTexProgram);
     *     RenderSystem.setShaderTexture(0, texture);
     *     
     *     // 绘制图片
     *     int x = 100;
     *     int y = 100;
     *     int u = 0;    // 纹理偏移
     *     int v = 0;
     *     int w = 64;   // 图片宽度
     *     int h = 64;   // 图片高度
     *     int texW = 64; // 纹理总宽度
     *     int texH = 64; // 纹理总高度
     *     context.drawTexture(texture, x, y, u, v, w, h, texW, texH);
     * }
     */
}
