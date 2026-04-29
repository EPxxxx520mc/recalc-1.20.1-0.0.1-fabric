
package com.rimeveil.recalc.client;

import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.ui.RecalcSettingsScreen;
import com.rimeveil.recalc.util.LogUtil;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.math.MathHelper;

public class RecalcBattleHUD {
    private static final int COLOR_WHITE = 0xFFFFFFFF;
    private static final int COLOR_TECH_BLUE = 0xFF88CCFF;
    private static final int COLOR_DARK_BLUE = 0xFF003366;
    private static final int COLOR_GRAY_BG = 0x66000000;
    
    private static float glowIntensity = 0.0f;
    private static long lastClickTime = 0;
    private static final long CLICK_COOLDOWN = 300;
    
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
        
        glowIntensity = (float)(Math.sin(client.world.getTime() * 0.1f) * 0.3f + 0.5f);
        
        int screenWidth = context.getScaledWindowWidth();
        int screenHeight = context.getScaledWindowHeight();
        
        drawEnergyBar(context, client, 20, 20);
        
        drawSettingsButton(context, client, screenWidth - 60, 20);
        
        drawPlayerInfo(context, client, screenWidth - 20, screenHeight - 80);
        
        drawRecalcLogo(context, client, 20, screenHeight - 60);
        
        if (BattleHUDManager.isCursorVisible()) {
            checkSettingsButtonClick(context, client, screenWidth - 60, 20);
        }
    }
    
    private static void drawEnergyBar(DrawContext context, MinecraftClient client, int x, int y) {
        int barWidth = 180;
        int barHeight = 24;
        int borderThickness = 2;
        
        float currentEnergy = BattleHUDManager.getEnergyCurrent();
        float maxEnergy = BattleHUDManager.getEnergyMax();
        float fillPercent = currentEnergy / maxEnergy;
        
        int glowAlpha = (int)(glowIntensity * 40);
        int glowColor = (glowAlpha << 24) | (COLOR_TECH_BLUE & 0x00FFFFFF);
        
        context.fill(
            x - borderThickness - 3, y - borderThickness - 3,
            x + barWidth + borderThickness + 3, y + barHeight + borderThickness + 3,
            glowColor
        );
        
        context.fill(
            x - borderThickness, y - borderThickness,
            x + barWidth + borderThickness, y + barHeight + borderThickness,
            COLOR_GRAY_BG
        );
        
        int fillWidth = (int)(barWidth * fillPercent);
        
        int energyColorTop = COLOR_WHITE;
        int energyColorBottom = 0xFF66AAFF;
        
        context.fillGradient(x, y, x + fillWidth, y + barHeight, energyColorTop, energyColorBottom);
        
        int borderColor = ((int)(glowIntensity * 80) << 24) | (COLOR_TECH_BLUE & 0x00FFFFFF);
        
        context.fill(x - borderThickness, y - borderThickness, x + barWidth + borderThickness, y, borderColor);
        context.fill(x - borderThickness, y + barHeight, x + barWidth + borderThickness, y + barHeight + borderThickness, borderColor);
        context.fill(x - borderThickness, y, x, y + barHeight, borderColor);
        context.fill(x + barWidth, y, x + barWidth + borderThickness, y + barHeight, borderColor);
        
        String energyText = String.format("%.0f / %.0f", currentEnergy, maxEnergy);
        Text styledText = Text.literal(energyText);
        
        int textWidth = client.textRenderer.getWidth(styledText);
        int textX = x + barWidth / 2 - textWidth / 2;
        int textY = y + (barHeight - client.textRenderer.fontHeight) / 2 + 1;
        
        context.drawText(client.textRenderer, styledText, textX, textY, COLOR_WHITE, true);
    }
    
    private static void drawSettingsButton(DrawContext context, MinecraftClient client, int x, int y) {
        int size = 40;
        boolean isHovered = false;
        
        if (BattleHUDManager.isCursorVisible()) {
            int mouseX = (int)client.mouse.getX();
            int mouseY = client.getWindow().getScaledHeight() - (int)client.mouse.getY();
            isHovered = (mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size);
        }
        
        int glowAlpha = (int)(glowIntensity * 50);
        int glowColor = (glowAlpha << 24) | (COLOR_TECH_BLUE & 0x00FFFFFF);
        
        context.fill(x - 3, y - 3, x + size + 3, y + size + 3, glowColor);
        
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
        
        if (isClicked && System.currentTimeMillis() - lastClickTime > CLICK_COOLDOWN) {
            if (mouseX >= x && mouseX <= x + size && mouseY >= y && mouseY <= y + size) {
                lastClickTime = System.currentTimeMillis();
                LogUtil.debug("Settings button clicked");
                client.setScreen(new RecalcSettingsScreen());
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
        
        int bgWidth = 200;
        int bgHeight = 80;
        
        int bgX = x - bgWidth;
        int bgY = y - bgHeight;
        
        int glowAlpha = (int)(glowIntensity * 40);
        int glowColor = (glowAlpha << 24) | (COLOR_TECH_BLUE & 0x00FFFFFF);
        
        context.fill(bgX - 3, bgY - 3, x + 3, y + 3, glowColor);
        
        context.fill(bgX, bgY, x, y, COLOR_GRAY_BG);
        
        int borderColor = ((int)(glowIntensity * 80) << 24) | (COLOR_TECH_BLUE & 0x00FFFFFF);
        context.fill(bgX, bgY, x, bgY + 2, borderColor);
        context.fill(bgX, y - 2, x, y, borderColor);
        context.fill(bgX, bgY, bgX + 2, y, borderColor);
        context.fill(x - 2, bgY, x, y, borderColor);
        
        int textY = bgY + 10;
        
        Text name = Text.literal(playerName);
        context.drawText(client.textRenderer, name, bgX + 10, textY, COLOR_WHITE, true);
        textY += 16;
        
        Text version = Text.literal(versionText);
        context.drawText(client.textRenderer, version, bgX + 10, textY, COLOR_TECH_BLUE, true);
        textY += 16;
        
        Text coords = Text.literal(coordsText);
        context.drawText(client.textRenderer, coords, bgX + 10, textY, COLOR_WHITE, true);
        textY += 16;
        
        Text dimension = Text.literal(dimensionText);
        context.drawText(client.textRenderer, dimension, bgX + 10, textY, COLOR_WHITE, true);
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
        
        Text styledText = Text.literal(logoText);
        
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
}
