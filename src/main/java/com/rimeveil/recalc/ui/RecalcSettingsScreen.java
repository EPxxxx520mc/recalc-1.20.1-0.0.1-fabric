
package com.rimeveil.recalc.ui;

import com.rimeveil.recalc.Recalc;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class RecalcSettingsScreen extends Screen {
    
    public RecalcSettingsScreen() {
        super(Text.translatable("ui.recalc.settings"));
    }
    
    @Override
    protected void init() {
        super.init();
        this.children().clear();
        
        int buttonWidth = 120;
        int buttonHeight = 20;
        int buttonX = this.width / 2 - buttonWidth / 2;
        int buttonY = this.height / 2 + 50;
        
        ButtonWidget backButton = ButtonWidget.builder(
            Text.translatable("ui.recalc.back"),
            button -> {
                if (this.client != null) {
                    this.client.setScreen(null);
                }
            }).dimensions(buttonX, buttonY, buttonWidth, buttonHeight).build();
        
        this.addDrawableChild(backButton);
    }
    
    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        
        int bgWidth = 300;
        int bgHeight = 200;
        int bgX = centerX - bgWidth / 2;
        int bgY = centerY - bgHeight / 2;
        
        context.fill(bgX, bgY, bgX + bgWidth, bgY + bgHeight, 0xCC000000);
        
        context.fill(bgX, bgY, bgX + bgWidth, bgY + 2, 0x9988CCFF);
        context.fill(bgX, bgY + bgHeight - 2, bgX + bgWidth, bgY + bgHeight, 0x9988CCFF);
        context.fill(bgX, bgY, bgX + 2, bgY + bgHeight, 0x9988CCFF);
        context.fill(bgX + bgWidth - 2, bgY, bgX + bgWidth, bgY + bgHeight, 0x9988CCFF);
        
        Text title = Text.translatable("ui.recalc.settings");
        context.drawText(this.textRenderer, title, centerX - this.textRenderer.getWidth(title) / 2, bgY + 30, 0xFFFFFFFF, false);
        
        super.render(context, mouseX, mouseY, delta);
    }
    
    @Override
    public boolean shouldPause() {
        return false;
    }
}
