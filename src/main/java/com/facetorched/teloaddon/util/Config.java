package com.facetorched.teloaddon.util;

import static com.dunk.tfc.WorldGen.Generators.WorldGenOre.oreList;

import java.io.File;
import java.util.HashMap;

import com.dunk.tfc.WorldGen.Generators.OreSpawnData;
import com.dunk.tfc.api.Constant.Global;
import com.facetorched.teloaddon.TeloMod;
import com.google.common.collect.ObjectArrays;

import net.minecraftforge.common.config.Configuration;

public class Config {
	//configuration object
	public static Configuration config;
	
	//default values
	public static boolean addAluminum = true;
	public static boolean aluminumSmeltable = true;
	public static boolean addFluorite = true;
	public static boolean stoneDropFluorite = true;
	public static boolean addUranium = true;
	public static boolean addFiberglass = true;
	public static boolean addOilyMash = true;
	public static boolean addLye = true;
	public static boolean addFluids = true;
	public static boolean moreOil = true;
	public static boolean plantOilIE = true;
	public static boolean cokeOvenPitch = true;
	public static boolean addCreosoteFluid = true;
	public static boolean hotspringBucket = true;
	public static String [] dieselGeneratorFuels = {"biodiesel,1000","ethanol,200","plantoil,200","oliveoil,200","telocreosote,20"};
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
		aluminumSmeltable = config.getBoolean("aluminumSmeltable", "Items", true, "Set to false to prevent bauxite from being smelted to aluminum");
		addFluorite = config.getBoolean("addFluorite", "Items", true, "Set to false to prevent fluorite gems from being added to the game");
		stoneDropFluorite = config.getBoolean("stoneDropFluorite", "Items", true, "Set to false to prevent fluorite gems dropping from stone");
		addUranium = config.getBoolean("addUranium", "Items", true, "Set to false to prevent uranium items from being added to the game");
		addFiberglass = config.getBoolean("addFiberglass", "Items", true, "Set to false to prevent fiberglass items from being added to the game");
		addOilyMash = config.getBoolean("addOilyMash", "Items", true, "Set to false to prevent oily mash item from being added to the game");
		addLye = config.getBoolean("addLye", "Items", true, "Set to false to prevent lye item from being added to the game");
		addFluids = config.getBoolean("addFluids", "Fluids", true, "Set to false to prevent new fluids from being added to the game");
		moreOil = config.getBoolean("moreOil", "Fluids", true, "Coconuts and soybeans can make olive oil. Set false to prevent this");
		hotspringBucket = config.getBoolean("hotSpringBucket", "Fluids", true, "Set false to disable picking up hotsprings with red steel buckets");
		plantOilIE = config.getBoolean("plantOilIE","Immersive Engineering",true,"If Immersive Engineering is loaded, Coconuts and soybeans make plant oil. This overrides \"moreOil\"");
		cokeOvenPitch = config.getBoolean("cokeOvenPitch", "Immersive Engineering", true, "If Immersive Engineering is loaded, the coke oven outputs pitch instead of creosote");
		addCreosoteFluid = config.getBoolean("addCreosoteFluid", "Immersive Engineering", true, "If Immersive Engineering is loaded, a new creosote fluid is added that is obtained by distillation of pitch");
		dieselGeneratorFuels = config.getStringList("dieselGeneratorFuels", "Immersive Engineering" ,dieselGeneratorFuels,
				"If Immersive engineering is loaded, these are valid fuels. Format each line: fluidname,burnduration. Any value above 1000 results in infinite burn duration");
		dieselGeneratorFuelsMap = configStringParser(dieselGeneratorFuels);
		
		if (config.hasChanged()) config.save();
	}
	
	//this must be run in the init phase (after blocks setup but before world gen)
	public static void reloadOres() {
		oreList.put("Bauxite", getOreData("Bauxite", "veins", "small", TeloMod.MODID+":bauxiteOre", 0, 35, new String[]{"limestone","dolomite","granite","gneiss","basalt","shale"}, 128, 240, 40, 40));
		if (config.hasChanged()) config.save();
	}
	
	//takes a string array input with "key,number" and outputs a hashmap with equivalent entries
	public static HashMap<String,Integer> configStringParser(String[] input) {
		HashMap<String,Integer> output = new HashMap<String,Integer>();
		for(String line : input) {
			String values[] =  line.trim().split(",");
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
	
	//Copied from tfc since this is all private
	private static final String[] ALLOWED_TYPES = new String[] {"default", "veins"};
	private static final String[] ALLOWED_SIZES = new String[] {"small", "medium", "large"};
	private static final String[] ALLOWED_BASE_ROCKS = ObjectArrays.concat(Global.STONE_ALL, new String[] {"igneous intrusive", "igneous extrusive", "sedimentary", "metamorphic"}, String.class);
	private static OreSpawnData getOreData(String category, String type, String size, String blockName, int meta, int rarity, String[] rocks, int min, int max, int v, int h)
	{
		return new OreSpawnData(
				config.get(category, "type", type).setValidValues(ALLOWED_TYPES).getString(),
				config.get(category, "size", size).setValidValues(ALLOWED_SIZES).getString(),
				config.get(category, "oreName", blockName).getString(),
				config.get(category, "oreMeta", meta).getInt(),
				config.get(category, "rarity", rarity).getInt(),
				config.get(category, "baseRocks", rocks).setValidValues(ALLOWED_BASE_ROCKS).getStringList(),
				config.get(category, "Minimum Height", min).getInt(),
				config.get(category, "Maximum Height", max).getInt(),
				config.get(category, "Vertical Density", v).getInt(),
				config.get(category, "Horizontal Density", h).getInt()
		);
	}
}
