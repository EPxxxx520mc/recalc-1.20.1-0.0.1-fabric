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
        translationBuilder.add(Moditem.SUPER_SUGAR_SWORD, "Super Sugar Sword");
        translationBuilder.add(Moditem.SUPER_SUGAR_PICKAXE, "Super Sugar Pickaxe");
        translationBuilder.add(Moditem.SUPER_SUGAR_AXE, "Super Sugar Axe");
        translationBuilder.add(Moditem.SUPER_SUGAR_SHOVEL, "Super Sugar Shovel");
        translationBuilder.add(Moditem.SUPER_SUGAR_HOE, "Super Sugar Hoe");
        translationBuilder.add(Moditem.SUPER_SUGAR_PICKAXE_AXE, "Super Sugar Pickaxe Axe");
        translationBuilder.add("item.recalc.pickaxe_axe.tooltip.shift", "This item can be used as a pickaxe and axe at the same time");
        translationBuilder.add("item.recalc.pickaxe_axe.tooltip", "Press §1§l§oShift§r§r§r for more information");
        translationBuilder.add(Moditem.SUPER_SUGAR_HELMET, "Super Sugar Helmet");
        translationBuilder.add(Moditem.SUPER_SUGAR_CHESTPLATE, "Super Sugar Chestplate");
        translationBuilder.add(Moditem.SUPER_SUGAR_LEGGINGS, "Super Sugar Leggings");
        translationBuilder.add(Moditem.SUPER_SUGAR_BOOTS, "Super Sugar Boots");
        translationBuilder.add(Moditem.SUPER_SUGAR_HORSE_ARMOR, "Super Sugar Horse Armor");
        translationBuilder.add(Moditem.SUPER_SUGAR_3D_TOY_SWORD, "Super Sugar 3D Toy Sword");
        //\u00A7 = § 
        //§0	黑色	\u00A70
        //§1	深蓝色	\u00A71
        //§2	深绿色	\u00A72
        //§3	湖蓝色	\u00A73
        //§4	深红色	\u00A74
        //§5	紫色	\u00A75
        //§6	金色	\u00A76
        //§7	灰色	\u00A77
        //§8	深灰色	\u00A78
        //§9	蓝色	\u00A79
        //§a	绿色	\u00A7a
        //§b	天蓝色	\u00A7b
        //§c	红色	\u00A7c
        //§d	粉红色	\u00A7d
        //§e	黄色	\u00A7e
        //§f	白色	\u00A7f
        //§k	随机字符	\u00A7k
        //§l	粗体	\u00A7l
        //§m	删除线	\u00A7m
        //§n	下划线	\u00A7n
        //§o	斜体	\u00A7o
        //§r	重置	\u00A7r
        //\n	换行	\n
        // Item Group / Other Translations
        translationBuilder.add("itemGroup.recalc", "Recalc");
    }
}
