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
    private final Map<String, Boolean> playersByUuid = new HashMap<>(); // 正式版用 UUID
    private final Map<String, Boolean> playersByName = new HashMap<>(); // 开发版用名字（防止UUID随机）

    public static FrameState create() {
        Recalc.LOGGER.info("Creating new FrameState");
        return new FrameState();
    }

    public static FrameState fromNbt(NbtCompound nbt) {
        FrameState state = new FrameState();
        
        // 加载 UUID 格式（带检查）
        if (nbt.contains("playersByUuid")) {
            NbtCompound playersByUuid = nbt.getCompound("playersByUuid");
            for (String key : playersByUuid.getKeys()) {
                state.playersByUuid.put(key, playersByUuid.getBoolean(key));
            }
        }
        
        // 加载名字格式（带检查）
        if (nbt.contains("playersByName")) {
            NbtCompound playersByName = nbt.getCompound("playersByName");
            for (String key : playersByName.getKeys()) {
                state.playersByName.put(key, playersByName.getBoolean(key));
            }
        }
        
        Recalc.LOGGER.info("Loaded FrameState from NBT: " + state.playersByUuid.size() + " UUID, " + state.playersByName.size() + " names");
        return state;
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        // 保存 UUID 格式
        NbtCompound playersByUuid = new NbtCompound();
        for (Map.Entry<String, Boolean> entry : this.playersByUuid.entrySet()) {
            playersByUuid.putBoolean(entry.getKey(), entry.getValue());
        }
        nbt.put("playersByUuid", playersByUuid);
        
        // 保存名字格式
        NbtCompound playersByName = new NbtCompound();
        for (Map.Entry<String, Boolean> entry : this.playersByName.entrySet()) {
            playersByName.putBoolean(entry.getKey(), entry.getValue());
        }
        nbt.put("playersByName", playersByName);
        
        Recalc.LOGGER.info("Saved FrameState: " + this.playersByUuid.size() + " UUID, " + this.playersByName.size() + " names");
        return nbt;
    }

    public static FrameState get(ServerWorld world) {
        PersistentStateManager manager = world.getPersistentStateManager();
        FrameState state = manager.getOrCreate(FrameState::fromNbt, FrameState::create, DATA_NAME);
        return state;
    }

    public boolean hasFrame(String playerName, UUID uuid) {
        // 先检查名字，再检查 UUID（开发环境主要靠名字）
        if (playersByName.containsKey(playerName)) {
            return playersByName.get(playerName);
        }
        return playersByUuid.getOrDefault(uuid.toString(), false);
    }

    public void setFrame(String playerName, UUID uuid, boolean hasFrame) {
        // 两种格式都保存，确保万无一失！
        playersByUuid.put(uuid.toString(), hasFrame);
        playersByName.put(playerName, hasFrame);
        markDirty();
        Recalc.LOGGER.info("Set frame for " + playerName + "(" + uuid + "): " + hasFrame);
    }
}
