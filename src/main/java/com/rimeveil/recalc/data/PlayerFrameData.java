package com.rimeveil.recalc.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerFrameData {
    private static final Map<UUID, Boolean> clientCache = new HashMap<>();

    public static boolean hasFrameAttached(PlayerEntity player) {
        if (player.getWorld().isClient) {
            return clientCache.getOrDefault(player.getUuid(), false);
        }
        ServerWorld overworld = ((ServerPlayerEntity) player).getServer().getOverworld();
        FrameState state = FrameState.get(overworld);
        return state.hasFrame(player.getUuid());
    }

    public static void attachFrame(PlayerEntity player) {
        if (!player.getWorld().isClient) {
            ServerWorld overworld = ((ServerPlayerEntity) player).getServer().getOverworld();
            FrameState state = FrameState.get(overworld);
            state.setFrame(player.getUuid(), true);
        }
        clientCache.put(player.getUuid(), true);
    }

    public static void detachFrame(PlayerEntity player) {
        if (!player.getWorld().isClient) {
            ServerWorld overworld = ((ServerPlayerEntity) player).getServer().getOverworld();
            FrameState state = FrameState.get(overworld);
            state.setFrame(player.getUuid(), false);
        }
        clientCache.put(player.getUuid(), false);
    }
}
