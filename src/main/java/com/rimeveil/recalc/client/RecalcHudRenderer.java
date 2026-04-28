package com.rimeveil.recalc.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

/**
 * ================================================
 * 🎨 HUD 渲染器（使用 Text.withFont() 指定字体）
 * ================================================
 */
public class RecalcHudRenderer {
    // 自定义字体 ID（对应 assets/recalc/font/recalc_font.json）
    private static final Identifier CUSTOM_FONT_ID = new Identifier("recalc", "recalc_font");

    public static void register() {
        HudRenderCallback.EVENT.register(RecalcHudRenderer::render);
    }

    private static void render(DrawContext context, float tickDelta) {
        renderAttachAnimation(context);
        renderRemoveAnimation(context);
    }

    // ==========================================
    // 🎬 渲染附着动画（右下角白色）
    // ==========================================
    private static void renderAttachAnimation(DrawContext context) {
        if (!AnimationManager.isActive(AnimationManager.ID_ATTACH)) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) {
            return;
        }

        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        
        float alpha = AnimationManager.getFadeInProgress(AnimationManager.ID_ATTACH, 0.1f) 
                    * AnimationManager.getFadeOutProgress(AnimationManager.ID_ATTACH, 0.1f);
        float progress = AnimationManager.getProgress(AnimationManager.ID_ATTACH);

        int logoX = screenWidth - 150;
        int logoY = screenHeight - 100;
        int barWidth = 100;

        String logoText = "Recalc";
        Text styledText = Text.literal(logoText).setStyle(Style.EMPTY.withFont(CUSTOM_FONT_ID));
        
        int textWidth = client.textRenderer.getWidth(styledText);
        float scale = (float) barWidth / textWidth;
        int textX = logoX + barWidth / 2;
        int textY = logoY;

        int textColor = 0xFFFFFF | ((int) (alpha * 255) << 24);

        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(textX, textY, 0);
        matrices.scale(scale, scale, 1);
        matrices.translate(-textWidth / 2f, 0, 0);

        for (int i = 0; i < 3; i++) {
            int offset = (3 - i) * 2;
            int glowAlpha = (int) (alpha * (0.3f - i * 0.1f) * 255);
            if (glowAlpha > 0) {
                context.drawText(
                    client.textRenderer,
                    styledText,
                    offset,
                    offset,
                    0xFFFFFF | (glowAlpha << 24),
                    false
                );
                context.drawText(
                    client.textRenderer,
                    styledText,
                    -offset,
                    -offset,
                    0xFFFFFF | (glowAlpha << 24),
                    false
                );
            }
        }

        context.drawText(
            client.textRenderer,
            styledText,
            0,
            0,
            textColor,
            false
        );

        matrices.pop();

        int barX = logoX;
        int barY = logoY + (int) (client.textRenderer.fontHeight * scale) + 10;
        int barHeight = 8;
        
        context.fill(barX, barY, barX + barWidth, barY + barHeight, (int) (alpha * 100) << 24 | 0x333333);
        int filledWidth = (int) (progress * barWidth);
        context.fill(barX, barY, barX + filledWidth, barY + barHeight, (int) (alpha * 200) << 24 | 0xFFFFFF);
        drawBorder(context, barX, barY, barWidth, barHeight, alpha, 0xFFFFFF);
    }

    // ==========================================
    // 🔴 渲染移除动画（左下角红色）
    // ==========================================
    private static void renderRemoveAnimation(DrawContext context) {
        if (!AnimationManager.isActive(AnimationManager.ID_REMOVE)) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) {
            return;
        }

        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        
        float alpha = AnimationManager.getFadeInProgress(AnimationManager.ID_REMOVE, 0.1f) 
                    * AnimationManager.getFadeOutProgress(AnimationManager.ID_REMOVE, 0.1f);
        float reverseProgress = AnimationManager.getReverseProgress(AnimationManager.ID_REMOVE);

        int logoX = 50;
        int logoY = screenHeight - 100;
        int barWidth = 100;

        String logoText = "Recalc";
        Text styledText = Text.literal(logoText).setStyle(Style.EMPTY.withFont(CUSTOM_FONT_ID));
        
        int textWidth = client.textRenderer.getWidth(styledText);
        float scale = (float) barWidth / textWidth;
        int textX = logoX + barWidth / 2;
        int textY = logoY;

        int textColor = 0xFF3333 | ((int) (alpha * 255) << 24);

        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(textX, textY, 0);
        matrices.scale(scale, scale, 1);
        matrices.translate(-textWidth / 2f, 0, 0);

        for (int i = 0; i < 3; i++) {
            int offset = (3 - i) * 2;
            int glowAlpha = (int) (alpha * (0.3f - i * 0.1f) * 255);
            if (glowAlpha > 0) {
                context.drawText(
                    client.textRenderer,
                    styledText,
                    offset,
                    offset,
                    0xFF3333 | (glowAlpha << 24),
                    false
                );
                context.drawText(
                    client.textRenderer,
                    styledText,
                    -offset,
                    -offset,
                    0xFF3333 | (glowAlpha << 24),
                    false
                );
            }
        }

        context.drawText(
            client.textRenderer,
            styledText,
            0,
            0,
            textColor,
            false
        );

        matrices.pop();

        int barX = logoX;
        int barY = logoY + (int) (client.textRenderer.fontHeight * scale) + 10;
        int barHeight = 8;
        
        context.fill(barX, barY, barX + barWidth, barY + barHeight, (int) (alpha * 100) << 24 | 0x333333);
        int filledWidth = (int) (reverseProgress * barWidth);
        context.fill(barX, barY, barX + filledWidth, barY + barHeight, (int) (alpha * 200) << 24 | 0xFF3333);
        drawBorder(context, barX, barY, barWidth, barHeight, alpha, 0xFF3333);
    }

    private static void drawBorder(DrawContext context, int x, int y, int width, int height, float alpha, int color) {
        int alphaInt = (int) (alpha * 150) << 24;
        context.fill(x - 1, y - 1, x + width + 1, y, alphaInt | color);
        context.fill(x - 1, y + height, x + width + 1, y + height + 1, alphaInt | color);
        context.fill(x - 1, y, x, y + height, alphaInt | color);
        context.fill(x + width, y, x + width + 1, y + height, alphaInt | color);
    }
}
