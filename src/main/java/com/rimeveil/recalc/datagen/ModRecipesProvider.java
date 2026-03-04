package com.rimeveil.recalc.datagen;

import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.block.Modblock;
import com.rimeveil.recalc.Recalc;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Identifier;
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.RecipeSerializer;


import java.util.function.Consumer;
import java.util.List;

public class ModRecipesProvider extends FabricRecipeProvider {
    public ModRecipesProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    public static final List<ItemConvertible> EXAMPLE_ITEM2_LIST =  List.of(Moditem.EXAMPLE_ITEM,Modblock.EXAMPLE_BLOCK3);
    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        // 可逆压缩配方 (例如：9个物品 -> 1个方块，1个方块 -> 9个物品)
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, Moditem.EXAMPLE_ITEM, 
            RecipeCategory.BUILDING_BLOCKS, Modblock.EXAMPLE_BLOCK);
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, Moditem.EXAMPLE_ITEM2, 
            RecipeCategory.BUILDING_BLOCKS, Modblock.EXAMPLE_BLOCK2);

        // 熔炼与高炉配方 (针对一组输入)
        offerSmelting(exporter, EXAMPLE_ITEM2_LIST, RecipeCategory.MISC, Moditem.EXAMPLE_ITEM2, 0.25F, 200, "example_item");
        offerBlasting(exporter, EXAMPLE_ITEM2_LIST, RecipeCategory.MISC, Moditem.EXAMPLE_ITEM2, 0.25F, 100, "example_item");

        // 无序合成配方 (不关心材料摆放位置)
        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Modblock.EXAMPLE_BLOCK4, 1)
            .input(Moditem.EXAMPLE_ITEM)
            .input(Moditem.EXAMPLE_ITEM2)
            .criterion(hasItem(Moditem.EXAMPLE_ITEM2), conditionsFromItem(Moditem.EXAMPLE_ITEM2))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "example_block4_shapeless"));
            
        // 有序合成配方 (关心材料摆放位置)
        ShapedRecipeJsonBuilder.create(RecipeCategory.FOOD, Items.SUGAR, 1)
            .pattern("###")
            .pattern("???")
            .pattern("###")
            .input('#', Moditem.EXAMPLE_ITEM)
            .input('?',Moditem.EXAMPLE_ITEM2)
            .criterion(hasItem(Moditem.EXAMPLE_ITEM), conditionsFromItem(Moditem.EXAMPLE_ITEM))
            .criterion(hasItem(Moditem.EXAMPLE_ITEM2), conditionsFromItem(Moditem.EXAMPLE_ITEM2))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "example_item_to_sugar"));

        // 营火烹饪配方
        offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, 
        200, Moditem.EXAMPLE_ITEM2, Items.SUGAR, 5F);

        // SUPER_SUGAR 配方
        offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, 
        200, Items.SUGAR, Moditem.SUPER_SUGAR, 5F);
        offerFoodCookingRecipe(exporter, "smelting", RecipeSerializer.SMELTING, 
        200, Items.SUGAR, Moditem.SUPER_SUGAR, 5F);
        offerFoodCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, 
        200, Items.SUGAR, Moditem.SUPER_SUGAR, 5F);

        // CORN 配方
        offerFoodCookingRecipe(exporter, "smelting", RecipeSerializer.SMELTING, 
        200, Moditem.EXAMPLE_ITEM,Moditem.CORN, 5F);
        offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, 
        200, Moditem.EXAMPLE_ITEM,Moditem.CORN, 5F);
        offerFoodCookingRecipe(exporter, "smoking", RecipeSerializer.SMOKING, 
        200, Moditem.EXAMPLE_ITEM,Moditem.CORN, 5F);
    }
}
