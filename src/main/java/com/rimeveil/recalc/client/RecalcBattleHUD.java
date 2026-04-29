
package com.rimeveil.recalc.client;

import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.ui.RecalcSettingsScreen;
import com.rimeveil.recalc.util.LogUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;
import com.mojang.blaze3d.systems.RenderSystem;

public class RecalcBattleHUD {
    private static final Identifier RECALC_FONT = new Identifier(Recalc.MOD_ID, "recalc_font");
    private static final Identifier SLOT_GLOW_TEXTURE = new Identifier(Recalc.MOD_ID, "textures/ui/slot_glow.png");
    
    private static final int COLOR_WHITE = 0xFFFFFFFF;
    private static final int COLOR_TECH_BLUE = 0xFF88CCFF;
    private static final int COLOR_DARK_BLUE = 0xFF003366;
    private static final int COLOR_GRAY_BG = 0x66000000;
    private static final int COLOR_EMPTY_SLOT = 0x33000000;
    private static final int COLOR_FILLED_SLOT = 0xFF66AAFF;
    private static final int COLOR_SLOT_BORDER = 0xFF88CCFF;
    
    private static final int SLOT_COUNT = 10;
    private static final int SLOT_WIDTH = 18;
    private static final int SLOT_HEIGHT = 24;
    private static final int SLOT_SPACING = 4;
    private static final int BORDER_THICKNESS = 2;
    private static final int GLOW_EXTEND = 2;
    
    public static void register() {
        HudRenderCallback.EVENT.register(RecalcBattleHUD::render);
    }
    
    private static void render(DrawContext context, float tickDelta) {
        if (!BattleHUDManager.isHUDVisible()) {
            return;
        }
        
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || client.player == null || client.world == null) {
            return;
        }
        
        double scale = client.options.getGuiScale().getValue();
        if (scale == 0) scale = 1;
        
        int scaledWidth = (int)(context.getScaledWindowWidth() / scale);
        int scaledHeight = (int)(context.getScaledWindowHeight() / scale);
        
        context.getMatrices().push();
        context.getMatrices().scale((float)scale, (float)scale, 1.0f);
        
        drawEnergyBar(context, client, 20, 20);
        drawSettingsButton(context, client, scaledWidth - 60, 20);
        
        int playerInfoX = scaledWidth - 10;
        int playerInfoY = scaledHeight - 10;
        drawPlayerInfo(context, client, playerInfoX, playerInfoY);
        
        drawRecalcLogo(context, client, 20, scaledHeight - 30);
        
        if (BattleHUDManager.isCursorVisible()) {
            checkSettingsButtonClick(context, client, scaledWidth - 60, 20);
        }
    }
    
    private static void drawEnergyBar(DrawContext context, MinecraftClient client, int x, int y) {
        float currentEnergy = BattleHUDManager.getEnergyCurrent();
        float maxEnergy = BattleHUDManager.getEnergyMax();
        float fillPercent = Math.min(1.0f, currentEnergy / maxEnergy);
        
        int totalWidth = SLOT_COUNT * SLOT_WIDTH + (SLOT_COUNT - 1) * SLOT_SPACING + BORDER_THICKNESS * 2;
        int totalHeight = SLOT_HEIGHT + BORDER_THICKNESS * 2;
        
        context.fill(x, y, x + totalWidth, y + totalHeight, COLOR_GRAY_BG);
        
        int borderColor = 0xCC88CCFF;
        context.fill(x, y, x + totalWidth, y + BORDER_THICKNESS, borderColor);
        context.fill(x, y + totalHeight - BORDER_THICKNESS, x + totalWidth, y + totalHeight, borderColor);
        context.fill(x, y, x + BORDER_THICKNESS, y + totalHeight, borderColor);
        context.fill(x + totalWidth - BORDER_THICKNESS, y, x + totalWidth, y + totalHeight, borderColor);
        
        int currentX = x + BORDER_THICKNESS;
        for (int i = 0; i < SLOT_COUNT; i++) {
            float slotPercent = Math.min(1.0f, Math.max(0.0f, fillPercent * SLOT_COUNT - i));
            drawSlot(context, currentX, y + BORDER_THICKNESS, slotPercent > 0, slotPercent);
            currentX += SLOT_WIDTH + SLOT_SPACING;
        }
        
        String energyText = String.format("%.0f / %.0f", currentEnergy, maxEnergy);
        Text styledText = Text.literal(energyText);
        int textWidth = client.textRenderer.getWidth(styledText);
        int textX = x + totalWidth / 2 - textWidth / 2;
        int textY = y + totalHeight + 5;
        context.drawText(client.textRenderer, styledText, textX, textY, COLOR_WHITE, true);
    }
    
    private static void drawSlot(DrawContext context, int x, int y, boolean hasGlow, float fillPercent) {
        context.fill(x, y, x + SLOT_WIDTH, y + SLOT_HEIGHT, COLOR_EMPTY_SLOT);
        
        if (fillPercent > 0) {
            int fillWidth = (int)(SLOT_WIDTH * fillPercent);
            context.fill(x, y, x + fillWidth, y + SLOT_HEIGHT, COLOR_FILLED_SLOT);
            context.fill(x, y, x + fillWidth, y + 2, COLOR_WHITE);
        }
        
        context.fill(x, y, x + SLOT_WIDTH, y + 1, COLOR_SLOT_BORDER);
        context.fill(x, y + SLOT_HEIGHT - 1, x + SLOT_WIDTH, y + SLOT_HEIGHT, COLOR_SLOT_BORDER);
        context.fill(x, y, x + 1, y + SLOT_HEIGHT, COLOR_SLOT_BORDER);
        context.fill(x + SLOT_WIDTH - 1, y, x + SLOT_WIDTH, y + SLOT_HEIGHT, COLOR_SLOT_BORDER);
        
        if (hasGlow) {
            drawSlotGlow(context, x, y, fillPercent);
        }
    }
    
    private static void drawSlotGlow(DrawContext context, int x, int y, float fillPercent) {
        boolean isFull = fillPercent >= 1.0f;
        double speed = isFull ? 300.0 : 500.0;
        double alpha = Math.sin(System.currentTimeMillis() / speed) * 0.25 + 0.75;
        
        int glowX = x - GLOW_EXTEND;
        int glowY = y - GLOW_EXTEND;
        int glowWidth = SLOT_WIDTH + GLOW_EXTEND * 2;
        int glowHeight = SLOT_HEIGHT + GLOW_EXTEND * 2;
        
        RenderSystem.enableBlend();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, (float)alpha);
        
        try {
            context.drawTexture(SLOT_GLOW_TEXTURE, glowX, glowY, 0, 0, glowWidth, glowHeight, glowWidth, glowHeight);
        } catch (Exception e) {
            int fillWidth = (int)(SLOT_WIDTH * fillPercent);
            int glowColor = ((int)(alpha * 180) << 24) | (COLOR_TECH_BLUE & 0x00FFFFFF);
            context.fill(x - GLOW_EXTEND, y - GLOW_EXTEND, x + fillWidth + GLOW_EXTEND, y + SLOT_HEIGHT + GLOW_EXTEND, glowColor);
        }
        
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.disableBlend();
    }
    
    private static void drawSettingsButton(DrawContext context, MinecraftClient client, int x, int y) {
        int size = 40;
        boolean isHovered = false;
        
        if (BattleHUDManager.isCursorVisible()) {
            int mouseX = (int)client.mouse.getX();
            int mouseY = client.getWindow().getScaledHeight() - (int)client.mouse.getY();
            isHovered = (mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size);
        }
        
        int bgColor = isHovered ? 0x88003366 : 0x66000000;
        context.fill(x, y, x + size, y + size, bgColor);
        
        int borderColor = isHovered ? COLOR_WHITE : COLOR_TECH_BLUE;
        context.fill(x, y, x + size, y + 2, borderColor);
        context.fill(x, y + size - 2, x + size, y + size, borderColor);
        context.fill(x, y, x + 2, y + size, borderColor);
        context.fill(x + size - 2, y, x + size, y + size, borderColor);
        
        int centerX = x + size / 2;
        int centerY = y + size / 2;
        int gearRadius = 12;
        
        for (int i = 0; i < 8; i++) {
            double angle = Math.PI * 2 * i / 8;
            int tx = centerX + (int)(Math.cos(angle) * (gearRadius - 3));
            int ty = centerY + (int)(Math.sin(angle) * (gearRadius - 3));
            context.fill(tx - 2, ty - 2, tx + 2, ty + 2, borderColor);
        }
        
        context.fill(centerX - 5, centerY - 5, centerX + 5, centerY + 5, COLOR_DARK_BLUE);
        context.fill(centerX - 4, centerY - 4, centerX + 4, centerY + 4, borderColor);
    }
    
    private static void checkSettingsButtonClick(DrawContext context, MinecraftClient client, int x, int y) {
        int size = 40;
        
        if (!Screen.hasAltDown()) {
            return;
        }
        
        int mouseX = (int)client.mouse.getX();
        int mouseY = client.getWindow().getScaledHeight() - (int)client.mouse.getY();
        
        boolean isClicked = client.mouse.wasLeftButtonClicked();
        
        if (isClicked) {
            long now = System.currentTimeMillis();
            if (now - (lastClickTime) > 300) {
                if (mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size) {
                    lastClickTime = now;
                    LogUtil.debug("Settings button clicked");
                    client.setScreen(new RecalcSettingsScreen());
                }
            }
        }
    }
    
    private static void drawPlayerInfo(DrawContext context, MinecraftClient client, int x, int y) {
        if (client.player == null || client.world == null) {
            return;
        }
        
        String playerName = client.player.getName().getString();
        String versionText = String.format("v%s", Recalc.MOD_VERSION);
        String coordsText = String.format("X: %.0f Y: %.0f Z: %.0f",
            client.player.getX(), client.player.getY(), client.player.getZ());
        String dimensionText = getDimensionName(client.world.getRegistryKey().getValue().getPath());
        
        int lineHeight = 12;
        int currentY = y;
        
        Text name = Text.literal(playerName);
        int nameWidth = client.textRenderer.getWidth(name);
        context.drawText(client.textRenderer, name, x - nameWidth, currentY, COLOR_WHITE, true);
        currentY -= lineHeight;
        
        Text version = Text.literal(versionText);
        int versionWidth = client.textRenderer.getWidth(version);
        context.drawText(client.textRenderer, version, x - versionWidth, currentY, COLOR_TECH_BLUE, true);
        currentY -= lineHeight;
        
        Text coords = Text.literal(coordsText);
        int coordsWidth = client.textRenderer.getWidth(coords);
        context.drawText(client.textRenderer, coords, x - coordsWidth, currentY, COLOR_WHITE, true);
        currentY -= lineHeight;
        
        Text dimension = Text.literal(dimensionText);
        int dimensionWidth = client.textRenderer.getWidth(dimension);
        context.drawText(client.textRenderer, dimension, x - dimensionWidth, currentY, COLOR_WHITE, true);
    }
    
    private static String getDimensionName(String dimension) {
        return switch (dimension) {
            case "overworld" -> "主世界";
            case "the_nether" -> "下界";
            case "the_end" -> "末地";
            default -> dimension;
        };
    }
    
    private static void drawRecalcLogo(DrawContext context, MinecraftClient client, int x, int y) {
        String logoText = "Recalc";
        
        Text styledText = Text.literal(logoText).setStyle(net.minecraft.text.Style.EMPTY.withFont(RECALC_FONT));
        
        float glowIntensity = (float)(Math.sin(System.currentTimeMillis() / 500.0) * 0.3f + 0.5f);
        for (int i = 0; i < 3; i++) {
            int offset = (3 - i) * 2;
            int layerAlpha = (int)(glowIntensity * (0.4f - i * 0.1f) * 255);
            if (layerAlpha > 0) {
                int layerColor = (layerAlpha << 24) | (COLOR_TECH_BLUE & 0x00FFFFFF);
                context.drawText(client.textRenderer, styledText, x + offset, y + offset, layerColor, false);
                context.drawText(client.textRenderer, styledText, x - offset, y - offset, layerColor, false);
            }
        }
        
        context.drawText(client.textRenderer, styledText, x, y, COLOR_WHITE, true);
    }
    
    private static long lastClickTime = 0;
}
