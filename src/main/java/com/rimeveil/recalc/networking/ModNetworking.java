package com.rimeveil.recalc.networking;

import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.data.PlayerFrameData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ModNetworking {
    public static final Identifier SYNC_FRAME_STATE = new Identifier(Recalc.MOD_ID, "sync_frame_state");

    public static void register() {
        // Server side: when player joins
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            boolean hasFrame = PlayerFrameData.hasFrameAttached(player);
            Recalc.LOGGER.info("Syncing frame state for " + player.getName().getString() + ": " + hasFrame);
            
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(hasFrame);
            ServerPlayNetworking.send(player, SYNC_FRAME_STATE, buf);
        });

        // Client side: receive sync
        ClientPlayNetworking.registerGlobalReceiver(SYNC_FRAME_STATE, (client, handler, buf, responseSender) -> {
            boolean hasFrame = buf.readBoolean();
            Recalc.LOGGER.info("Client received frame state: " + hasFrame);
            client.execute(() -> {
                if (client.player != null) {
                    if (hasFrame) {
                        PlayerFrameData.attachFrame(client.player);
                    } else {
                        PlayerFrameData.detachFrame(client.player);
                    }
                }
            });
        });
    }

    public static void syncToPlayer(ServerPlayerEntity player, boolean hasFrame) {
        Recalc.LOGGER.info("Sending frame state to " + player.getName().getString() + ": " + hasFrame);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(hasFrame);
        ServerPlayNetworking.send(player, SYNC_FRAME_STATE, buf);
    }
}
