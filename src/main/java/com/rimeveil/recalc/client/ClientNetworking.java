package com.rimeveil.recalc.client;

import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.networking.ModNetworking;
import com.rimeveil.recalc.util.LogUtil;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class ClientNetworking {
    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ModNetworking.SYNC_FRAME_STATE, (client, handler, buf, responseSender) -> {
            boolean hasFrame = buf.readBoolean();
            LogUtil.debug("Client received frame state: {}", hasFrame);

            client.execute(() -> {
                if (client.player == null) {
                    return;
                }

                if (hasFrame) {
                    PlayerFrameData.attachFrame(client.player);
                } else {
                    PlayerFrameData.detachFrame(client.player);
                }
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(ModNetworking.PLAY_REMOVE_ANIMATION, (client, handler, buf, responseSender) -> {
            LogUtil.debug("Client received remove animation signal");
            client.execute(() -> AnimationManager.start(AnimationManager.ID_REMOVE));
        });
    }
}
