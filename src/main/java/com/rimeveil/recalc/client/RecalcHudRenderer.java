package com.rimeveil.recalc.client;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public class RecalcHudRenderer {
    public static void register() {
        HudRenderCallback.EVENT.register(RecalcHudRenderer::render);
    }

    private static void render(DrawContext context, float tickDelta) {
        if (!AnimationState.isActive()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null) {
            return;
        }

        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        
        float alpha = AnimationState.getFadeInProgress() * AnimationState.getFadeOutProgress();
        float glowIntensity = AnimationState.getGlowIntensity();
        float progress = AnimationState.getProgress();

        int logoX = screenWidth - 150;
        int logoY = screenHeight - 100;

        String logoText = "Recalc";
        int textWidth = client.textRenderer.getWidth(logoText);
        int textX = logoX + 50 - textWidth / 2;
        int textY = logoY;

        int glowColor = 0xFFFFFF | ((int) (glowIntensity * 80 + 175) << 24);
        int textColor = 0xFFFFFF | ((int) (alpha * 255) << 24);

        // Draw glow effect (multiple layers)
        for (int i = 0; i < 3; i++) {
            int offset = (3 - i) * 2;
            int glowAlpha = (int) (alpha * (0.3f - i * 0.1f) * 255);
            if (glowAlpha > 0) {
                context.drawText(
                    client.textRenderer,
                    Text.literal(logoText),
                    textX + offset,
                    textY + offset,
                    0xFFFFFF | (glowAlpha << 24),
                    false
                );
                context.drawText(
                    client.textRenderer,
                    Text.literal(logoText),
                    textX - offset,
                    textY - offset,
                    0xFFFFFF | (glowAlpha << 24),
                    false
                );
            }
        }

        // Draw main text
        context.drawText(
            client.textRenderer,
            Text.literal(logoText),
            textX,
            textY,
            textColor,
            false
        );

        // Draw progress bar background
        int barX = logoX;
        int barY = logoY + 20;
        int barWidth = 100;
        int barHeight = 8;
        
        context.fill(
            barX, barY,
            barX + barWidth, barY + barHeight,
            (int) (alpha * 100) << 24 | 0x333333
        );

        // Draw progress bar foreground
        int filledWidth = (int) (progress * barWidth);
        context.fill(
            barX, barY,
            barX + filledWidth, barY + barHeight,
            (int) (alpha * 200) << 24 | 0xFFFFFF
        );

        // Draw border
        context.fill(
            barX - 1, barY - 1,
            barX + barWidth + 1, barY,
            (int) (alpha * 150) << 24 | 0xFFFFFF
        );
        context.fill(
            barX - 1, barY + barHeight,
            barX + barWidth + 1, barY + barHeight + 1,
            (int) (alpha * 150) << 24 | 0xFFFFFF
        );
        context.fill(
            barX - 1, barY,
            barX, barY + barHeight,
            (int) (alpha * 150) << 24 | 0xFFFFFF
        );
        context.fill(
            barX + barWidth, barY,
            barX + barWidth + 1, barY + barHeight,
            (int) (alpha * 150) << 24 | 0xFFFFFF
        );
    }
}
