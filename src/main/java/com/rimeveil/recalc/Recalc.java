package com.rimeveil.recalc;

import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.Item.Moditemgroup;
import com.rimeveil.recalc.block.Modblock;
import com.rimeveil.recalc.command.RecalcCommand;
import com.rimeveil.recalc.networking.ModNetworking;
import com.rimeveil.recalc.sound.ModSoundEvents;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Recalc implements ModInitializer {
    public static final String MOD_ID = "recalc";
    public static final String MOD_VERSION = "0.0.1";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        Moditem.regitems();
        Moditemgroup.regitemtogroup();
        Modblock.regblocks();
        ModSoundEvents.registerSounds();

        CommandRegistrationCallback.EVENT.register(RecalcCommand::register);
        ModNetworking.register();

        LOGGER.info("Recalc initialized");
    }
}
