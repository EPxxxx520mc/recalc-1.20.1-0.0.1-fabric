package com.rimeveil.recalc.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerFrameData {
    private static final String NBT_KEY = "recalc:has_frame";

    public static boolean hasFrameAttached(PlayerEntity player) {
        NbtCompound nbt = new NbtCompound();
        player.writeNbt(nbt);
        return nbt.contains(NBT_KEY) && nbt.getBoolean(NBT_KEY);
    }

    public static void attachFrame(PlayerEntity player) {
        NbtCompound nbt = new NbtCompound();
        player.writeNbt(nbt);
        nbt.putBoolean(NBT_KEY, true);
        player.readNbt(nbt);
    }

    public static void detachFrame(PlayerEntity player) {
        NbtCompound nbt = new NbtCompound();
        player.writeNbt(nbt);
        nbt.putBoolean(NBT_KEY, false);
        player.readNbt(nbt);
    }
}
