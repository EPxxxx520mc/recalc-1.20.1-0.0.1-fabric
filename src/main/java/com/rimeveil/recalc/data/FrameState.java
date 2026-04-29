package com.rimeveil.recalc.data;

import com.rimeveil.recalc.util.LogUtil;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FrameState extends PersistentState {
    private static final String DATA_NAME = "recalc_frame_data";
    private static final String KEY_PLAYERS_BY_UUID = "playersByUuid";
    private static final String KEY_PLAYERS_BY_NAME = "playersByName";
    
    private final Map<String, Boolean> playersByUuid = new HashMap<>();
    private final Map<String, Boolean> playersByName = new HashMap<>();

    public static FrameState create() {
        LogUtil.debug("Creating new FrameState");
        return new FrameState();
    }

    public static FrameState fromNbt(NbtCompound nbt) {
        FrameState state = new FrameState();
        
        if (nbt.contains(KEY_PLAYERS_BY_UUID)) {
            NbtCompound playersByUuidNbt = nbt.getCompound(KEY_PLAYERS_BY_UUID);
            for (String key : playersByUuidNbt.getKeys()) {
                state.playersByUuid.put(key, playersByUuidNbt.getBoolean(key));
            }
        }
        
        if (nbt.contains(KEY_PLAYERS_BY_NAME)) {
            NbtCompound playersByNameNbt = nbt.getCompound(KEY_PLAYERS_BY_NAME);
            for (String key : playersByNameNbt.getKeys()) {
                state.playersByName.put(key, playersByNameNbt.getBoolean(key));
            }
        }
        
        LogUtil.debug("Loaded FrameState from NBT: {} UUID, {} names", 
                     state.playersByUuid.size(), state.playersByName.size());
        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        NbtCompound playersByUuidNbt = new NbtCompound();
        for (Map.Entry<String, Boolean> entry : this.playersByUuid.entrySet()) {
            playersByUuidNbt.putBoolean(entry.getKey(), entry.getValue());
        }
        nbt.put(KEY_PLAYERS_BY_UUID, playersByUuidNbt);
        
        NbtCompound playersByNameNbt = new NbtCompound();
        for (Map.Entry<String, Boolean> entry : this.playersByName.entrySet()) {
            playersByNameNbt.putBoolean(entry.getKey(), entry.getValue());
        }
        nbt.put(KEY_PLAYERS_BY_NAME, playersByNameNbt);
        
        LogUtil.debug("Saved FrameState: {} UUID, {} names", 
                     this.playersByUuid.size(), this.playersByName.size());
        return nbt;
    }

    public static FrameState get(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();
        FrameState state = manager.getOrCreate(FrameState::fromNbt, FrameState::create, DATA_NAME);
        return state;
    }

    public boolean hasFrame(String playerName, UUID uuid) {
        if (playersByName.containsKey(playerName)) {
            return playersByName.get(playerName);
        }
        return playersByUuid.getOrDefault(uuid.toString(), false);
    }

    public void setFrame(String playerName, UUID uuid, boolean hasFrame) {
        playersByUuid.put(uuid.toString(), hasFrame);
        playersByName.put(playerName, hasFrame);
        markDirty();
        LogUtil.debug("Set frame for {} ({}) to {}", playerName, uuid, hasFrame);
    }
}
