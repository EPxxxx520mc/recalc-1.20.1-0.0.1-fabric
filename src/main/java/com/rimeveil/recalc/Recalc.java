 package com.rimeveil.recalc;

import net.fabricmc.api.ModInitializer;
import com.rimeveil.recalc.Item.Moditem;
import com.rimeveil.recalc.Item.Moditemgroup;
import com.rimeveil.recalc.block.Modblock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.rimeveil.recalc.sound.ModSoundEvents;

public class Recalc implements ModInitializer {
	public static final String MOD_ID = "recalc";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	// 入口
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		// Runs our example mod item registry.



		Moditem.regitems();//加载物品
		LOGGER.info("load items");//控制台输出load items
		Moditemgroup.regitemtogroup();//加载物品组
		LOGGER.info("load item groups");//控制台输出load item groups
		Modblock.regblocks();//加载方块
		LOGGER.info("load blocks");//控制台输出load blocks
		ModSoundEvents.registerSounds();//加载音效
		LOGGER.info("load sounds");//控制台输出load sounds
		


		
		LOGGER.info("Hello recalc!");//控制台输出Hello recalc!
	}
}