package com.rimeveil.recalc.datagen;

import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.block.Modblock;
import com.rimeveil.recalc.tag.ModItemTags;
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

        ShapelessRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Modblock.SUPER_SUGAR_BUTTON, 1)
            .input(Modblock.SUPER_SUGAR_BLOCK)
            .criterion(hasItem(Modblock.SUPER_SUGAR_BLOCK), conditionsFromItem(Modblock.SUPER_SUGAR_BLOCK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_button_shapeless"));

        ShapelessRecipeJsonBuilder.create(RecipeCategory.MISC, Items.DIAMOND_SWORD, 1)
            .input(ModItemTags.EXAMPLE_ITEM)
            .input(Moditem.EXAMPLE_ITEM2)
            .criterion(hasItem(Moditem.EXAMPLE_ITEM), conditionsFromItem(Moditem.EXAMPLE_ITEM))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "diamond_sword_from_example_items"));
        
            
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

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Modblock.SUPER_SUGAR_STAIRS, 4)
            .pattern("#  ")
            .pattern("## ")
            .pattern("###")
            .input('#', Modblock.SUPER_SUGAR_BLOCK)
            .criterion(hasItem(Modblock.SUPER_SUGAR_BLOCK), conditionsFromItem(Modblock.SUPER_SUGAR_BLOCK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_stairs_from_block"));

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Modblock.SUPER_SUGAR_SLAB, 6)
            .pattern("###")
            .input('#', Modblock.SUPER_SUGAR_BLOCK)
            .criterion(hasItem(Modblock.SUPER_SUGAR_BLOCK), conditionsFromItem(Modblock.SUPER_SUGAR_BLOCK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_slab_from_block"));
            
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Modblock.SUPER_SUGAR_PRESSURE_PLATE, 1)
            .pattern("##")
            .input('#', Modblock.SUPER_SUGAR_BLOCK)
            .criterion(hasItem(Modblock.SUPER_SUGAR_BLOCK), conditionsFromItem(Modblock.SUPER_SUGAR_BLOCK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_pressure_plate_from_block"));

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Modblock.SUPER_SUGAR_FENCE, 2)
            .pattern("#?#")
            .pattern("#?#")
            .input('#', Modblock.SUPER_SUGAR_BLOCK)
            .input('?', Items.STICK)
            .criterion(hasItem(Modblock.SUPER_SUGAR_BLOCK), conditionsFromItem(Modblock.SUPER_SUGAR_BLOCK))
            .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_fence_from_block"));
            
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Modblock.SUPER_SUGAR_FENCE_GATE, 1)
            .pattern("?#?")
            .pattern("?#?")
            .input('#', Modblock.SUPER_SUGAR_BLOCK)
            .input('?', Items.STICK)
            .criterion(hasItem(Modblock.SUPER_SUGAR_BLOCK), conditionsFromItem(Modblock.SUPER_SUGAR_BLOCK))
            .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_fence_gate_from_block"));

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Modblock.SUPER_SUGAR_WALL, 2)
            .pattern("###")
            .pattern("###")
            .input('#', Modblock.SUPER_SUGAR_BLOCK)
            .criterion(hasItem(Modblock.SUPER_SUGAR_BLOCK), conditionsFromItem(Modblock.SUPER_SUGAR_BLOCK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_wall_from_block"));

            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Modblock.SUPER_SUGAR_DOOR, 2)
            .pattern("##")
            .pattern("##")
            .pattern("##")
            .input('#', Modblock.SUPER_SUGAR_BLOCK)
            .criterion(hasItem(Modblock.SUPER_SUGAR_BLOCK), conditionsFromItem(Modblock.SUPER_SUGAR_BLOCK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_door_from_block"));
            
            ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, Modblock.SUPER_SUGAR_TRAPDOOR, 2)
            .pattern("##")
            .pattern("##")
            .input('#', Modblock.SUPER_SUGAR_BLOCK)
            .criterion(hasItem(Modblock.SUPER_SUGAR_BLOCK), conditionsFromItem(Modblock.SUPER_SUGAR_BLOCK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_trapdoor_from_block"));
            
            
        // 营火烹饪配方
        offerFoodCookingRecipe(exporter, "campfire_cooking", RecipeSerializer.CAMPFIRE_COOKING, 
        200, Moditem.EXAMPLE_ITEM2, Items.SUGAR, 5F);

        // SUPER_SUGAR 配方
        offerReversibleCompactingRecipes(exporter, RecipeCategory.MISC, Moditem.SUPER_SUGAR, RecipeCategory.BUILDING_BLOCKS, Modblock.SUPER_SUGAR_BLOCK);

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

        // SUPER_COAL 配方 (3个 EXAMPLE_ITEM2 摆成一行)
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, Moditem.SUPER_COAL, 1)
            .pattern("###")
            .input('#', Moditem.EXAMPLE_ITEM2)
            .criterion(hasItem(Moditem.EXAMPLE_ITEM2), conditionsFromItem(Moditem.EXAMPLE_ITEM2))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_coal"));


        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Moditem.SUPER_SUGAR_SWORD, 1)
            .pattern(" % ")
            .pattern("#%#")
            .pattern(" ? ")
            .input('#', Moditem.SUPER_SUGAR)
            .input('?', Items.BAMBOO)
            .input('%', Items.AMETHYST_SHARD)
            .criterion(hasItem(Moditem.SUPER_SUGAR), conditionsFromItem(Moditem.SUPER_SUGAR))
            .criterion(hasItem(Items.BAMBOO), conditionsFromItem(Items.BAMBOO))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_sword"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Moditem.SUPER_SUGAR_AXE, 1)
            .pattern(" ##")
            .pattern(" ?#")
            .pattern(" ? ")
            .input('#', Moditem.SUPER_SUGAR)
            .input('?', Items.STICK)
            .criterion(hasItem(Moditem.SUPER_SUGAR), conditionsFromItem(Moditem.SUPER_SUGAR))
            .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_axe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Moditem.SUPER_SUGAR_SHOVEL, 1)
            .pattern(" # ")
            .pattern(" ? ")
            .pattern(" ? ")
            .input('#', Moditem.SUPER_SUGAR)
            .input('?', Items.STICK)
            .criterion(hasItem(Moditem.SUPER_SUGAR), conditionsFromItem(Moditem.SUPER_SUGAR))
            .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_shovel"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Moditem.SUPER_SUGAR_PICKAXE, 1)
            .pattern("###")
            .pattern(" ? ")
            .pattern(" ? ")
            .input('#', Moditem.SUPER_SUGAR)
            .input('?', Items.STICK)
            .criterion(hasItem(Moditem.SUPER_SUGAR), conditionsFromItem(Moditem.SUPER_SUGAR))
            .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_pickaxe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Moditem.SUPER_SUGAR_HOE, 1)
            .pattern("## ")
            .pattern(" ? ")
            .pattern(" ? ")
            .input('#', Moditem.SUPER_SUGAR)
            .input('?', Items.STICK)
            .criterion(hasItem(Moditem.SUPER_SUGAR), conditionsFromItem(Moditem.SUPER_SUGAR))
            .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_hoe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Moditem.SUPER_SUGAR_PICKAXE_AXE, 1)
            .pattern("###")
            .pattern(" ?#")
            .pattern(" ? ")
            .input('#', Moditem.SUPER_SUGAR)
            .input('?', Items.STICK)
            .criterion(hasItem(Moditem.SUPER_SUGAR), conditionsFromItem(Moditem.SUPER_SUGAR))
            .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_pickaxe_axe"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Moditem.SUPER_SUGAR_HELMET, 1)
            .pattern("###")
            .pattern("# #")
            .pattern("   ")
            .input('#', Moditem.SUPER_SUGAR)
            .criterion(hasItem(Moditem.SUPER_SUGAR), conditionsFromItem(Moditem.SUPER_SUGAR))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_helmet"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Moditem.SUPER_SUGAR_CHESTPLATE, 1)
            .pattern("# #")
            .pattern("###")
            .pattern("###")
            .input('#', Moditem.SUPER_SUGAR)
            .criterion(hasItem(Moditem.SUPER_SUGAR), conditionsFromItem(Moditem.SUPER_SUGAR))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_chestplate"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Moditem.SUPER_SUGAR_LEGGINGS, 1)
            .pattern("###")
            .pattern("# #")
            .pattern("# #")
            .input('#', Moditem.SUPER_SUGAR)
            .criterion(hasItem(Moditem.SUPER_SUGAR), conditionsFromItem(Moditem.SUPER_SUGAR))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_leggings"));

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, Moditem.SUPER_SUGAR_BOOTS, 1)
            .pattern("   ")
            .pattern("# #")
            .pattern("# #")
            .input('#', Moditem.SUPER_SUGAR)
            .criterion(hasItem(Moditem.SUPER_SUGAR), conditionsFromItem(Moditem.SUPER_SUGAR))
            .offerTo(exporter, new Identifier(Recalc.MOD_ID, "super_sugar_boots"));
    }
}
