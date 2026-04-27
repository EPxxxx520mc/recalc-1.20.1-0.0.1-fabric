package com.rimeveil.recalc.data;

import com.rimeveil.recalc.Recalc;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerFrameData {
    private static final Map<UUID, Boolean> clientCache = new HashMap<>();
    private static FrameState state;

    private static class FrameState extends PersistentState {
        private final Map<UUID, Boolean> players = new HashMap<>();

        public FrameState() {
        }

        public static FrameState fromNbt(NbtCompound nbt) {
            FrameState state = new FrameState();
            NbtCompound playersNbt = nbt.getCompound("players");
            for (String key : playersNbt.getKeys()) {
                UUID uuid = UUID.fromString(key);
                state.players.put(uuid, playersNbt.getBoolean(key));
            }
            Recalc.LOGGER.info("Loaded frame data: " + state.players.size() + " players");
            return state;
        }

        @Override
        public NbtCompound writeNbt(NbtCompound nbt) {
            NbtCompound playersNbt = new NbtCompound();
            for (Map.Entry<UUID, Boolean> entry : players.entrySet()) {
                playersNbt.putBoolean(entry.getKey().toString(), entry.getValue());
            }
            nbt.put("players", playersNbt);
            Recalc.LOGGER.info("Saved frame data: " + players.size() + " players");
            return nbt;
        }
    }

    private static FrameState getState(World world) {
        if (world.isClient) {
            return null;
        }
        ServerWorld serverWorld = (ServerWorld) world;
        // Get overworld
        ServerWorld overworld = serverWorld.getServer().getOverworld();
        PersistentStateManager manager = overworld.getPersistentStateManager();
        return manager.getOrCreate(FrameState::fromNbt, FrameState::new, "recalc_frame_data");
    }

    public static boolean hasFrameAttached(PlayerEntity player) {
        World world = player.getWorld();
        if (world.isClient) {
            return clientCache.getOrDefault(player.getUuid(), false);
        }
        FrameState state = getState(world);
        return state.players.getOrDefault(player.getUuid(), false);
    }

    public static void attachFrame(PlayerEntity player) {
        World world = player.getWorld();
        if (!world.isClient) {
            FrameState state = getState(world);
            state.players.put(player.getUuid(), true);
            state.markDirty();
            Recalc.LOGGER.info("Attached frame to " + player.getName().getString());
        }
        clientCache.put(player.getUuid(), true);
    }

    public static void detachFrame(PlayerEntity player) {
        World world = player.getWorld();
        if (!world.isClient) {
            FrameState state = getState(world);
            state.players.put(player.getUuid(), false);
            state.markDirty();
            Recalc.LOGGER.info("Detached frame from " + player.getName().getString());
        }
        clientCache.put(player.getUuid(), false);
    }
}
