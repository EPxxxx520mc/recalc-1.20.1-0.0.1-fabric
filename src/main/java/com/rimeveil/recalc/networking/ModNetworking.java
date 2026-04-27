package com.rimeveil.recalc.networking;

import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.data.PlayerFrameSavedData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;

public class ModNetworking {
    public static final Identifier SYNC_FRAME_STATE = new Identifier(Recalc.MOD_ID, "sync_frame_state");

    public static void register() {
        // Server-side
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            ServerWorld overworld = server.getOverworld();
            PlayerFrameSavedData data = PlayerFrameSavedData.get(overworld);
            boolean hasFrame = data.hasFrameAttached(player.getUuid());
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(hasFrame);
            ServerPlayNetworking.send(player, SYNC_FRAME_STATE, buf);
        });

        // Client-side
        ClientPlayNetworking.registerGlobalReceiver(SYNC_FRAME_STATE, (client, handler, buf, responseSender) -> {
            boolean hasFrame = buf.readBoolean();
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
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(hasFrame);
        ServerPlayNetworking.send(player, SYNC_FRAME_STATE, buf);
    }
}
