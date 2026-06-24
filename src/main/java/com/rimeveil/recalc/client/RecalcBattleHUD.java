package com.rimeveil.recalc.client;

import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.ui.RecalcSettingsScreen;
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
    private static final Identifier SETTINGS_BUTTON_TEXTURE = Recalc.id("textures/ui/settings_button.png");

    private static final int WHITE = 0xFFFFFFFF;
    private static final int WHITE_SOFT = 0xB8FFFFFF;
    private static final int WHITE_FAINT = 0x30FFFFFF;
    private static final int GLASS_BACKGROUND = 0x16050A0E;
    private static final int ACCENT_CYAN = 0xFFB9F3FA;
    private static final int ACCENT_BLUE = 0xFFBBDDF7;
    private static final int ACCENT_LAVENDER = 0xFFD8D2FA;
    private static final int TEXT_SHADOW = 0x80000000;

    private static final int ABILITY_X = 20;
    private static final int ABILITY_Y = 20;
    private static final int ABILITY_PANEL_WIDTH = 218;
    private static final int ABILITY_PANEL_HEIGHT = 30;
    private static final int ABILITY_BAR_X = 10;
    private static final int ABILITY_BAR_Y = 17;
    private static final int ABILITY_BAR_WIDTH = 190;
    private static final int ABILITY_BAR_HEIGHT = 7;
    private static final int ABILITY_BAR_SLANT = 6;
    private static final int ABILITY_SEGMENT_COUNT = 10;

    private static final int SETTINGS_BUTTON_SIZE = 18;
    private static final int SETTINGS_BUTTON_MARGIN = 14;
    private static final int SETTINGS_TEXTURE_WIDTH = SETTINGS_BUTTON_SIZE * 2;
    private static final int SETTINGS_TEXTURE_HEIGHT = SETTINGS_BUTTON_SIZE;

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

        if (getSettingsBounds(screenWidth).contains(mouseX, mouseY)) {
            context.drawTooltip(client.textRenderer, List.of(Text.translatable("hud.recalc.settings")), mouseX, mouseY);
        }
    }

    public static boolean handleInteractionClick(MinecraftClient client, int mouseX, int mouseY) {
        if (getSettingsBounds(client.getWindow().getScaledWidth()).contains(mouseX, mouseY)) {
            client.setScreen(new RecalcSettingsScreen());
            return true;
        }
        return false;
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

        if (BattleHUDManager.isCursorVisible()) {
            HudBackgroundBlur.render(context, client);
        }

        drawAbilityBarAnimated(context, client, stagedProgress(progress, 0.0f, 0.58f));
        drawPlayerInfoAnimated(context, client, screenWidth, screenHeight, stagedProgress(progress, 0.16f, 0.78f));
        drawSettingsButtonAnimated(context, client, getSettingsBounds(screenWidth), stagedProgress(progress, 0.42f, 0.82f));
        drawRecalcLogoAnimated(context, client, LOGO_MARGIN, screenHeight - 30, stagedProgress(progress, 0.56f, 1.0f));
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

    private static void drawSettingsButtonAnimated(
        DrawContext context,
        MinecraftClient client,
        Bounds bounds,
        float progress
    ) {
        if (progress <= 0.0f) {
            return;
        }

        int revealWidth = Math.max(1, Math.round(bounds.width * progress));
        int revealHeight = Math.max(1, Math.round(bounds.height * progress));
        int centerX = bounds.x + bounds.width / 2;
        int centerY = bounds.y + bounds.height / 2;
        context.enableScissor(
            centerX - revealWidth / 2,
            centerY - revealHeight / 2,
            centerX + (revealWidth + 1) / 2,
            centerY + (revealHeight + 1) / 2
        );
        drawSettingsButton(context, client, bounds);
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
        drawParallelogram(context, 0, 0, ABILITY_PANEL_WIDTH, ABILITY_PANEL_HEIGHT, 10, GLASS_BACKGROUND);

        context.fill(8, 2, 72, 3, WHITE_SOFT);
        context.fill(8, 3, 10, 15, WHITE_SOFT);
        context.fill(72, 2, 90, 3, ACCENT_CYAN);
        context.fill(204, 27, 216, 28, WHITE_FAINT);
        context.fill(216, 19, 217, 28, WHITE_FAINT);
    }

    private static void drawAbilityTrack(DrawContext context, float fillPercent) {
        drawParallelogram(
            context,
            ABILITY_BAR_X,
            ABILITY_BAR_Y,
            ABILITY_BAR_WIDTH,
            ABILITY_BAR_HEIGHT,
            ABILITY_BAR_SLANT,
            WHITE_SOFT
        );
        drawParallelogram(
            context,
            ABILITY_BAR_X + 2,
            ABILITY_BAR_Y + 1,
            ABILITY_BAR_WIDTH - 4,
            ABILITY_BAR_HEIGHT - 2,
            ABILITY_BAR_SLANT - 2,
            0x26000000
        );

        int fillWidth = Math.round((ABILITY_BAR_WIDTH - 4) * fillPercent);
        if (fillWidth > 0) {
            drawParallelogram(
                context,
                ABILITY_BAR_X + 2,
                ABILITY_BAR_Y + 1,
                fillWidth,
                ABILITY_BAR_HEIGHT - 2,
                Math.min(ABILITY_BAR_SLANT - 2, fillWidth),
                ACCENT_CYAN
            );
            drawAbilityPulse(context, fillWidth);
        }

        int segmentWidth = ABILITY_BAR_WIDTH / ABILITY_SEGMENT_COUNT;
        for (int i = 1; i < ABILITY_SEGMENT_COUNT; i++) {
            int segmentX = ABILITY_BAR_X + i * segmentWidth;
            context.fill(segmentX, ABILITY_BAR_Y + 2, segmentX + 1, ABILITY_BAR_Y + ABILITY_BAR_HEIGHT - 2, 0x45FFFFFF);
        }
    }

    private static void drawAbilityPulse(DrawContext context, int fillWidth) {
        int contentWidth = Math.max(1, fillWidth - 10);
        int scanOffset = (int)((System.currentTimeMillis() / 15L) % (contentWidth + 16)) - 8;
        int scanX = ABILITY_BAR_X + 5 + scanOffset;
        int fillEnd = ABILITY_BAR_X + 2 + fillWidth;

        if (scanX < fillEnd) {
            int right = Math.min(scanX + 5, fillEnd);
            context.fill(scanX, ABILITY_BAR_Y + 2, right, ABILITY_BAR_Y + ABILITY_BAR_HEIGHT - 2, 0x78FFFFFF);
        }

        int capX = ABILITY_BAR_X + fillWidth - 1;
        context.fill(capX, ABILITY_BAR_Y, capX + 2, ABILITY_BAR_Y + ABILITY_BAR_HEIGHT, ACCENT_LAVENDER);
    }

    private static void drawAbilityText(DrawContext context, MinecraftClient client, float current, float max) {
        Text label = Text.literal("ABILITY");
        Text value = Text.literal(String.format("%.0f / %.0f", current, max));

        context.drawText(client.textRenderer, label, 13, 6, WHITE, true);
        int valueX = ABILITY_PANEL_WIDTH - 14 - client.textRenderer.getWidth(value);
        context.drawText(client.textRenderer, value, valueX + 1, 7, TEXT_SHADOW, false);
        context.drawText(client.textRenderer, value, valueX, 6, ACCENT_BLUE, false);
    }

    private static void drawSettingsButton(DrawContext context, MinecraftClient client, Bounds bounds) {
        boolean hovered = BattleHUDManager.isCursorVisible()
            && bounds.contains(getScaledMouseX(client), getScaledMouseY(client));
        int textureU = hovered ? SETTINGS_BUTTON_SIZE : 0;

        context.drawTexture(
            SETTINGS_BUTTON_TEXTURE,
            bounds.x,
            bounds.y,
            textureU,
            0,
            SETTINGS_BUTTON_SIZE,
            SETTINGS_BUTTON_SIZE,
            SETTINGS_TEXTURE_WIDTH,
            SETTINGS_TEXTURE_HEIGHT
        );
    }

    private static void drawPlayerInfo(DrawContext context, MinecraftClient client, int screenWidth, int screenHeight) {
        List<Text> lines = getPlayerSummary(client);
        Bounds bounds = getPlayerInfoBounds(client, screenWidth, screenHeight);
        int logicalWidth = getPlayerPanelLogicalWidth(client, lines);
        int logicalHeight = getPlayerPanelLogicalHeight(lines);

        context.fill(bounds.x, bounds.y, bounds.right(), bounds.bottom(), GLASS_BACKGROUND);
        drawTechCorners(context, bounds.x, bounds.y, logicalWidth, logicalHeight);
        context.fill(bounds.right() - 34, bounds.y, bounds.right(), bounds.y + 1, ACCENT_CYAN);

        int textY = bounds.y + PLAYER_PANEL_PADDING;
        for (int i = 0; i < lines.size(); i++) {
            int color = i == 0 ? ACCENT_CYAN : WHITE;
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
            0xB9F3FA,
            pulse,
            true
        );

        int textWidth = client.textRenderer.getWidth(logo);
        context.fill(x, y + client.textRenderer.fontHeight + 2, x + textWidth, y + client.textRenderer.fontHeight + 3, WHITE_FAINT);
        context.fill(x + textWidth - 14, y + client.textRenderer.fontHeight + 2, x + textWidth, y + client.textRenderer.fontHeight + 3, ACCENT_CYAN);
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

    private static Bounds getSettingsBounds(int screenWidth) {
        return new Bounds(
            screenWidth - SETTINGS_BUTTON_MARGIN - SETTINGS_BUTTON_SIZE,
            SETTINGS_BUTTON_MARGIN,
            SETTINGS_BUTTON_SIZE,
            SETTINGS_BUTTON_SIZE
        );
    }

    private static int getScaledMouseX(MinecraftClient client) {
        return (int)(client.mouse.getX() * client.getWindow().getScaledWidth() / client.getWindow().getWidth());
    }

    private static int getScaledMouseY(MinecraftClient client) {
        return (int)(client.mouse.getY() * client.getWindow().getScaledHeight() / client.getWindow().getHeight());
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
        context.fill(x, y, x + corner, y + 1, WHITE_SOFT);
        context.fill(x, y, x + 1, y + corner, WHITE_SOFT);
        context.fill(x + width - corner, y, x + width, y + 1, WHITE_SOFT);
        context.fill(x + width - 1, y, x + width, y + corner, WHITE_SOFT);
        context.fill(x, y + height - 1, x + corner, y + height, WHITE_SOFT);
        context.fill(x, y + height - corner, x + 1, y + height, WHITE_SOFT);
        context.fill(x + width - corner, y + height - 1, x + width, y + height, WHITE_SOFT);
        context.fill(x + width - 1, y + height - corner, x + width, y + height, WHITE_SOFT);
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
