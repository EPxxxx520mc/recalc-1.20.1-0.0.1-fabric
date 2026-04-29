package com.rimeveil.recalc.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.text.Style;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class RecalcHudRenderer {
    private static final Identifier CUSTOM_FONT_ID = new Identifier("recalc", "recalc_font");
    
    private static final String LOGO_TEXT = "Recalc";
    private static final int BAR_WIDTH = 100;
    private static final int BAR_HEIGHT = 8;
    private static final int LOGO_Y_OFFSET = 100;
    private static final int ATTACH_X_OFFSET = 150;
    private static final int REMOVE_X_OFFSET = 50;
    private static final int GLOW_LAYERS = 3;
    private static final float GLOW_BASE_OFFSET = 2f;
    private static final float GLOW_BASE_ALPHA = 0.3f;
    private static final float GLOW_ALPHA_DECREMENT = 0.1f;
    private static final float FADE_PERCENT = 0.1f;
    private static final int BORDER_ALPHA_MULTIPLIER = 150;
    private static final int BAR_BG_ALPHA_MULTIPLIER = 100;
    private static final int BAR_FG_ALPHA_MULTIPLIER = 200;

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

        renderAnimation(context, client, logoX, logoY, alpha, progress, 0xFFFFFF);
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

        renderAnimation(context, client, logoX, logoY, alpha, progress, 0xFF3333);
    }

    private static void renderAnimation(DrawContext context, MinecraftClient client, 
                                        int logoX, int logoY, float alpha, float progress, 
                                        int color) {
        Text styledText = Text.literal(LOGO_TEXT).setStyle(Style.EMPTY.withFont(CUSTOM_FONT_ID));
        
        int textWidth = client.textRenderer.getWidth(styledText);
        float scale = (float) BAR_WIDTH / textWidth;
        int textX = logoX + BAR_WIDTH / 2;
        int textY = logoY;

        int textColor = color | ((int) (alpha * 255) << 24);

        MatrixStack matrices = context.getMatrices();
        matrices.push();
        matrices.translate(textX, textY, 0);
        matrices.scale(scale, scale, 1);
        matrices.translate(-textWidth / 2f, 0, 0);

        for (int i = 0; i < GLOW_LAYERS; i++) {
            int offset = (int) ((GLOW_LAYERS - i) * GLOW_BASE_OFFSET);
            int glowAlpha = (int) (alpha * (GLOW_BASE_ALPHA - i * GLOW_ALPHA_DECREMENT) * 255);
            if (glowAlpha > 0) {
                int glowColor = color | (glowAlpha << 24);
                context.drawText(client.textRenderer, styledText, offset, offset, glowColor, false);
                context.drawText(client.textRenderer, styledText, -offset, -offset, glowColor, false);
            }
        }

        context.drawText(client.textRenderer, styledText, 0, 0, textColor, false);
        matrices.pop();

        int barX = logoX;
        int barY = logoY + (int) (client.textRenderer.fontHeight * scale) + 10;
        
        context.fill(barX, barY, barX + BAR_WIDTH, barY + BAR_HEIGHT, 
                    (int) (alpha * BAR_BG_ALPHA_MULTIPLIER) << 24 | 0x333333);
        int filledWidth = (int) (progress * BAR_WIDTH);
        context.fill(barX, barY, barX + filledWidth, barY + BAR_HEIGHT, 
                    (int) (alpha * BAR_FG_ALPHA_MULTIPLIER) << 24 | color);
        drawBorder(context, barX, barY, BAR_WIDTH, BAR_HEIGHT, alpha, color);
    }

    private static void drawBorder(DrawContext context, int x, int y, int width, int height, float alpha, int color) {
        int alphaInt = (int) (alpha * BORDER_ALPHA_MULTIPLIER) << 24;
        context.fill(x - 1, y - 1, x + width + 1, y, alphaInt | color);
        context.fill(x - 1, y + height, x + width + 1, y + height + 1, alphaInt | color);
        context.fill(x - 1, y, x, y + height, alphaInt | color);
        context.fill(x + width, y, x + width + 1, y + height, alphaInt | color);
    }
}
