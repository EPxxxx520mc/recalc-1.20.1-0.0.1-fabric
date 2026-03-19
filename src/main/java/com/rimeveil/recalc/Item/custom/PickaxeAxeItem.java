package com.rimeveil.recalc.Item.custom;

import net.minecraft.item.ToolMaterial;
import net.minecraft.item.AxeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.block.BlockState;
import net.minecraft.world.World;
import net.minecraft.text.Text;
import net.minecraft.text.HoverEvent.Action;
import net.minecraft.util.ActionResult;
import com.rimeveil.recalc.sound.ModSoundEvents;
import net.minecraft.sound.BlockSoundGroup;


import org.jetbrains.annotations.Nullable;
import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.gui.screen.Screen;



import com.rimeveil.recalc.tag.ModBlockTags;

public class PickaxeAxeItem extends AxeItem {
    public PickaxeAxeItem(ToolMaterial material, float attackDamage, float miningSpeed, Settings settings) {
        super(material, attackDamage, miningSpeed, settings);
    }

    @Override
    public float getMiningSpeedMultiplier(ItemStack stack, BlockState state) {
        return state.isIn(ModBlockTags.PICKAXE_AXE) ? this.miningSpeed : 1.0F;
    }
    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isIn(ModBlockTags.PICKAXE_AXE);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context);
        //tooltip.add(Text.translatable("item.recalc.pickaxe_axe.tooltip"));
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.recalc.pickaxe_axe.tooltip.shift"));
        }else{
            tooltip.add(Text.translatable("item.recalc.pickaxe_axe.tooltip"));
        }
    }


    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (!context.getWorld().isClient()) {
            BlockState state = context.getWorld().getBlockState(context.getBlockPos());
            if (state.isIn(ModBlockTags.PICKAXE_AXE)) {
                context.getWorld().playSound(null, context.getBlockPos(), ModSoundEvents.TEXT, BlockSoundGroup.BLOCKS, 1.0F, 1.0F);
            }
        }
    } 
}
