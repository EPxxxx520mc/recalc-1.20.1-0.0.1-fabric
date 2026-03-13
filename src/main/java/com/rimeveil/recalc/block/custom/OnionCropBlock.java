package com.rimeveil.recalc.block.custom;

import com.rimeveil.recalc.Item.Moditem;

import net.minecraft.world.BlockView;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.item.ItemConvertible;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.Blocks;

public class OnionCropBlock extends CropBlock {
    public static final int MAX_AGE = 5;
    public static final IntProperty AGE = Properties.AGE_5;

    public OnionCropBlock(Settings settings) {
        // Constructor logic for the OnionCropBlock
        super(settings);
    }

    public int getMaxAge() {
        return MAX_AGE;
    }

    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getAge(BlockState state) {
        return state.get(this.getAgeProperty());
    }
    
    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Override
    protected ItemConvertible getSeedsItem() {
        return Moditem.ONION_SEEDS; // Replace with your actual seeds item
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        // Allow planting on dirt, grass, and farmland
        return floor.isOf(Blocks.DIRT) || floor.isOf(Blocks.FARMLAND);
    }
}