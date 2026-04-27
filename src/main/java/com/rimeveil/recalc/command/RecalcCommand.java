package com.rimeveil.recalc.command;

import com.mojang.brigadier.CommandDispatcher;
import com.rimeveil.recalc.data.PlayerFrameData;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.literal;

public class RecalcCommand {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(literal("recalc")
            .then(literal("clear")
                .executes(context -> {
                    if (context.getSource().getPlayer() != null) {
                        PlayerFrameData.detachFrame(context.getSource().getPlayer());
                        context.getSource().sendFeedback(() -> Text.translatable("command.recalc.frame_removed"), false);
                        return 1;
                    }
                    context.getSource().sendError(Text.translatable("command.recalc.only_player"));
                    return 0;
                })
            )
        );
    }
}
