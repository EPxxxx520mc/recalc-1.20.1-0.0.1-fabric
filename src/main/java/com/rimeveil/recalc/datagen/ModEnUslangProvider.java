package com.rimeveil.recalc.datagen;

import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.block.Modblock;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class ModEnUslangProvider extends FabricLanguageProvider {
    public ModEnUslangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        translationBuilder.add(Moditem.EXAMPLE_ITEM, "Sugar Crystal");
        translationBuilder.add(Moditem.EXAMPLE_ITEM2, "Refined Sugar Ingot");
        translationBuilder.add(Moditem.SUPER_SUGAR, "Super Sugar");
        translationBuilder.add(Moditem.CORN, "Corn");
        translationBuilder.add(Moditem.SUPER_COAL, "Super Coal");
        translationBuilder.add(Moditem.SUPER_SUGAR_SWORD, "Super Sugar Sword");
        translationBuilder.add(Moditem.SUPER_SUGAR_PICKAXE, "Super Sugar Pickaxe");
        translationBuilder.add(Moditem.SUPER_SUGAR_AXE, "Super Sugar Axe");
        translationBuilder.add(Moditem.SUPER_SUGAR_SHOVEL, "Super Sugar Shovel");
        translationBuilder.add(Moditem.SUPER_SUGAR_HOE, "Super Sugar Hoe");
        translationBuilder.add(Moditem.SUPER_SUGAR_PICKAXE_AXE, "Super Sugar Pickaxe Axe");
        translationBuilder.add(Moditem.SUPER_SUGAR_HELMET, "Super Sugar Helmet");
        translationBuilder.add(Moditem.SUPER_SUGAR_CHESTPLATE, "Super Sugar Chestplate");
        translationBuilder.add(Moditem.SUPER_SUGAR_LEGGINGS, "Super Sugar Leggings");
        translationBuilder.add(Moditem.SUPER_SUGAR_BOOTS, "Super Sugar Boots");
        translationBuilder.add(Moditem.SUPER_SUGAR_HORSE_ARMOR, "Super Sugar Horse Armor");
        translationBuilder.add(Moditem.SUPER_SUGAR_3D_TOY_SWORD, "Super Sugar 3D Toy Sword");
        translationBuilder.add(Moditem.FICTIONAL_FRAME, "Fictional Frame");

        translationBuilder.add(Modblock.EXAMPLE_BLOCK, "Sugar Crystal Block");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK2, "Refined Sugar Block");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK3, "Sugar Ore");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK4, "Compressed Sugar Block");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK5, "Polished Sugar Block");
        translationBuilder.add(Modblock.SUPER_SUGAR_ORE, "Super Sugar Ore");
        translationBuilder.add(Modblock.SUPER_SUGAR_BLOCK, "Super Sugar Block");
        translationBuilder.add(Modblock.SUPER_SUGAR_STAIRS, "Super Sugar Stairs");
        translationBuilder.add(Modblock.SUPER_SUGAR_SLAB, "Super Sugar Slab");
        translationBuilder.add(Modblock.SUPER_SUGAR_BUTTON, "Super Sugar Button");
        translationBuilder.add(Modblock.SUPER_SUGAR_PRESSURE_PLATE, "Super Sugar Pressure Plate");
        translationBuilder.add(Modblock.SUPER_SUGAR_FENCE, "Super Sugar Fence");
        translationBuilder.add(Modblock.SUPER_SUGAR_FENCE_GATE, "Super Sugar Fence Gate");
        translationBuilder.add(Modblock.SUPER_SUGAR_WALL, "Super Sugar Wall");
        translationBuilder.add(Modblock.SUPER_SUGAR_DOOR, "Super Sugar Door");
        translationBuilder.add(Modblock.SUPER_SUGAR_TRAPDOOR, "Super Sugar Trapdoor");

        translationBuilder.add("item.recalc.pickaxe_axe.tooltip", "Press \u00A71\u00A7l\u00A7oShift\u00A7r for more information");
        translationBuilder.add("item.recalc.pickaxe_axe.tooltip.shift", "This item can be used as a pickaxe and axe at the same time");
        translationBuilder.add("itemGroup.recalc", "Recalc");
        translationBuilder.add("key.recalc.toggle_battle_ui", "Toggle Battle UI");
        translationBuilder.add("category.recalc", "Recalc");
        translationBuilder.add("ui.recalc.battle", "Recalc Battle");
        translationBuilder.add("ui.recalc.settings", "Recalc Settings");
        translationBuilder.add("ui.recalc.back", "Back");
        translationBuilder.add("hud.recalc.settings", "Settings");
        translationBuilder.add("hud.recalc.mana_title", "Mana Details");
        translationBuilder.add("hud.recalc.mana_current", "Current Mana: %s");
        translationBuilder.add("hud.recalc.mana_capacity", "Capacity: %s");
        translationBuilder.add("hud.recalc.mana_charge", "Charge: %s%%");
        translationBuilder.add("hud.recalc.mana_state", "State: %s");
        translationBuilder.add("hud.recalc.mana_state.full", "Saturated");
        translationBuilder.add("hud.recalc.mana_state.stable", "Stable");
        translationBuilder.add("hud.recalc.mana_state.low", "Low");
        translationBuilder.add("hud.recalc.player_title", "Player Details");
        translationBuilder.add("hud.recalc.health", "Health: %s / %s");
        translationBuilder.add("hud.recalc.chunk", "Chunk: %s, %s");
        translationBuilder.add("hud.recalc.target_block", "Target Block: %s");
        translationBuilder.add("hud.recalc.target_position", "Block Position: %s, %s, %s");
        translationBuilder.add("hud.recalc.target_none", "Target Block: None");
        translationBuilder.add("message.recalc.frame_attached", "Fictional Frame has been attached to you!");
        translationBuilder.add("message.recalc.frame_already_attached", "You already have the Fictional Frame attached!");
        translationBuilder.add("message.recalc.no_frame", "No frame attached! Please use Fictional Frame first.");
        translationBuilder.add("message.recalc.wait_for_animation", "Please wait for the animation to complete!");
        translationBuilder.add("command.recalc.frame_removed", "Fictional Frame has been removed!");
        translationBuilder.add("command.recalc.only_player", "This command can only be executed by a player!");
    }
}
