package com.rimeveil.recalc.client;

import com.rimeveil.recalc.Recalc;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class RecalcBattleHUD {
    private static final Identifier RECALC_FONT = Recalc.id("recalc_font");

    private static final int WHITE = 0xFFFFFFFF;
    private static final int WHITE_FAINT = 0x30FFFFFF;
    private static final int GLASS_BACKGROUND = 0x553A424A;
    private static final int GLASS_BACKGROUND_DARK = 0x7A22282E;
    private static final int PANEL_SHADOW = 0x5820252B;
    private static final int PANEL_STROKE = 0xC8E1E7EA;
    private static final int PANEL_STROKE_FAINT = 0x5CE1E7EA;
    private static final int CELL_EMPTY = 0x64D0D7DB;
    private static final int CELL_FILLED = 0xEAF4F7F8;
    private static final int ACCENT_CYAN = 0xFFC7F6FA;
    private static final int ACCENT_BLUE = 0xFFDCE5EA;
    private static final int ACCENT_LAVENDER = 0xFFE7E3F5;
    private static final int ABILITY_FIRE = 0xFFFFB06A;
    private static final int ABILITY_LIGHTNING = 0xFFF7F0A0;
    private static final int ABILITY_WIND = 0xFFC7F6FA;
    private static final int ABILITY_TELEPORT = 0xFFD9D0FF;
    private static final int ABILITY_VECTOR = 0xFFE7E3F5;
    private static final int TEXT_SHADOW = 0x80000000;

    private static final int ABILITY_X = 20;
    private static final int ABILITY_Y = 20;
    private static final int ABILITY_PANEL_WIDTH = 218;
    private static final int ABILITY_PANEL_HEIGHT = 30;
    private static final int ABILITY_BAR_X = 8;
    private static final int ABILITY_BAR_Y = 5;
    private static final int ABILITY_BAR_WIDTH = 166;
    private static final int ABILITY_BAR_HEIGHT = 10;
    private static final int ABILITY_BAR_SLANT = 5;
    private static final int ABILITY_SEGMENT_COUNT = 10;
    private static final int ABILITY_SEGMENT_GAP = 2;
    private static final int ABILITY_STATUS_X = 187;
    private static final int ABILITY_STATUS_Y = 3;
    private static final int ABILITY_STATUS_SIZE = 24;
    private static final int ABILITY_PROMPT_X = 12;
    private static final int ABILITY_PROMPT_Y = 18;
    private static final int ABILITY_PROMPT_RIGHT_PADDING = 8;
    private static final int ABILITY_PROMPT_TEXT_PADDING = 4;

    private static final int PLAYER_PANEL_MARGIN = 10;
    private static final int PLAYER_PANEL_PADDING = 4;
    private static final int PLAYER_LINE_HEIGHT = 10;
    private static final int LOGO_MARGIN = 20;
    private static final int LOGO_HEIGHT = 14;

    public static void register() {
        HudRenderCallback.EVENT.register(RecalcBattleHUD::render);
    }

    public static void renderInteractionOverlay(DrawContext context, MinecraftClient client, int mouseX, int mouseY) {
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();

        if (abilityBounds().contains(mouseX, mouseY)) {
            context.drawTooltip(client.textRenderer, getAbilityDetails(), mouseX, mouseY);
            return;
        }

        if (getPlayerInfoBounds(client, screenWidth, screenHeight).contains(mouseX, mouseY)) {
            context.drawTooltip(client.textRenderer, getPlayerDetails(client), mouseX, mouseY);
            return;
        }

    }

    private static void render(DrawContext context, float tickDelta) {
        if (!BattleHUDManager.shouldRenderHUD()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) {
            return;
        }

        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        float progress = BattleHUDManager.getHudAnimationProgress();
        boolean interactionVisible = BattleHUDManager.isCursorVisible();

        if (interactionVisible) {
            HudBackgroundBlur.render(context, client);
        }

        drawAbilityBarAnimated(context, client, stagedProgress(progress, 0.0f, 0.58f));
        if (interactionVisible) {
            drawPlayerInfoAnimated(context, client, screenWidth, screenHeight, 1.0f);
            drawRecalcLogoAnimated(context, client, LOGO_MARGIN, screenHeight - 30, 1.0f);
        }
    }

    private static void drawAbilityBarAnimated(DrawContext context, MinecraftClient client, float progress) {
        if (progress <= 0.0f) {
            return;
        }

        Bounds bounds = abilityBounds();
        int revealWidth = Math.max(1, Math.round(bounds.width * progress));
        context.enableScissor(bounds.x, bounds.y, bounds.x + revealWidth, bounds.bottom());
        drawAbilityBar(context, client);
        context.disableScissor();
    }

    private static void drawPlayerInfoAnimated(
        DrawContext context,
        MinecraftClient client,
        int screenWidth,
        int screenHeight,
        float progress
    ) {
        if (progress <= 0.0f) {
            return;
        }

        Bounds bounds = getPlayerInfoBounds(client, screenWidth, screenHeight);
        int revealWidth = Math.max(1, Math.round(bounds.width * progress));
        int revealHeight = Math.max(1, Math.round(bounds.height * progress));
        context.enableScissor(
            bounds.right() - revealWidth,
            bounds.bottom() - revealHeight,
            bounds.right(),
            bounds.bottom()
        );
        drawPlayerInfo(context, client, screenWidth, screenHeight);
        context.disableScissor();
    }

    private static void drawRecalcLogoAnimated(
        DrawContext context,
        MinecraftClient client,
        int x,
        int y,
        float progress
    ) {
        if (progress <= 0.0f) {
            return;
        }

        Text logo = Text.literal("Recalc").setStyle(Style.EMPTY.withFont(RECALC_FONT));
        int width = client.textRenderer.getWidth(logo) + 6;
        int revealWidth = Math.max(1, Math.round(width * progress));
        context.enableScissor(x - 3, y - 3, x - 3 + revealWidth, y + LOGO_HEIGHT);
        drawRecalcLogo(context, client, x, y);
        context.disableScissor();
    }

    private static void drawAbilityBar(DrawContext context, MinecraftClient client) {
        float current = BattleHUDManager.getAbilityCurrent();
        float max = BattleHUDManager.getAbilityMax();
        float fillPercent = clamp(max <= 0 ? 0 : current / max, 0.0f, 1.0f);

        context.getMatrices().push();
        context.getMatrices().translate(ABILITY_X, ABILITY_Y, 0);
        drawAbilityFrame(context);
        drawAbilityTrack(context, fillPercent);
        drawAbilityText(context, client, current, max);
        context.getMatrices().pop();
    }

    private static void drawAbilityFrame(DrawContext context) {
        drawParallelogram(context, 4, 4, ABILITY_PANEL_WIDTH, ABILITY_PANEL_HEIGHT - 2, 10, PANEL_SHADOW);
        drawParallelogram(context, 0, 0, ABILITY_PANEL_WIDTH, ABILITY_PANEL_HEIGHT, 10, GLASS_BACKGROUND);
        drawParallelogram(context, 4, 3, ABILITY_PANEL_WIDTH - 10, ABILITY_PANEL_HEIGHT - 7, 7, 0x323F474F);

        context.fill(9, 2, 115, 3, PANEL_STROKE);
        context.fill(7, 3, 9, 16, PANEL_STROKE);
        context.fill(154, 27, 205, 28, PANEL_STROKE_FAINT);
        context.fill(214, 11, 216, 28, PANEL_STROKE_FAINT);
        drawAbilityStatusIcon(context);
    }

    private static void drawAbilityTrack(DrawContext context, float fillPercent) {
        drawParallelogram(context, ABILITY_BAR_X - 2, ABILITY_BAR_Y - 2, ABILITY_BAR_WIDTH + 5, ABILITY_BAR_HEIGHT + 4, 7, 0x6A20262C);

        int segmentWidth = (ABILITY_BAR_WIDTH - (ABILITY_SEGMENT_COUNT - 1) * ABILITY_SEGMENT_GAP) / ABILITY_SEGMENT_COUNT;
        float filledSegments = fillPercent * ABILITY_SEGMENT_COUNT;
        for (int i = 0; i < ABILITY_SEGMENT_COUNT; i++) {
            int segmentX = ABILITY_BAR_X + i * (segmentWidth + ABILITY_SEGMENT_GAP);
            float segmentFill = clamp(filledSegments - i, 0.0f, 1.0f);

            drawParallelogram(
                context,
                segmentX,
                ABILITY_BAR_Y,
                segmentWidth,
                ABILITY_BAR_HEIGHT,
                ABILITY_BAR_SLANT,
                CELL_EMPTY
            );

            drawParallelogram(
                context,
                segmentX + 1,
                ABILITY_BAR_Y + 1,
                segmentWidth - 2,
                ABILITY_BAR_HEIGHT - 2,
                Math.max(1, ABILITY_BAR_SLANT - 2),
                GLASS_BACKGROUND_DARK
            );

            int fillWidth = Math.round((segmentWidth - 2) * segmentFill);
            if (fillWidth > 0) {
                int fillColor = segmentFill < 1.0f ? ACCENT_CYAN : CELL_FILLED;
                drawParallelogram(
                    context,
                    segmentX + 1,
                    ABILITY_BAR_Y + 1,
                    fillWidth,
                    ABILITY_BAR_HEIGHT - 2,
                    Math.min(Math.max(1, ABILITY_BAR_SLANT - 2), fillWidth),
                    fillColor
                );
            }
        }

        drawAbilityScanLine(context, fillPercent);
    }

    private static void drawAbilityScanLine(DrawContext context, float fillPercent) {
        int fillWidth = Math.round(ABILITY_BAR_WIDTH * fillPercent);
        if (fillWidth <= 0) {
            return;
        }

        int contentWidth = Math.max(1, fillWidth - 12);
        int scanOffset = (int)((System.currentTimeMillis() / 18L) % (contentWidth + 18)) - 9;
        int scanX = ABILITY_BAR_X + scanOffset;
        int fillEnd = ABILITY_BAR_X + fillWidth;

        if (scanX < fillEnd) {
            int right = Math.min(scanX + 5, fillEnd);
            context.fill(scanX, ABILITY_BAR_Y + 1, right, ABILITY_BAR_Y + ABILITY_BAR_HEIGHT - 1, 0x74FFFFFF);
        }

        int capX = ABILITY_BAR_X + fillWidth;
        context.fill(capX, ABILITY_BAR_Y - 1, capX + 2, ABILITY_BAR_Y + ABILITY_BAR_HEIGHT + 1, ACCENT_LAVENDER);
    }

    private static void drawAbilityText(DrawContext context, MinecraftClient client, float current, float max) {
        float fillPercent = clamp(max <= 0 ? 0 : current / max, 0.0f, 1.0f);
        Text value = Text.literal(String.format("%.0f%%", fillPercent * 100.0f));

        int valueX = ABILITY_STATUS_X - 8 - client.textRenderer.getWidth(value);
        drawAbilityPromptLabel(context, client, valueX - ABILITY_PROMPT_RIGHT_PADDING);

        context.drawText(client.textRenderer, value, valueX + 1, 19, TEXT_SHADOW, false);
        context.drawText(client.textRenderer, value, valueX, 18, ACCENT_BLUE, false);
    }

    private static void drawAbilityPromptLabel(DrawContext context, MinecraftClient client, int maxRightX) {
        Text label = BattleHUDManager.getAbilityPromptLabel();
        int maxTextWidth = maxRightX - ABILITY_PROMPT_X - ABILITY_PROMPT_TEXT_PADDING * 2;
        if (label == null || maxTextWidth <= 0) {
            return;
        }

        Text visibleLabel = trimTextToWidth(client, label, maxTextWidth);
        int labelWidth = client.textRenderer.getWidth(visibleLabel);
        if (labelWidth <= 0) {
            return;
        }

        int labelRight = ABILITY_PROMPT_X + labelWidth + ABILITY_PROMPT_TEXT_PADDING * 2;

        context.fill(ABILITY_PROMPT_X, ABILITY_PROMPT_Y, labelRight, ABILITY_PROMPT_Y + 10, 0x60474F57);
        context.fill(ABILITY_PROMPT_X, ABILITY_PROMPT_Y, labelRight, ABILITY_PROMPT_Y + 1, PANEL_STROKE_FAINT);
        context.drawText(
            client.textRenderer,
            visibleLabel,
            ABILITY_PROMPT_X + ABILITY_PROMPT_TEXT_PADDING,
            ABILITY_PROMPT_Y + 1,
            WHITE,
            true
        );
    }

    private static Text trimTextToWidth(MinecraftClient client, Text text, int maxWidth) {
        if (client.textRenderer.getWidth(text) <= maxWidth) {
            return text;
        }

        int ellipsisWidth = client.textRenderer.getWidth("...");
        if (maxWidth <= ellipsisWidth) {
            return Text.literal("");
        }

        return Text.literal(client.textRenderer.trimToWidth(text.getString(), maxWidth - ellipsisWidth) + "...");
    }

    private static void drawAbilityStatusIcon(DrawContext context) {
        int x = ABILITY_STATUS_X;
        int y = ABILITY_STATUS_Y;
        int right = x + ABILITY_STATUS_SIZE;
        int bottom = y + ABILITY_STATUS_SIZE;

        context.fill(x, y, right, bottom, 0x7A4B535B);
        context.fill(x + 1, y + 1, right - 1, bottom - 1, 0x55303840);
        context.fill(x, y, right, y + 1, PANEL_STROKE);
        context.fill(x, bottom - 1, right, bottom, PANEL_STROKE_FAINT);
        context.fill(x, y, x + 1, bottom, PANEL_STROKE_FAINT);
        context.fill(right - 1, y, right, bottom, PANEL_STROKE);

        switch (BattleHUDManager.getAbilityType()) {
            case FIRE -> drawFireIcon(context, x, y);
            case LIGHTNING -> drawLightningIcon(context, x, y);
            case WIND -> drawWindIcon(context, x, y);
            case TELEPORT -> drawTeleportIcon(context, x, y);
            case VECTOR -> drawVectorIcon(context, x, y);
            case NONE -> {
            }
        }
    }

    private static void drawFireIcon(DrawContext context, int x, int y) {
        int glyphX = x + 8;
        int glyphY = y + 5;
        context.fill(glyphX + 5, glyphY, glyphX + 10, glyphY + 4, ABILITY_FIRE);
        context.fill(glyphX + 3, glyphY + 4, glyphX + 9, glyphY + 10, ABILITY_FIRE);
        context.fill(glyphX + 7, glyphY + 9, glyphX + 12, glyphY + 14, ABILITY_FIRE);
        context.fill(glyphX + 1, glyphY + 13, glyphX + 8, glyphY + 17, WHITE);
        context.fill(glyphX + 10, glyphY + 2, glyphX + 13, glyphY + 6, PANEL_STROKE_FAINT);
    }

    private static void drawLightningIcon(DrawContext context, int x, int y) {
        int glyphX = x + 7;
        int glyphY = y + 4;
        context.fill(glyphX + 6, glyphY, glyphX + 13, glyphY + 3, ABILITY_LIGHTNING);
        context.fill(glyphX + 4, glyphY + 3, glyphX + 10, glyphY + 8, ABILITY_LIGHTNING);
        context.fill(glyphX + 8, glyphY + 8, glyphX + 14, glyphY + 11, WHITE);
        context.fill(glyphX + 3, glyphY + 11, glyphX + 9, glyphY + 16, WHITE);
        context.fill(glyphX + 1, glyphY + 16, glyphX + 5, glyphY + 19, PANEL_STROKE_FAINT);
    }

    private static void drawWindIcon(DrawContext context, int x, int y) {
        int glyphX = x + 5;
        int glyphY = y + 7;
        context.fill(glyphX, glyphY, glyphX + 13, glyphY + 2, ABILITY_WIND);
        context.fill(glyphX + 5, glyphY + 4, glyphX + 17, glyphY + 6, WHITE);
        context.fill(glyphX + 2, glyphY + 8, glyphX + 15, glyphY + 10, ABILITY_WIND);
        context.fill(glyphX + 13, glyphY + 1, glyphX + 16, glyphY + 4, PANEL_STROKE_FAINT);
        context.fill(glyphX + 15, glyphY + 5, glyphX + 18, glyphY + 8, PANEL_STROKE_FAINT);
    }

    private static void drawTeleportIcon(DrawContext context, int x, int y) {
        int glyphX = x + 6;
        int glyphY = y + 6;
        context.fill(glyphX, glyphY, glyphX + 5, glyphY + 5, ABILITY_TELEPORT);
        context.fill(glyphX + 11, glyphY + 11, glyphX + 16, glyphY + 16, WHITE);
        context.fill(glyphX + 6, glyphY + 6, glyphX + 11, glyphY + 8, PANEL_STROKE_FAINT);
        context.fill(glyphX + 8, glyphY + 8, glyphX + 10, glyphY + 13, PANEL_STROKE_FAINT);
    }

    private static void drawVectorIcon(DrawContext context, int x, int y) {
        int glyphX = x + 5;
        int glyphY = y + 5;
        context.fill(glyphX + 1, glyphY + 1, glyphX + 5, glyphY + 5, ABILITY_VECTOR);
        context.fill(glyphX + 12, glyphY + 12, glyphX + 16, glyphY + 16, WHITE);
        context.fill(glyphX + 5, glyphY + 5, glyphX + 13, glyphY + 7, ABILITY_VECTOR);
        context.fill(glyphX + 11, glyphY + 7, glyphX + 13, glyphY + 13, ABILITY_VECTOR);
    }

    private static void drawPlayerInfo(DrawContext context, MinecraftClient client, int screenWidth, int screenHeight) {
        List<Text> lines = getPlayerSummary(client);
        Bounds bounds = getPlayerInfoBounds(client, screenWidth, screenHeight);
        int logicalWidth = getPlayerPanelLogicalWidth(client, lines);
        int logicalHeight = getPlayerPanelLogicalHeight(lines);

        context.fill(bounds.x + 3, bounds.y + 3, bounds.right() + 3, bounds.bottom() + 3, PANEL_SHADOW);
        context.fill(bounds.x, bounds.y, bounds.right(), bounds.bottom(), GLASS_BACKGROUND);
        drawTechCorners(context, bounds.x, bounds.y, logicalWidth, logicalHeight);
        context.fill(bounds.right() - 40, bounds.y, bounds.right(), bounds.y + 1, PANEL_STROKE);
        context.fill(bounds.x + 1, bounds.y + 1, bounds.right() - 1, bounds.y + 2, 0x3FFFFFFF);

        int textY = bounds.y + PLAYER_PANEL_PADDING;
        for (int i = 0; i < lines.size(); i++) {
            int color = i == 0 ? WHITE : ACCENT_BLUE;
            context.drawText(client.textRenderer, lines.get(i), bounds.x + PLAYER_PANEL_PADDING, textY, color, true);
            textY += PLAYER_LINE_HEIGHT;
        }
    }

    private static void drawRecalcLogo(DrawContext context, MinecraftClient client, int x, int y) {
        Text logo = Text.literal("Recalc").setStyle(Style.EMPTY.withFont(RECALC_FONT));
        float pulse = (float)(0.88 + Math.sin(System.currentTimeMillis() / 600.0) * 0.08);

        HudRenderUtils.drawGlowingText(
            context,
            client.textRenderer,
            logo,
            x,
            y,
            0xFFFFFF,
            0xDCE5EA,
            pulse,
            true
        );

        int textWidth = client.textRenderer.getWidth(logo);
        context.fill(x, y + client.textRenderer.fontHeight + 2, x + textWidth, y + client.textRenderer.fontHeight + 3, WHITE_FAINT);
        context.fill(x + textWidth - 14, y + client.textRenderer.fontHeight + 2, x + textWidth, y + client.textRenderer.fontHeight + 3, PANEL_STROKE);
    }

    private static List<Text> getAbilityDetails() {
        float current = BattleHUDManager.getAbilityCurrent();
        float max = BattleHUDManager.getAbilityMax();
        float percent = max <= 0 ? 0 : clamp(current / max, 0.0f, 1.0f);

        Text state;
        if (percent >= 0.99f) {
            state = Text.translatable("hud.recalc.ability_state.full");
        } else if (percent <= 0.2f) {
            state = Text.translatable("hud.recalc.ability_state.low");
        } else {
            state = Text.translatable("hud.recalc.ability_state.stable");
        }

        return List.of(
            Text.translatable("hud.recalc.ability_title").formatted(Formatting.WHITE),
            Text.translatable("hud.recalc.ability_current", String.format("%.0f", current)),
            Text.translatable("hud.recalc.ability_capacity", String.format("%.0f", max)),
            Text.translatable("hud.recalc.ability_charge", String.format("%.0f", percent * 100)),
            Text.translatable("hud.recalc.ability_state", state)
        );
    }

    private static List<Text> getPlayerDetails(MinecraftClient client) {
        List<Text> lines = new ArrayList<>();
        int chunkX = Math.floorDiv(client.player.getBlockX(), 16);
        int chunkZ = Math.floorDiv(client.player.getBlockZ(), 16);

        lines.add(Text.translatable("hud.recalc.player_title").formatted(Formatting.WHITE));
        lines.add(Text.translatable(
            "hud.recalc.health",
            String.format("%.1f", client.player.getHealth()),
            String.format("%.1f", client.player.getMaxHealth())
        ));
        lines.add(Text.translatable("hud.recalc.chunk", chunkX, chunkZ));

        if (client.crosshairTarget instanceof BlockHitResult blockHitResult) {
            BlockPos pos = blockHitResult.getBlockPos();
            BlockState state = client.world.getBlockState(pos);
            lines.add(Text.translatable("hud.recalc.target_block", state.getBlock().getName()));
            lines.add(Text.translatable("hud.recalc.target_position", pos.getX(), pos.getY(), pos.getZ()));
        } else {
            lines.add(Text.translatable("hud.recalc.target_none"));
        }

        return lines;
    }

    private static List<Text> getPlayerSummary(MinecraftClient client) {
        return List.of(
            client.player.getName(),
            Text.literal("v" + Recalc.MOD_VERSION),
            Text.literal(String.format(
                "X: %.0f  Y: %.0f  Z: %.0f",
                client.player.getX(),
                client.player.getY(),
                client.player.getZ()
            )),
            Text.literal(client.world.getRegistryKey().getValue().getPath())
        );
    }

    private static Bounds getPlayerInfoBounds(MinecraftClient client, int screenWidth, int screenHeight) {
        List<Text> lines = getPlayerSummary(client);
        int width = getPlayerPanelLogicalWidth(client, lines);
        int height = getPlayerPanelLogicalHeight(lines);
        return new Bounds(
            screenWidth - PLAYER_PANEL_MARGIN - width,
            screenHeight - PLAYER_PANEL_MARGIN - height,
            width,
            height
        );
    }

    private static Bounds abilityBounds() {
        return new Bounds(
            ABILITY_X,
            ABILITY_Y,
            ABILITY_PANEL_WIDTH + 10,
            ABILITY_PANEL_HEIGHT
        );
    }

    private static int getPlayerPanelLogicalWidth(MinecraftClient client, List<Text> lines) {
        int contentWidth = 0;
        for (Text line : lines) {
            contentWidth = Math.max(contentWidth, client.textRenderer.getWidth(line));
        }
        return contentWidth + PLAYER_PANEL_PADDING * 2;
    }

    private static int getPlayerPanelLogicalHeight(List<Text> lines) {
        return lines.size() * PLAYER_LINE_HEIGHT + PLAYER_PANEL_PADDING * 2;
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

    private static void drawTechCorners(DrawContext context, int x, int y, int width, int height) {
        int corner = 10;
        context.fill(x, y, x + corner, y + 1, PANEL_STROKE);
        context.fill(x, y, x + 1, y + corner, PANEL_STROKE);
        context.fill(x + width - corner, y, x + width, y + 1, PANEL_STROKE);
        context.fill(x + width - 1, y, x + width, y + corner, PANEL_STROKE);
        context.fill(x, y + height - 1, x + corner, y + height, PANEL_STROKE_FAINT);
        context.fill(x, y + height - corner, x + 1, y + height, PANEL_STROKE_FAINT);
        context.fill(x + width - corner, y + height - 1, x + width, y + height, PANEL_STROKE_FAINT);
        context.fill(x + width - 1, y + height - corner, x + width, y + height, PANEL_STROKE_FAINT);
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
    }

    private static float stagedProgress(float progress, float start, float end) {
        if (progress <= start) {
            return 0.0f;
        }
        if (progress >= end) {
            return 1.0f;
        }

        float normalized = (progress - start) / (end - start);
        return normalized * normalized * (3.0f - 2.0f * normalized);
    }

    private record Bounds(int x, int y, int width, int height) {
        private int right() {
            return x + width;
        }

        private int bottom() {
            return y + height;
        }

        private boolean contains(int pointX, int pointY) {
            return pointX >= x && pointX < right() && pointY >= y && pointY < bottom();
        }
    }
}
