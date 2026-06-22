package com.rimeveil.recalc.ui;

import com.rimeveil.recalc.client.RecalcBattleHUD;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class BattleHudInteractionScreen extends Screen {
    public BattleHudInteractionScreen() {
        super(Text.empty());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.client != null) {
            RecalcBattleHUD.renderInteractionOverlay(context, this.client, mouseX, mouseY);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button == 0
            && this.client != null
            && RecalcBattleHUD.handleInteractionClick(this.client, (int)mouseX, (int)mouseY)) {
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
