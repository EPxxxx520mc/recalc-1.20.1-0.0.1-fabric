package com.rimeveil.recalc.block;

import com.google.common.collect.Maps;
import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;

import java.util.Map;
import java.util.stream.Stream;

public class ModBlockFamilis {
    private static final Map<Block, BlockFamily> BASE_BLOCK_TO_FAMILIES = Maps.<Block, BlockFamily>newHashMap();

    public static final BlockFamily SUPER_SUGAR = register(Modblock.SUPER_SUGAR_BLOCK)
        .stairs(Modblock.SUPER_SUGAR_STAIRS)
        .slab(Modblock.SUPER_SUGAR_SLAB)
        .button(Modblock.SUPER_SUGAR_BUTTON)
        .pressurePlate(Modblock.SUPER_SUGAR_PRESSURE_PLATE)
        .fence(Modblock.SUPER_SUGAR_FENCE)
        .fenceGate(Modblock.SUPER_SUGAR_FENCE_GATE)
        .wall(Modblock.SUPER_SUGAR_WALL)
        .door(Modblock.SUPER_SUGAR_DOOR)
        .trapdoor(Modblock.SUPER_SUGAR_TRAPDOOR)
        .build();



    public static BlockFamily.Builder register(Block baseBlock) {
        BlockFamily.Builder builder = new BlockFamily.Builder(baseBlock);
        BlockFamily blockFamily = (BlockFamily)BASE_BLOCK_TO_FAMILIES.put(baseBlock, builder.build());
        if (blockFamily != null){
            throw new IllegalStateException("Duplicate family definition for " + Registries.BLOCK.getId(baseBlock));
        }else{
            return builder;
        }
    }
    public static Stream<BlockFamily> getFamilies() {
        return BASE_BLOCK_TO_FAMILIES.values().stream();
    }
}
