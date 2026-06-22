package com.rimeveil.recalc.data;

import com.rimeveil.recalc.util.LogUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerFrameData {
    private static final Set<UUID> CLIENT_FRAME_CACHE = new HashSet<>();

    public static boolean hasFrameAttached(PlayerEntity player) {
        if (player == null) {
            return false;
        }

        if (player.getWorld().isClient) {
            return CLIENT_FRAME_CACHE.contains(player.getUuid());
        }

        if (player instanceof ServerPlayerEntity serverPlayer) {
            return getState(serverPlayer).hasFrame(player.getName().getString(), player.getUuid());
        }

        return false;
    }

    public static void attachFrame(PlayerEntity player) {
        setFrameAttached(player, true);
    }

    public static void detachFrame(PlayerEntity player) {
        setFrameAttached(player, false);
    }

    private static void setFrameAttached(PlayerEntity player, boolean attached) {
        if (player == null) {
            return;
        }

        if (!player.getWorld().isClient && player instanceof ServerPlayerEntity serverPlayer) {
            getState(serverPlayer).setFrame(player.getName().getString(), player.getUuid(), attached);
            LogUtil.debug("{} frame for {}", attached ? "Attached" : "Detached", player.getName().getString());
        }

        if (attached) {
            CLIENT_FRAME_CACHE.add(player.getUuid());
        } else {
            CLIENT_FRAME_CACHE.remove(player.getUuid());
        }
    }

    private static FrameState getState(ServerPlayerEntity player) {
        return FrameState.get(player.getServer().getOverworld());
    }
}
