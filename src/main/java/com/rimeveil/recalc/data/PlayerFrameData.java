package com.rimeveil.recalc.data;

import com.rimeveil.recalc.util.LogUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerFrameData {
    private static final Map<UUID, Boolean> clientCache = new HashMap<>();

    public static boolean hasFrameAttached(PlayerEntity player) {
        if (player == null) {
            return false;
        }
        
        if (player.getWorld().isClient) {
            return clientCache.getOrDefault(player.getUuid(), false);
        }
        
        if (!(player instanceof ServerPlayerEntity)) {
            return false;
        }
        
        ServerWorld overworld = ((ServerPlayerEntity) player).getServer().getOverworld();
        FrameState state = FrameState.get(overworld);
        return state.hasFrame(player.getName().getString(), player.getUuid());
    }

    public static void attachFrame(PlayerEntity player) {
        if (player == null) {
            return;
        }
        
        if (!player.getWorld().isClient) {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                ServerWorld overworld = serverPlayer.getServer().getOverworld();
                FrameState state = FrameState.get(overworld);
                state.setFrame(player.getName().getString(), player.getUuid(), true);
                LogUtil.debug("Attached frame to {}", player.getName().getString());
            }
        }
        clientCache.put(player.getUuid(), true);
    }

    public static void detachFrame(PlayerEntity player) {
        if (player == null) {
            return;
        }
        
        if (!player.getWorld().isClient) {
            if (player instanceof ServerPlayerEntity serverPlayer) {
                ServerWorld overworld = serverPlayer.getServer().getOverworld();
                FrameState state = FrameState.get(overworld);
                state.setFrame(player.getName().getString(), player.getUuid(), false);
                LogUtil.debug("Detached frame from {}", player.getName().getString());
            }
        }
        clientCache.put(player.getUuid(), false);
    }
}
