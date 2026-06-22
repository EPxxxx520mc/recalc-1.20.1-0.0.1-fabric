package com.rimeveil.recalc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.rimeveil.recalc.data.PlayerFrameData;
import com.rimeveil.recalc.networking.ModNetworking;
import com.rimeveil.recalc.util.LogUtil;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class RecalcCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("recalc")
            .then(literal("clear")
                .executes(context -> clearFrame(context.getSource()))
            )
        );
    }

    private static int clearFrame(ServerCommandSource source) {
        if (!(source.getEntity() instanceof ServerPlayerEntity player)) {
            source.sendError(Text.translatable("command.recalc.only_player"));
            return 0;
        }

        PlayerFrameData.detachFrame(player);
        ModNetworking.syncToPlayer(player, false);
        ModNetworking.sendRemoveAnimation(player);
        source.sendFeedback(() -> Text.translatable("command.recalc.frame_removed"), false);
        LogUtil.debug("Executed clear command for {}", player.getName().getString());
        return 1;
    }
}
