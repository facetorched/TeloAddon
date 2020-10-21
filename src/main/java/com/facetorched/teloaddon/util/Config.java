package com.facetorched.teloaddon.util;

import java.io.File;

import net.minecraftforge.common.config.Configuration;

public class Config {
	//configuration object
	public static Configuration config;
	
	//default values
	public static boolean addAluminum = true;
	public static boolean addFluorite = true;
	public static boolean stoneDropFluorite = true;
	public static boolean addUranium = true;
	public static boolean addFiberglass = true;
	public static boolean addOilyMash = true;
	public static boolean addLye = true;
	public static boolean addFluids = true;
	
	public static void preInit(File configDir)
	{
		if (config != null) throw new IllegalStateException("Preinit can't be called twice.");
		config = new Configuration(new File(configDir,"TeloAddon.cfg"));
	}
	public static void reload()
	{
		if (config == null) throw new IllegalStateException("Config reload attempt before preinit.");
		TeloLogger.info("Loading TeloAddon Config");
		config.load();
		addAluminum = config.getBoolean("addAluminum", "Items", true, "Set to false to prevent aluminum items from being added to the game");
		addFluorite = config.getBoolean("addFluorite", "Items", true, "Set to false to prevent fluorite gems from being added to the game");
		stoneDropFluorite = config.getBoolean("stoneDropFluorite", "Items", true, "Set to false to prevent fluorite gems dropping from stone");
		addUranium = config.getBoolean("addUranium", "Items", true, "Set to false to prevent uranium items from being added to the game");
		addFiberglass = config.getBoolean("addFiberglass", "Items", true, "Set to false to prevent fiberglass items from being added to the game");
		addOilyMash = config.getBoolean("addOilyMash", "Items", true, "Set to false to prevent oily mash item from being added to the game");
		addLye = config.getBoolean("addLye", "Items", true, "Set to false to prevent lye item from being added to the game");
		addFluids = config.getBoolean("addFluids", "Fluids", true, "Set to false to prevent fiberglass items from being added to the game");
		if (config.hasChanged()) config.save();
	}	
}
