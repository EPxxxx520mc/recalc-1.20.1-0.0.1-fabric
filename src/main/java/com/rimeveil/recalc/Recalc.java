 package com.rimeveil.recalc;

import net.fabricmc.api.ModInitializer;
import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.Item.Moditemgroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Recalc implements ModInitializer {
	public static final String MOD_ID = "recalc";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		// Runs our example mod item registry.
		Moditem.regitems();
		Moditemgroup.regitemtogroup();
		
		LOGGER.info("Hello Fabric world!");
	}
}