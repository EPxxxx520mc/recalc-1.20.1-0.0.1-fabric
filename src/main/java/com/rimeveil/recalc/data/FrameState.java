package com.rimeveil.recalc.data;

import com.rimeveil.recalc.util.LogUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FrameState extends PersistentState {
    private static final String DATA_NAME = "recalc_frame_data";
    private static final String KEY_PLAYERS_BY_UUID = "playersByUuid";
    private static final String KEY_PLAYERS_BY_NAME = "playersByName";

    private final Set<String> playersByUuid = new HashSet<>();
    private final Set<String> playersByName = new HashSet<>();

    public static FrameState create() {
        LogUtil.debug("Creating new FrameState");
        return new FrameState();
    }

    public static FrameState fromNbt(NbtCompound nbt) {
        FrameState state = new FrameState();
        readPlayerSet(nbt, KEY_PLAYERS_BY_UUID, state.playersByUuid);
        readPlayerSet(nbt, KEY_PLAYERS_BY_NAME, state.playersByName);
        LogUtil.debug("Loaded FrameState from NBT: {} UUID, {} names", state.playersByUuid.size(), state.playersByName.size());
        return state;
    }

    public static FrameState get(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();
        return manager.getOrCreate(FrameState::fromNbt, FrameState::create, DATA_NAME);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.put(KEY_PLAYERS_BY_UUID, writePlayerSet(playersByUuid));
        nbt.put(KEY_PLAYERS_BY_NAME, writePlayerSet(playersByName));
        LogUtil.debug("Saved FrameState: {} UUID, {} names", playersByUuid.size(), playersByName.size());
        return nbt;
    }

    public boolean hasFrame(String playerName, UUID uuid) {
        return playersByUuid.contains(uuid.toString()) || playersByName.contains(playerName);
    }

    public void setFrame(String playerName, UUID uuid, boolean hasFrame) {
        updateSet(playersByUuid, uuid.toString(), hasFrame);
        updateSet(playersByName, playerName, hasFrame);
        markDirty();
        LogUtil.debug("Set frame for {} ({}) to {}", playerName, uuid, hasFrame);
    }

    private static void readPlayerSet(NbtCompound nbt, String key, Set<String> target) {
        if (!nbt.contains(key)) {
            return;
        }

        NbtCompound playersNbt = nbt.getCompound(key);
        for (String playerKey : playersNbt.getKeys()) {
            if (playersNbt.getBoolean(playerKey)) {
                target.add(playerKey);
            }
        }
    }

    private static NbtCompound writePlayerSet(Set<String> players) {
        NbtCompound nbt = new NbtCompound();
        for (String player : players) {
            nbt.putBoolean(player, true);
        }
        return nbt;
    }

    private static void updateSet(Set<String> set, String value, boolean shouldContain) {
        if (shouldContain) {
            set.add(value);
        } else {
            set.remove(value);
        }
    }
}
