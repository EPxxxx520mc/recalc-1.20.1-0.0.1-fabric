package com.rimeveil.recalc.ui;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class RecalcSettingsScreen extends Screen {
    private static final int PANEL_WIDTH = 300;
    private static final int PANEL_HEIGHT = 200;
    private static final int BORDER_SIZE = 2;
    private static final int BUTTON_WIDTH = 120;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BACK_BUTTON_Y_OFFSET = 50;
    private static final int TITLE_Y_OFFSET = 30;
    private static final int PANEL_COLOR = 0x24050A0E;
    private static final int BORDER_COLOR = 0xB8FFFFFF;
    private static final int ACCENT_COLOR = 0xFFB9F3FA;
    private static final int TEXT_COLOR = 0xFFFFFFFF;

    public RecalcSettingsScreen() {
        super(Text.translatable("ui.recalc.settings"));
    }

    @Override
    protected void init() {
        super.init();
        int buttonX = this.width / 2 - BUTTON_WIDTH / 2;
        int buttonY = this.height / 2 + BACK_BUTTON_Y_OFFSET;

        this.addDrawableChild(ButtonWidget.builder(
            Text.translatable("ui.recalc.back"),
            button -> close()
        ).dimensions(buttonX, buttonY, BUTTON_WIDTH, BUTTON_HEIGHT).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int panelX = centerX - PANEL_WIDTH / 2;
        int panelY = centerY - PANEL_HEIGHT / 2;

        drawPanel(context, panelX, panelY);
        drawCenteredTitle(context, centerX, panelY + TITLE_Y_OFFSET);

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public void close() {
        if (this.client != null) {
            this.client.setScreen(null);
        }
    }

    private static void drawPanel(DrawContext context, int x, int y) {
        context.fill(x, y, x + PANEL_WIDTH, y + PANEL_HEIGHT, PANEL_COLOR);
        int corner = 18;
        context.fill(x, y, x + corner, y + BORDER_SIZE, BORDER_COLOR);
        context.fill(x, y, x + BORDER_SIZE, y + corner, BORDER_COLOR);
        context.fill(x + PANEL_WIDTH - corner, y, x + PANEL_WIDTH, y + BORDER_SIZE, BORDER_COLOR);
        context.fill(x + PANEL_WIDTH - BORDER_SIZE, y, x + PANEL_WIDTH, y + corner, BORDER_COLOR);
        context.fill(x, y + PANEL_HEIGHT - BORDER_SIZE, x + corner, y + PANEL_HEIGHT, BORDER_COLOR);
        context.fill(x, y + PANEL_HEIGHT - corner, x + BORDER_SIZE, y + PANEL_HEIGHT, BORDER_COLOR);
        context.fill(x + PANEL_WIDTH - corner, y + PANEL_HEIGHT - BORDER_SIZE, x + PANEL_WIDTH, y + PANEL_HEIGHT, BORDER_COLOR);
        context.fill(x + PANEL_WIDTH - BORDER_SIZE, y + PANEL_HEIGHT - corner, x + PANEL_WIDTH, y + PANEL_HEIGHT, BORDER_COLOR);
        context.fill(x + PANEL_WIDTH - 72, y, x + PANEL_WIDTH, y + 1, ACCENT_COLOR);
    }

    private void drawCenteredTitle(DrawContext context, int centerX, int y) {
        Text title = Text.translatable("ui.recalc.settings");
        int titleX = centerX - this.textRenderer.getWidth(title) / 2;
        context.drawText(this.textRenderer, title, titleX, y, TEXT_COLOR, true);
        context.fill(centerX - 28, y + this.textRenderer.fontHeight + 4, centerX + 28, y + this.textRenderer.fontHeight + 5, ACCENT_COLOR);
    }
}
