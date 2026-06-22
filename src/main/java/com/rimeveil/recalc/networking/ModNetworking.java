package com.rimeveil.recalc.networking;

import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.util.LogUtil;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ModNetworking {
    public static final Identifier SYNC_FRAME_STATE = Recalc.id("sync_frame_state");
    public static final Identifier PLAY_REMOVE_ANIMATION = Recalc.id("play_remove_animation");

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            boolean hasFrame = PlayerFrameData.hasFrameAttached(player);
            LogUtil.debug("Syncing frame state for {}: {}", player.getName().getString(), hasFrame);
            sendFrameState(player, hasFrame);
        });
    }

    public static void syncToPlayer(ServerPlayerEntity player, boolean hasFrame) {
        if (player == null) {
            return;
        }

        LogUtil.debug("Sending frame state to {}: {}", player.getName().getString(), hasFrame);
        sendFrameState(player, hasFrame);
    }

    public static void sendRemoveAnimation(ServerPlayerEntity player) {
        if (player == null) {
            return;
        }

        LogUtil.debug("Sending remove animation to {}", player.getName().getString());
        ServerPlayNetworking.send(player, PLAY_REMOVE_ANIMATION, PacketByteBufs.empty());
    }

    private static void sendFrameState(ServerPlayerEntity player, boolean hasFrame) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(hasFrame);
        ServerPlayNetworking.send(player, SYNC_FRAME_STATE, buf);
    }
}
