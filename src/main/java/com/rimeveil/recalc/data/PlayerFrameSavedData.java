package com.rimeveil.recalc.data;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerFrameSavedData extends PersistentState {
    private static final String DATA_NAME = "recalc_player_frame_data";
    private final Map<UUID, Boolean> frameAttachedPlayers = new HashMap<>();

    public PlayerFrameSavedData() {
    }

    public static PlayerFrameSavedData createFromNbt(NbtCompound nbt) {
        PlayerFrameSavedData data = new PlayerFrameSavedData();
        NbtCompound playersNbt = nbt.getCompound("players");
        playersNbt.getKeys().forEach(key -> {
            UUID uuid = UUID.fromString(key);
            data.frameAttachedPlayers.put(uuid, playersNbt.getBoolean(key));
        });
        return data;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound playersNbt = new NbtCompound();
        frameAttachedPlayers.forEach((uuid, hasFrame) -> {
            playersNbt.putBoolean(uuid.toString(), hasFrame);
        });
        nbt.put("players", playersNbt);
        return nbt;
    }

    public static PlayerFrameSavedData get(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();
        PlayerFrameSavedData data = manager.getOrCreate(
                PlayerFrameSavedData::createFromNbt,
                PlayerFrameSavedData::new,
                DATA_NAME
        );
        return data;
    }

    public boolean hasFrameAttached(UUID uuid) {
        return frameAttachedPlayers.getOrDefault(uuid, false);
    }

    public void attachFrame(UUID uuid) {
        frameAttachedPlayers.put(uuid, true);
        markDirty();
    }

    public void detachFrame(UUID uuid) {
        frameAttachedPlayers.put(uuid, false);
        markDirty();
    }
}
