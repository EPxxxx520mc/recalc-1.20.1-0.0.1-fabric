package com.rimeveil.recalc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.networking.ModNetworking;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

/**
 * ================================================
 * 📜 Recalc 指令管理类
 * ================================================
 * 
 * 【功能】
 * - /recalc clear：移除玩家的框架
 * 
 * 【引用位置】
 * - Recalc.java → mod初始化时调用 register()
 * 
 * 【新增功能】
 * - 执行 clear 指令时播放红色移除动画
 * ================================================
 */
public class RecalcCommand {
    /**
     * 【注册指令】
     * 调用位置：Recalc.java → onInitialize()
     */
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("recalc")
            .then(literal("clear")
                .executes(context -> {
                    if (context.getSource().getPlayer() != null) {
                        ServerPlayerEntity player = context.getSource().getPlayer();
                        // 1. 移除框架
                        PlayerFrameData.detachFrame(player);
                        // 2. 发送反馈消息
                        context.getSource().sendFeedback(() -> Text.translatable("command.recalc.frame_removed"), false);
                        // 3. 同步状态给客户端
                        ModNetworking.syncToPlayer(player, false);
                        // ✅ 4. 发送移除动画信号！
                        ModNetworking.sendRemoveAnimation(player);
                        return 1;
                    }
                    context.getSource().sendError(Text.translatable("command.recalc.only_player"));
                    return 0;
                })
            )
        );
    }
}
