package com.rimeveil.recalc.client;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import org.joml.Matrix4f;

public final class HudBackgroundBlur {
    private static final int[][] BLUR_OFFSETS = {
        {-2, 0}, {2, 0}, {0, -2}, {0, 2},
        {-3, -3}, {3, -3}, {-3, 3}, {3, 3},
        {-6, 0}, {6, 0}, {0, -6}, {0, 6},
        {-5, -5}, {5, -5}, {-5, 5}, {5, 5},
        {-8, -2}, {8, -2}, {-8, 2}, {8, 2},
        {-2, -8}, {2, -8}, {-2, 8}, {2, 8}
    };
    private static final int SAMPLE_ALPHA = 16;
    private static final int EDGE_PADDING = 10;

    private static SimpleFramebuffer snapshotFramebuffer;

    private HudBackgroundBlur() {
    }

    public static void render(DrawContext context, MinecraftClient client) {
        Framebuffer mainFramebuffer = client.getFramebuffer();
        if (mainFramebuffer.fbo <= 0) {
            return;
        }

        context.draw();
        ensureSnapshotFramebuffer(mainFramebuffer);
        copyFramebuffer(mainFramebuffer, snapshotFramebuffer);

        mainFramebuffer.beginWrite(false);
        snapshotFramebuffer.setTexFilter(GlConst.GL_LINEAR);

        int width = context.getScaledWindowWidth();
        int height = context.getScaledWindowHeight();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();

        for (int[] offset : BLUR_OFFSETS) {
            drawSnapshot(context, snapshotFramebuffer, width, height, offset[0], offset[1], SAMPLE_ALPHA);
        }

        RenderSystem.depthMask(true);
        RenderSystem.disableBlend();

        context.fillGradient(0, 0, width, height, 0x7204070B, 0x8A0A0F17);
        context.fill(0, 0, width, height, 0x26000000);
        context.fill(0, 0, width, height, 0x0EDDF8FF);
        drawFocusLines(context, width, height);
    }

    private static void ensureSnapshotFramebuffer(Framebuffer mainFramebuffer) {
        int width = mainFramebuffer.textureWidth;
        int height = mainFramebuffer.textureHeight;
        if (snapshotFramebuffer != null
            && snapshotFramebuffer.textureWidth == width
            && snapshotFramebuffer.textureHeight == height) {
            return;
        }

        release();
        snapshotFramebuffer = new SimpleFramebuffer(width, height, false, MinecraftClient.IS_SYSTEM_MAC);
        snapshotFramebuffer.setClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    }

    private static void copyFramebuffer(Framebuffer source, Framebuffer target) {
        GlStateManager._glBindFramebuffer(GlConst.GL_READ_FRAMEBUFFER, source.fbo);
        GlStateManager._glBindFramebuffer(GlConst.GL_DRAW_FRAMEBUFFER, target.fbo);
        GlStateManager._glBlitFrameBuffer(
            0,
            0,
            source.textureWidth,
            source.textureHeight,
            0,
            0,
            target.textureWidth,
            target.textureHeight,
            GlConst.GL_COLOR_BUFFER_BIT,
            GlConst.GL_NEAREST
        );
        GlStateManager._glBindFramebuffer(GlConst.GL_FRAMEBUFFER, source.fbo);
    }

    private static void drawSnapshot(
        DrawContext context,
        Framebuffer framebuffer,
        int width,
        int height,
        int offsetX,
        int offsetY,
        int alpha
    ) {
        float textureMaxU = (float)framebuffer.viewportWidth / framebuffer.textureWidth;
        float textureMaxV = (float)framebuffer.viewportHeight / framebuffer.textureHeight;
        float colorAlpha = alpha / 255.0f;
        int x1 = -EDGE_PADDING + offsetX;
        int y1 = -EDGE_PADDING + offsetY;
        int x2 = width + EDGE_PADDING + offsetX;
        int y2 = height + EDGE_PADDING + offsetY;

        RenderSystem.setShader(GameRenderer::getPositionColorTexProgram);
        RenderSystem.setShaderTexture(0, framebuffer.getColorAttachment());

        Matrix4f matrix = context.getMatrices().peek().getPositionMatrix();
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR_TEXTURE);
        bufferBuilder.vertex(matrix, x1, y2, 0.0f).color(1.0f, 1.0f, 1.0f, colorAlpha).texture(0.0f, 0.0f).next();
        bufferBuilder.vertex(matrix, x2, y2, 0.0f).color(1.0f, 1.0f, 1.0f, colorAlpha).texture(textureMaxU, 0.0f).next();
        bufferBuilder.vertex(matrix, x2, y1, 0.0f).color(1.0f, 1.0f, 1.0f, colorAlpha).texture(textureMaxU, textureMaxV).next();
        bufferBuilder.vertex(matrix, x1, y1, 0.0f).color(1.0f, 1.0f, 1.0f, colorAlpha).texture(0.0f, textureMaxV).next();
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

    private static void drawFocusLines(DrawContext context, int width, int height) {
        int centerY = height / 2;
        context.fill(0, centerY - 28, width, centerY - 27, 0x16FFFFFF);
        context.fill(0, centerY + 28, width, centerY + 29, 0x14B9F3FA);
    }

    public static void release() {
        if (snapshotFramebuffer != null) {
            snapshotFramebuffer.delete();
            snapshotFramebuffer = null;
        }
    }
}
