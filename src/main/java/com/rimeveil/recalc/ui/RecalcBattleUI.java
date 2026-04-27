package com.rimeveil.recalc.ui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

public class RecalcBattleUI extends Screen {
    public RecalcBattleUI() {
        super(Text.literal("recalc_battle"));
    }

    @Override
    protected void init() {
        super.init();
        this.children().clear();
        ButtonWidget btn = ButtonWidget.builder(
            Text.literal("recalc_battle"),
            button -> {
                // 这个按钮现在不做任何事，防止关闭
            }).dimensions(width / 2 - 50, height / 2, 100, 20).build();
        this.addDrawableChild(btn);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        super.render(context, mouseX, mouseY, delta);

        context.fill(
            width / 2 - 100, height / 2 - 50,
            width / 2 + 100, height / 2 + 80,
            0x88000000);

        String tip = "Recalc Battle";
        TextRenderer textRenderer = this.textRenderer;
        context.drawText(textRenderer, tip, width / 2 - textRenderer.getWidth(tip) / 2, height / 2 - 20, 0xFFFFFFFF, false);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }
}