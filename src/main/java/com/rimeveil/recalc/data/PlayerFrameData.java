package com.rimeveil.recalc.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class PlayerFrameData {
    private static final String FRAME_KEY = "recalc_frame_attached";
    private static final String MOD_ID = "recalc";

    public static boolean hasFrameAttached(PlayerEntity player) {
        NbtCompound nbt = new NbtCompound();
        player.writeNbt(nbt);
        if (nbt.contains(MOD_ID)) {
            NbtCompound modData = nbt.getCompound(MOD_ID);
            return modData.getBoolean(FRAME_KEY);
        }
        return false;
    }

    public static void attachFrame(PlayerEntity player) {
        NbtCompound nbt = new NbtCompound();
        player.writeNbt(nbt);
        NbtCompound modData = nbt.contains(MOD_ID) ? nbt.getCompound(MOD_ID) : new NbtCompound();
        modData.putBoolean(FRAME_KEY, true);
        nbt.put(MOD_ID, modData);
        player.readNbt(nbt);
    }

    public static void detachFrame(PlayerEntity player) {
        NbtCompound nbt = new NbtCompound();
        player.writeNbt(nbt);
        if (nbt.contains(MOD_ID)) {
            NbtCompound modData = nbt.getCompound(MOD_ID);
            modData.putBoolean(FRAME_KEY, false);
            nbt.put(MOD_ID, modData);
            player.readNbt(nbt);
        }
    }
}
