package com.facetorched.teloaddon.util;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;

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
	public static boolean moreOil = true;
	public static boolean plantOilIE = true;
	public static String dieselGeneratorFuels = "biodiesel,1000\nethanol,200\nplantoil,200\noliveoil,200\ncreosote,20";
	public static HashMap<String,Integer> dieselGeneratorFuelsMap = new HashMap<String,Integer>();
	
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
		addFluids = config.getBoolean("addFluids", "Fluids", true, "Set to false to prevent new fluids from being added to the game");
		moreOil = config.getBoolean("moreOil", "Fluids", true, "Coconuts and soybeans can make olive oil. Set false to prevent this");
		plantOilIE = config.getBoolean("plantOilIE","Immersive Engineering",true,"If Immersive Engineering is loaded, Coconuts and soybeans make plant oil. This overrides \"moreOil\"");
		dieselGeneratorFuels = config.getString("dieselGeneratorFuels", "Immersive Engineering", "\nbiodiesel,1000\nethanol,200\nplantoil,200\noliveoil,200\ncreosote,20", 
				"If Immersive engineering is loaded, these are valid fuels. Format each line: fluidname,burnduration. Any value above 1000 results in infinite burn duration");
		dieselGeneratorFuelsMap = configStringParser(dieselGeneratorFuels);
		if (config.hasChanged()) config.save();
	}
	public static HashMap<String,Integer> configStringParser(String input) {
		HashMap<String,Integer> output = new HashMap<String,Integer>();
		String lines[] = input.trim().split("\\r?\\n");
		for(String line : lines) {
			String values[] =  line.split(",");
			try{
				output.put(values[0].trim(),Integer.parseInt(values[1].trim()));
			}
			catch(NumberFormatException e)  
			{
				TeloLogger.error("Unable to parse int value "+e.getMessage());
			}
		}
		return (output);
	}
}
