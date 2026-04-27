package com.rimeveil.recalc.data;

import com.rimeveil.recalc.Recalc;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FrameState extends PersistentState {
    private static final String DATA_NAME = "recalc_frame_data";
    private final Map<UUID, Boolean> players = new HashMap<>();

    public static FrameState create() {
        Recalc.LOGGER.info("Creating new FrameState");
        return new FrameState();
    }

    public static FrameState fromNbt(NbtCompound nbt) {
        FrameState state = new FrameState();
        NbtCompound playersNbt = nbt.getCompound("players");
        for (String key : playersNbt.getKeys()) {
            UUID uuid = UUID.fromString(key);
            state.players.put(uuid, playersNbt.getBoolean(key));
        }
        Recalc.LOGGER.info("Loaded FrameState from NBT: " + state.players.size() + " players");
        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound playersNbt = new NbtCompound();
        for (Map.Entry<UUID, Boolean> entry : players.entrySet()) {
            playersNbt.putBoolean(entry.getKey().toString(), entry.getValue());
        }
        nbt.put("players", playersNbt);
        Recalc.LOGGER.info("Saved FrameState: " + players.size() + " players");
        return nbt;
    }

    public static FrameState get(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();
        FrameState state = manager.getOrCreate(FrameState::fromNbt, FrameState::create, DATA_NAME);
        return state;
    }

    public boolean hasFrame(UUID uuid) {
        return players.getOrDefault(uuid, false);
    }

    public void setFrame(UUID uuid, boolean hasFrame) {
        players.put(uuid, hasFrame);
        markDirty();
        Recalc.LOGGER.info("Set frame for " + uuid + ": " + hasFrame);
    }
}
