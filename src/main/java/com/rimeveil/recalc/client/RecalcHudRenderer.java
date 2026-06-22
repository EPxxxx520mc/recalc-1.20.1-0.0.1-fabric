package com.rimeveil.recalc.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.client.util.math.MatrixStack;
import com.rimeveil.recalc.Recalc;

public class RecalcHudRenderer {
    private static final net.minecraft.util.Identifier CUSTOM_FONT_ID = Recalc.id("recalc_font");
    
    private static final String LOGO_TEXT = "Recalc";
    private static final int BAR_WIDTH = 100;
    private static final int BAR_HEIGHT = 8;
    private static final int LOGO_Y_OFFSET = 100;
    private static final int ATTACH_X_OFFSET = 150;
    private static final int REMOVE_X_OFFSET = 50;
    private static final float FADE_PERCENT = 0.1f;
    private static final int BAR_SLANT = 7;

    public static void register() {
        HudRenderCallback.EVENT.register(RecalcHudRenderer::render);
    }

    private static void render(DrawContext context, float tickDelta) {
        renderAttachAnimation(context);
        renderRemoveAnimation(context);
    }

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
        
        float alpha = AnimationManager.getFadeInProgress(AnimationManager.ID_ATTACH, FADE_PERCENT) 
                    * AnimationManager.getFadeOutProgress(AnimationManager.ID_ATTACH, FADE_PERCENT);
        float progress = AnimationManager.getProgress(AnimationManager.ID_ATTACH);

        int logoX = screenWidth - ATTACH_X_OFFSET;
        int logoY = screenHeight - LOGO_Y_OFFSET;

        renderAnimation(context, client, logoX, logoY, alpha, progress, 0xFFFFFF, 0xB9F3FA);
    }

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
        
        float alpha = AnimationManager.getFadeInProgress(AnimationManager.ID_REMOVE, FADE_PERCENT) 
                    * AnimationManager.getFadeOutProgress(AnimationManager.ID_REMOVE, FADE_PERCENT);
        float progress = AnimationManager.getReverseProgress(AnimationManager.ID_REMOVE);

        int logoX = REMOVE_X_OFFSET;
        int logoY = screenHeight - LOGO_Y_OFFSET;

        renderAnimation(context, client, logoX, logoY, alpha, progress, 0xFFFFFF, 0xD8D2FA);
    }

    private static void renderAnimation(DrawContext context, MinecraftClient client, 
                                        int logoX, int logoY, float alpha, float progress, 
                                        int color, int glowColor) {
        Text styledText = Text.literal(LOGO_TEXT).setStyle(Style.EMPTY.withFont(CUSTOM_FONT_ID));
        
        int textWidth = client.textRenderer.getWidth(styledText);
        float scale = (float) BAR_WIDTH / textWidth;
        int textX = logoX + BAR_WIDTH / 2;
        int textY = logoY;

        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(textX, textY, 0);
        matrices.scale(scale, scale, 1);
        matrices.translate(-textWidth / 2f, 0, 0);

        HudRenderUtils.drawGlowingText(
            context,
            client.textRenderer,
            styledText,
            0,
            0,
            color,
            glowColor,
            alpha,
            false
        );
        matrices.pop();

        int barX = logoX;
        int barY = logoY + (int) (client.textRenderer.fontHeight * scale) + 10;

        int glowAlpha = (int)(alpha * 34);
        int barGlowColor = HudRenderUtils.withAlpha(glowColor, glowAlpha);
        drawParallelogram(context, barX - 2, barY - 2, BAR_WIDTH + 4, BAR_HEIGHT + 4, BAR_SLANT, barGlowColor);
        drawParallelogram(
            context,
            barX,
            barY,
            BAR_WIDTH,
            BAR_HEIGHT,
            BAR_SLANT - 1,
            HudRenderUtils.withAlpha(0xFFFFFF, (int)(alpha * 170))
        );
        drawParallelogram(
            context,
            barX + 2,
            barY + 1,
            BAR_WIDTH - 4,
            BAR_HEIGHT - 2,
            BAR_SLANT - 3,
            HudRenderUtils.withAlpha(0x000000, (int)(alpha * 42))
        );
        int filledWidth = (int) (progress * BAR_WIDTH);
        drawParallelogram(
            context,
            barX + 2,
            barY + 1,
            Math.max(0, filledWidth - 4),
            BAR_HEIGHT - 2,
            Math.min(BAR_SLANT - 3, Math.max(0, filledWidth - 4)),
            HudRenderUtils.withAlpha(glowColor, (int)(alpha * 230))
        );
    }

    private static void drawParallelogram(DrawContext context, int x, int y, int width, int height, int slant, int color) {
        if (width <= 0 || height <= 0) {
            return;
        }

        for (int row = 0; row < height; row++) {
            float progress = height == 1 ? 0.0f : (float)row / (height - 1);
            int offset = Math.round(slant * (1.0f - progress));
            context.fill(x + offset, y + row, x + offset + width, y + row + 1, color);
        }
    }
}
