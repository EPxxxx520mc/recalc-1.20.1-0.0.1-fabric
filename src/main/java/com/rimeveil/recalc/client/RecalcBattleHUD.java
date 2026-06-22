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

    private static final int COLOR_WHITE = 0xFFFFFFFF;
    private static final int COLOR_PANEL = 0x880A0718;
    private static final int COLOR_PANEL_DARK = 0xBB05030C;
    private static final int COLOR_BORDER_DIM = 0x885E4DD8;
    private static final int COLOR_BORDER_BRIGHT = 0xFFD6E2FF;
    private static final int COLOR_MANA_START = 0xFF5EE7FF;
    private static final int COLOR_MANA_MID = 0xFF8E6CFF;
    private static final int COLOR_MANA_END = 0xFFFF7BDE;
    private static final int COLOR_MANA_SHINE = 0x88FFFFFF;
    private static final int COLOR_MANA_EMPTY = 0x660E1021;
    private static final int COLOR_TEXT_SHADOW = 0xB0000000;

    private static final int MANA_X = 20;
    private static final int MANA_Y = 20;
    private static final float MANA_UI_SCALE = 0.78F;
    private static final int MANA_PANEL_WIDTH = 286;
    private static final int MANA_PANEL_HEIGHT = 42;
    private static final int MANA_BAR_X_OFFSET = 42;
    private static final int MANA_BAR_Y_OFFSET = 16;
    private static final int MANA_BAR_WIDTH = 220;
    private static final int MANA_BAR_HEIGHT = 12;
    private static final int MANA_CORE_SIZE = 18;
    private static final int MANA_SEGMENT_COUNT = 10;

    private static final int SETTINGS_BUTTON_SIZE = 18;
    private static final int SETTINGS_BUTTON_MARGIN = 14;
    private static final int SETTINGS_TEXTURE_WIDTH = SETTINGS_BUTTON_SIZE * 2;
    private static final int SETTINGS_TEXTURE_HEIGHT = SETTINGS_BUTTON_SIZE;

    private static final int PLAYER_PANEL_MARGIN = 10;
    private static final int PLAYER_PANEL_PADDING = 6;
    private static final int PLAYER_LINE_HEIGHT = 11;
    private static final float PLAYER_UI_SCALE = 0.82F;
    private static final int LOGO_MARGIN = 20;

    public static void register() {
        HudRenderCallback.EVENT.register(RecalcBattleHUD::render);
    }

    public static void renderInteractionOverlay(DrawContext context, MinecraftClient client, int mouseX, int mouseY) {
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();

        if (manaBounds().contains(mouseX, mouseY)) {
            context.drawTooltip(client.textRenderer, getManaDetails(), mouseX, mouseY);
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
        if (!BattleHUDManager.isHUDVisible()) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.world == null) {
            return;
        }

        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();

        drawScaledManaBar(context, client);
        drawSettingsButton(context, client, getSettingsBounds(screenWidth));
        drawPlayerInfo(context, client, screenWidth, screenHeight);
        drawRecalcLogo(context, client, LOGO_MARGIN, screenHeight - 30);
    }

    private static void drawScaledManaBar(DrawContext context, MinecraftClient client) {
        context.getMatrices().push();
        context.getMatrices().translate(MANA_X, MANA_Y, 0);
        context.getMatrices().scale(MANA_UI_SCALE, MANA_UI_SCALE, 1.0F);
        drawManaBar(context, client, 0, 0);
        context.getMatrices().pop();
    }

    private static void drawManaBar(DrawContext context, MinecraftClient client, int x, int y) {
        float currentEnergy = BattleHUDManager.getEnergyCurrent();
        float maxEnergy = BattleHUDManager.getEnergyMax();
        float fillPercent = clamp(maxEnergy <= 0 ? 0 : currentEnergy / maxEnergy, 0.0f, 1.0f);

        drawManaPanel(context, x, y);
        drawManaCore(context, x + 20, y + MANA_PANEL_HEIGHT / 2, fillPercent);
        drawManaTrack(context, x + MANA_BAR_X_OFFSET, y + MANA_BAR_Y_OFFSET, fillPercent);
        drawManaText(context, client, x + MANA_BAR_X_OFFSET, y + 4, currentEnergy, maxEnergy);
    }

    private static void drawManaPanel(DrawContext context, int x, int y) {
        context.fill(x + 2, y + 3, x + MANA_PANEL_WIDTH + 2, y + MANA_PANEL_HEIGHT + 3, 0x66000000);
        context.fill(x, y, x + MANA_PANEL_WIDTH, y + MANA_PANEL_HEIGHT, COLOR_PANEL);
        context.fill(x + 2, y + 2, x + MANA_PANEL_WIDTH - 2, y + MANA_PANEL_HEIGHT - 2, COLOR_PANEL_DARK);

        drawRectBorder(context, x, y, MANA_PANEL_WIDTH, MANA_PANEL_HEIGHT, COLOR_BORDER_DIM);
        context.fill(x + 1, y + 1, x + 86, y + 2, COLOR_BORDER_BRIGHT);
        context.fill(x + 1, y + 1, x + 2, y + 24, COLOR_BORDER_BRIGHT);
    }

    private static void drawManaCore(DrawContext context, int centerX, int centerY, float fillPercent) {
        int half = MANA_CORE_SIZE / 2;
        int pulseAlpha = (int)(95 + Math.sin(System.currentTimeMillis() / 340.0) * 45 * Math.max(0.2f, fillPercent));
        int pulseColor = (pulseAlpha << 24) | 0x00A8DFFF;

        drawDiamond(context, centerX, centerY, half + 5, 0x33000000);
        drawDiamond(context, centerX, centerY, half + 3, pulseColor);
        drawDiamond(context, centerX, centerY, half + 1, 0xFF17122E);
        drawDiamond(context, centerX, centerY, half - 2, blend(COLOR_MANA_START, COLOR_MANA_END, fillPercent));
        context.fill(centerX - 1, centerY - half + 4, centerX + 1, centerY + half - 4, 0xAAFFFFFF);
    }

    private static void drawManaTrack(DrawContext context, int x, int y, float fillPercent) {
        context.fill(x - 2, y - 2, x + MANA_BAR_WIDTH + 2, y + MANA_BAR_HEIGHT + 2, 0xAA000000);
        context.fill(x, y, x + MANA_BAR_WIDTH, y + MANA_BAR_HEIGHT, COLOR_MANA_EMPTY);
        context.fill(x, y, x + MANA_BAR_WIDTH, y + 1, 0x44FFFFFF);
        context.fill(x, y + MANA_BAR_HEIGHT - 1, x + MANA_BAR_WIDTH, y + MANA_BAR_HEIGHT, 0x77000000);

        int fillWidth = Math.round(MANA_BAR_WIDTH * fillPercent);
        if (fillWidth > 0) {
            drawGradientFill(context, x, y + 1, fillWidth, MANA_BAR_HEIGHT - 2);
            drawManaGlow(context, x, y, fillWidth);
            drawManaShine(context, x, y, fillWidth);
        }

        drawManaSegments(context, x, y);
        drawRectBorder(context, x - 1, y - 1, MANA_BAR_WIDTH + 2, MANA_BAR_HEIGHT + 2, COLOR_BORDER_DIM);
    }

    private static void drawGradientFill(DrawContext context, int x, int y, int width, int height) {
        for (int i = 0; i < width; i++) {
            float progress = width <= 1 ? 1.0f : (float)i / (float)(width - 1);
            int color = progress < 0.55f
                ? blend(COLOR_MANA_START, COLOR_MANA_MID, progress / 0.55f)
                : blend(COLOR_MANA_MID, COLOR_MANA_END, (progress - 0.55f) / 0.45f);
            context.fill(x + i, y, x + i + 1, y + height, color);
        }
    }

    private static void drawManaGlow(DrawContext context, int x, int y, int fillWidth) {
        int glowAlpha = (int)(72 + Math.sin(System.currentTimeMillis() / 420.0) * 26);
        int glowColor = (glowAlpha << 24) | 0x00A7EFFF;
        context.fill(x - 1, y - 3, x + fillWidth + 1, y - 1, glowColor);
        context.fill(x - 1, y + MANA_BAR_HEIGHT + 1, x + fillWidth + 1, y + MANA_BAR_HEIGHT + 3, glowColor);
    }

    private static void drawManaShine(DrawContext context, int x, int y, int fillWidth) {
        int cycle = (int)((System.currentTimeMillis() / 18L) % (MANA_BAR_WIDTH + 34));
        int shineX = x + cycle - 28;
        int shineStart = Math.max(x, shineX);
        int shineEnd = Math.min(x + fillWidth, shineX + 22);

        if (shineEnd > shineStart) {
            context.fill(shineStart, y + 2, shineEnd, y + 4, COLOR_MANA_SHINE);
        }
    }

    private static void drawManaSegments(DrawContext context, int x, int y) {
        int segmentWidth = MANA_BAR_WIDTH / MANA_SEGMENT_COUNT;
        for (int i = 1; i < MANA_SEGMENT_COUNT; i++) {
            int segmentX = x + i * segmentWidth;
            context.fill(segmentX, y + 1, segmentX + 1, y + MANA_BAR_HEIGHT - 1, 0x77000000);
            context.fill(segmentX + 1, y + 1, segmentX + 2, y + MANA_BAR_HEIGHT - 1, 0x22FFFFFF);
        }
    }

    private static void drawManaText(DrawContext context, MinecraftClient client, int x, int y, float currentEnergy, float maxEnergy) {
        Text text = Text.literal(String.format("MANA  %.0f / %.0f", currentEnergy, maxEnergy));
        context.drawText(client.textRenderer, text, x + 1, y + 1, COLOR_TEXT_SHADOW, false);
        context.drawText(client.textRenderer, text, x, y, COLOR_WHITE, false);
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

        context.getMatrices().push();
        context.getMatrices().translate(bounds.x, bounds.y, 0);
        context.getMatrices().scale(PLAYER_UI_SCALE, PLAYER_UI_SCALE, 1.0F);

        context.fill(2, 2, logicalWidth + 2, logicalHeight + 2, 0x55000000);
        context.fill(0, 0, logicalWidth, logicalHeight, 0x8805030C);
        drawRectBorder(context, 0, 0, logicalWidth, logicalHeight, 0x665E4DD8);

        int textY = PLAYER_PANEL_PADDING;
        for (int i = 0; i < lines.size(); i++) {
            int color = i == 0 ? 0xFFA9E8FF : COLOR_WHITE;
            context.drawText(client.textRenderer, lines.get(i), PLAYER_PANEL_PADDING, textY, color, true);
            textY += PLAYER_LINE_HEIGHT;
        }
        context.getMatrices().pop();
    }

    private static void drawRecalcLogo(DrawContext context, MinecraftClient client, int x, int y) {
        Text logo = Text.literal("Recalc").setStyle(Style.EMPTY.withFont(RECALC_FONT));
        float pulse = (float)(0.82 + Math.sin(System.currentTimeMillis() / 520.0) * 0.12);

        int textWidth = client.textRenderer.getWidth(logo);
        int flareAlpha = Math.round(42 * pulse);
        int flareColor = HudRenderUtils.withAlpha(0x66DFFF, flareAlpha);
        context.fill(x - 4, y + client.textRenderer.fontHeight / 2, x + textWidth + 4, y + client.textRenderer.fontHeight / 2 + 1, flareColor);

        HudRenderUtils.drawGlowingText(
            context,
            client.textRenderer,
            logo,
            x,
            y,
            0xFFFFFF,
            0x65DFFF,
            pulse,
            true
        );
    }

    private static List<Text> getManaDetails() {
        float current = BattleHUDManager.getEnergyCurrent();
        float max = BattleHUDManager.getEnergyMax();
        float percent = max <= 0 ? 0 : clamp(current / max, 0.0f, 1.0f);

        Text state;
        if (percent >= 0.99f) {
            state = Text.translatable("hud.recalc.mana_state.full");
        } else if (percent <= 0.2f) {
            state = Text.translatable("hud.recalc.mana_state.low");
        } else {
            state = Text.translatable("hud.recalc.mana_state.stable");
        }

        return List.of(
            Text.translatable("hud.recalc.mana_title").formatted(Formatting.AQUA),
            Text.translatable("hud.recalc.mana_current", String.format("%.0f", current)),
            Text.translatable("hud.recalc.mana_capacity", String.format("%.0f", max)),
            Text.translatable("hud.recalc.mana_charge", String.format("%.0f", percent * 100)),
            Text.translatable("hud.recalc.mana_state", state)
        );
    }

    private static List<Text> getPlayerDetails(MinecraftClient client) {
        List<Text> lines = new ArrayList<>();
        int chunkX = Math.floorDiv(client.player.getBlockX(), 16);
        int chunkZ = Math.floorDiv(client.player.getBlockZ(), 16);

        lines.add(Text.translatable("hud.recalc.player_title").formatted(Formatting.LIGHT_PURPLE));
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
        int width = Math.round(getPlayerPanelLogicalWidth(client, lines) * PLAYER_UI_SCALE);
        int height = Math.round(getPlayerPanelLogicalHeight(lines) * PLAYER_UI_SCALE);
        return new Bounds(
            screenWidth - PLAYER_PANEL_MARGIN - width,
            screenHeight - PLAYER_PANEL_MARGIN - height,
            width,
            height
        );
    }

    private static Bounds manaBounds() {
        return new Bounds(
            MANA_X,
            MANA_Y,
            Math.round(MANA_PANEL_WIDTH * MANA_UI_SCALE),
            Math.round(MANA_PANEL_HEIGHT * MANA_UI_SCALE)
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

    private static void drawDiamond(DrawContext context, int centerX, int centerY, int radius, int color) {
        for (int dy = -radius; dy <= radius; dy++) {
            int halfWidth = radius - Math.abs(dy);
            context.fill(centerX - halfWidth, centerY + dy, centerX + halfWidth + 1, centerY + dy + 1, color);
        }
    }

    private static void drawRectBorder(DrawContext context, int x, int y, int width, int height, int color) {
        context.fill(x, y, x + width, y + 1, color);
        context.fill(x, y + height - 1, x + width, y + height, color);
        context.fill(x, y, x + 1, y + height, color);
        context.fill(x + width - 1, y, x + width, y + height, color);
    }

    private static int blend(int startColor, int endColor, float progress) {
        float amount = clamp(progress, 0.0f, 1.0f);
        int a = lerp((startColor >>> 24) & 0xFF, (endColor >>> 24) & 0xFF, amount);
        int r = lerp((startColor >>> 16) & 0xFF, (endColor >>> 16) & 0xFF, amount);
        int g = lerp((startColor >>> 8) & 0xFF, (endColor >>> 8) & 0xFF, amount);
        int b = lerp(startColor & 0xFF, endColor & 0xFF, amount);
        return a << 24 | r << 16 | g << 8 | b;
    }

    private static int lerp(int start, int end, float amount) {
        return Math.round(start + (end - start) * amount);
    }

    private static float clamp(float value, float min, float max) {
        return Math.max(min, Math.min(value, max));
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
