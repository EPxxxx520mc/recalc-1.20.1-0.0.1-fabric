package com.rimeveil.recalc.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerFrameData {
    private static final Map<UUID, Boolean> clientCache = new HashMap<>();

    public static boolean hasFrameAttached(PlayerEntity player) {
        World world = player.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            // Always use overworld to save
            MinecraftServer server = serverWorld.getServer();
            ServerWorld overworld = server.getOverworld();
            PlayerFrameSavedData data = PlayerFrameSavedData.get(overworld);
            return data.hasFrameAttached(player.getUuid());
        }
        return clientCache.getOrDefault(player.getUuid(), false);
    }

    public static void attachFrame(PlayerEntity player) {
        World world = player.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            // Always use overworld to save
            MinecraftServer server = serverWorld.getServer();
            ServerWorld overworld = server.getOverworld();
            PlayerFrameSavedData data = PlayerFrameSavedData.get(overworld);
            data.attachFrame(player.getUuid());
        }
        clientCache.put(player.getUuid(), true);
    }

    public static void detachFrame(PlayerEntity player) {
        World world = player.getWorld();
        if (world instanceof ServerWorld serverWorld) {
            // Always use overworld to save
            MinecraftServer server = serverWorld.getServer();
            ServerWorld overworld = server.getOverworld();
            PlayerFrameSavedData data = PlayerFrameSavedData.get(overworld);
            data.detachFrame(player.getUuid());
        }
        clientCache.put(player.getUuid(), false);
    }
}
