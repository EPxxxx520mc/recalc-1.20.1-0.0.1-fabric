package com.rimeveil.recalc.datagen;

import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.block.Modblock;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class ModEnUslangProvider extends FabricLanguageProvider{
    public ModEnUslangProvider(FabricDataOutput dataOutput) {
        super(dataOutput, "en_us");
    }
    @Override
    public void generateTranslations(TranslationBuilder translationBuilder) {
        // Item Translations
        translationBuilder.add(Moditem.EXAMPLE_ITEM, "Sugar Crystal");
        translationBuilder.add(Moditem.EXAMPLE_ITEM2, "Refined Sugar Ingot");
        translationBuilder.add(Moditem.SUPER_SUGAR, "Super Sugar");
        translationBuilder.add(Moditem.CORN, "Corn");
        translationBuilder.add(Moditem.SUPER_COAL, "Super Coal");
        
        // Block Translations
        translationBuilder.add(Modblock.EXAMPLE_BLOCK, "Sugar Crystal Block");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK2, "Refined Sugar Block");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK3, "Sugar Ore");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK4, "Compressed Sugar Block");
        translationBuilder.add(Modblock.EXAMPLE_BLOCK5, "Polished Sugar Block");
        
        // Super Sugar Blocks
        translationBuilder.add(Modblock.SUPER_SUAR_ORE, "Super Sugar Ore");
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
        translationBuilder.add(Moditem.SUPER_SUGAR_SWORD, "Super Sugar Sword");
        translationBuilder.add(Moditem.SUPER_SUGAR_PICKAXE, "Super Sugar Pickaxe");
        translationBuilder.add(Moditem.SUPER_SUGAR_AXE, "Super Sugar Axe");
        translationBuilder.add(Moditem.SUPER_SUGAR_SHOVEL, "Super Sugar Shovel");
        translationBuilder.add(Moditem.SUPER_SUGAR_HOE, "Super Sugar Hoe");
        translationBuilder.add(Moditem.SUPER_SUGAR_PICKAXE_AXE, "Super Sugar Pickaxe Axe");
        
        // Item Group / Other Translations
        translationBuilder.add("itemGroup.recalc", "Recalc");
    }
}
