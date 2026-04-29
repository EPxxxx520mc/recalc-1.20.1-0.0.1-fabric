package com.rimeveil.recalc.networking;

import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.client.AnimationManager;
import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.util.LogUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ModNetworking {
    public static final Identifier SYNC_FRAME_STATE = new Identifier(Recalc.MOD_ID, "sync_frame_state");
    public static final Identifier PLAY_REMOVE_ANIMATION = new Identifier(Recalc.MOD_ID, "play_remove_animation");

    public static void register() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            boolean hasFrame = PlayerFrameData.hasFrameAttached(player);
            LogUtil.debug("Syncing frame state for {}: {}", player.getName().getString(), hasFrame);
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(hasFrame);
            ServerPlayNetworking.send(player, SYNC_FRAME_STATE, buf);
        });

        ClientPlayNetworking.registerGlobalReceiver(SYNC_FRAME_STATE, (client, handler, buf, responseSender) -> {
            boolean hasFrame = buf.readBoolean();
            LogUtil.debug("Client received frame state: {}", hasFrame);
            client.execute(() -> {
                if (client.player != null) {
                    if (hasFrame) {
                        PlayerFrameData.attachFrame(client.player);
                        AnimationManager.start(AnimationManager.ID_ATTACH);
                    } else {
                        PlayerFrameData.detachFrame(client.player);
                    }
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(PLAY_REMOVE_ANIMATION, (client, handler, buf, responseSender) -> {
            LogUtil.debug("Client received remove animation signal");
            client.execute(() -> {
                AnimationManager.start(AnimationManager.ID_REMOVE);
            });
        });
    }

    public static void syncToPlayer(ServerPlayerEntity player, boolean hasFrame) {
        if (player == null) {
            return;
        }
        LogUtil.debug("Sending frame state to {}: {}", player.getName().getString(), hasFrame);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(hasFrame);
        ServerPlayNetworking.send(player, SYNC_FRAME_STATE, buf);
    }

    public static void sendRemoveAnimation(ServerPlayerEntity player) {
        if (player == null) {
            return;
        }
        LogUtil.debug("Sending remove animation to {}", player.getName().getString());
        PacketByteBuf buf = PacketByteBufs.create();
        ServerPlayNetworking.send(player, PLAY_REMOVE_ANIMATION, buf);
    }
}
