package com.rimeveil.recalc.client;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;

public final class HudRenderUtils {
    private static final int[][] INNER_GLOW_OFFSETS = {
        {-1, -1}, {0, -1}, {1, -1},
        {-1, 0},           {1, 0},
        {-1, 1},  {0, 1},  {1, 1}
    };

    private static final int[][] OUTER_GLOW_OFFSETS = {
        {-2, 0}, {2, 0}, {0, -2}, {0, 2},
        {-2, -1}, {-1, -2}, {1, -2}, {2, -1},
        {-2, 1}, {-1, 2}, {1, 2}, {2, 1}
    };

    private HudRenderUtils() {
    }

    public static void drawGlowingText(
        DrawContext context,
        TextRenderer renderer,
        Text text,
        int x,
        int y,
        int textRgb,
        int glowRgb,
        float opacity,
        boolean shadow
    ) {
        float alpha = clamp(opacity, 0.0f, 1.0f);
        int outerColor = withAlpha(glowRgb, Math.round(24 * alpha));
        int innerColor = withAlpha(glowRgb, Math.round(62 * alpha));

        for (int[] offset : OUTER_GLOW_OFFSETS) {
            context.drawText(renderer, text, x + offset[0], y + offset[1], outerColor, false);
        }
        for (int[] offset : INNER_GLOW_OFFSETS) {
            context.drawText(renderer, text, x + offset[0], y + offset[1], innerColor, false);
        }

        context.drawText(renderer, text, x, y, withAlpha(textRgb, Math.round(255 * alpha)), shadow);
    }

    public static int withAlpha(int rgb, int alpha) {
        return (Math.max(0, Math.min(alpha, 255)) << 24) | (rgb & 0x00FFFFFF);
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }
}
