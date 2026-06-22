package com.rimeveil.recalc.block;

import net.minecraft.block.Block;
import net.minecraft.data.family.BlockFamily;
import net.minecraft.registry.Registries;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;

public class ModBlockFamilis {
    private static final Map<Block, BlockFamily> BASE_BLOCK_TO_FAMILY = new LinkedHashMap<>();

    public static final BlockFamily SUPER_SUGAR = register(new BlockFamily.Builder(Modblock.SUPER_SUGAR_BLOCK)
        .stairs(Modblock.SUPER_SUGAR_STAIRS)
        .slab(Modblock.SUPER_SUGAR_SLAB)
        .button(Modblock.SUPER_SUGAR_BUTTON)
        .pressurePlate(Modblock.SUPER_SUGAR_PRESSURE_PLATE)
        .fence(Modblock.SUPER_SUGAR_FENCE)
        .fenceGate(Modblock.SUPER_SUGAR_FENCE_GATE)
        .wall(Modblock.SUPER_SUGAR_WALL)
        .door(Modblock.SUPER_SUGAR_DOOR)
        .trapdoor(Modblock.SUPER_SUGAR_TRAPDOOR)
        .build());

    public static Stream<BlockFamily> getFamilies() {
        return BASE_BLOCK_TO_FAMILY.values().stream();
    }

    private static BlockFamily register(BlockFamily family) {
        Block previous = family.getBaseBlock();
        BlockFamily existing = BASE_BLOCK_TO_FAMILY.put(previous, family);
        if (existing != null) {
            throw new IllegalStateException("Duplicate family definition for " + Registries.BLOCK.getId(previous));
        }
        return family;
    }
}
