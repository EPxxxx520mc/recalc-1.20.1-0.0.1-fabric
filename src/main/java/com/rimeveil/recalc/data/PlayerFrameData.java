package com.rimeveil.recalc.data;

import net.minecraft.entity.player.PlayerEntity;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerFrameData {
    private static final Map<UUID, Boolean> frameAttachedPlayers = new HashMap<>();

    public static boolean hasFrameAttached(PlayerEntity player) {
        return frameAttachedPlayers.getOrDefault(player.getUuid(), false);
    }

    public static void attachFrame(PlayerEntity player) {
        frameAttachedPlayers.put(player.getUuid(), true);
    }

    public static void detachFrame(PlayerEntity player) {
        frameAttachedPlayers.put(player.getUuid(), false);
    }
}
