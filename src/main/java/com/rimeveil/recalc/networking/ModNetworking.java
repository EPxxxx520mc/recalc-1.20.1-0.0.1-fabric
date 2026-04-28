package com.rimeveil.recalc.networking;

import com.rimeveil.recalc.Recalc;
import com.rimeveil.recalc.client.AnimationState;
import com.rimeveil.recalc.client.RemoveAnimationState;
import com.rimeveil.recalc.data.PlayerFrameData;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

/**
 * ================================================
 * 🌐 网络同步管理类
 * ================================================
 * 
 * 【功能】
 * - 同步玩家框架状态
 * - 播放附着框架动画
 * - 播放移除框架动画
 * 
 * 【引用位置】
 * - Recalc.java → mod初始化时调用 register()
 * - FictionalFrameItem.java → 使用框架时调用 syncToPlayer()
 * - RecalcCommand.java → 清除框架时调用 syncRemoveAnimation()
 * 
 * 【新增功能】
 * - PLAY_REMOVE_ANIMATION 包：播放红色移除动画
 * ================================================
 */
public class ModNetworking {
    // ==================== 网络包标识符 ====================
    public static final Identifier SYNC_FRAME_STATE = new Identifier(Recalc.MOD_ID, "sync_frame_state");
    public static final Identifier PLAY_REMOVE_ANIMATION = new Identifier(Recalc.MOD_ID, "play_remove_animation");

    /**
     * 【注册网络处理器】
     * 调用位置：Recalc.java → onInitialize()
     */
    public static void register() {
        // ========== 服务端：玩家加入时同步框架状态 ==========
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            boolean hasFrame = PlayerFrameData.hasFrameAttached(player);
            Recalc.LOGGER.info("Syncing frame state for " + player.getName().getString() + ": " + hasFrame);
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeBoolean(hasFrame);
            ServerPlayNetworking.send(player, SYNC_FRAME_STATE, buf);
        });

        // ========== 客户端：接收框架状态同步 ==========
        ClientPlayNetworking.registerGlobalReceiver(SYNC_FRAME_STATE, (client, handler, buf, responseSender) -> {
            boolean hasFrame = buf.readBoolean();
            Recalc.LOGGER.info("Client received frame state: " + hasFrame);
            client.execute(() -> {
                if (client.player != null) {
                    if (hasFrame) {
                        PlayerFrameData.attachFrame(client.player);
                        // ✅ 每次加入游戏都播放附着动画！
                        AnimationState.start();
                    } else {
                        PlayerFrameData.detachFrame(client.player);
                    }
                }
            });
        });

        // ========== 客户端：接收移除动画信号 ==========
        ClientPlayNetworking.registerGlobalReceiver(PLAY_REMOVE_ANIMATION, (client, handler, buf, responseSender) -> {
            Recalc.LOGGER.info("Client received remove animation signal!");
            client.execute(() -> {
                // ✅ 播放红色移除动画！
                RemoveAnimationState.start();
            });
        });
    }

    /**
     * 【同步框架状态给玩家】
     * 调用位置：FictionalFrameItem.java → 右键使用框架时
     */
    public static void syncToPlayer(ServerPlayerEntity player, boolean hasFrame) {
        Recalc.LOGGER.info("Sending frame state to " + player.getName().getString() + ": " + hasFrame);
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeBoolean(hasFrame);
        ServerPlayNetworking.send(player, SYNC_FRAME_STATE, buf);
    }

    /**
     * 【发送移除动画信号给玩家】
     * 调用位置：RecalcCommand.java → 执行 /recalc clear 时
     */
    public static void sendRemoveAnimation(ServerPlayerEntity player) {
        Recalc.LOGGER.info("Sending remove animation to " + player.getName().getString());
        PacketByteBuf buf = PacketByteBufs.create();
        ServerPlayNetworking.send(player, PLAY_REMOVE_ANIMATION, buf);
    }
}
