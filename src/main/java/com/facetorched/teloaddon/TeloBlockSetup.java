package com.facetorched.teloaddon;

import com.facetorched.teloaddon.blocks.TeloBlockIngotPile;
import com.facetorched.teloaddon.blocks.TeloBlockOre;
import com.facetorched.teloaddon.util.Config;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class TeloBlockSetup {
	
	public static Block ingotPile;
	public static Block ore;
	
	public static void loadBlocks() {
		if (Config.addAluminum) {
			ingotPile = new TeloBlockIngotPile().setBlockName("ingotpile").setHardness(3);
			ore = new TeloBlockOre(Material.rock).setHardness(10F).setResistance(10F).setBlockName("Ore");
			ore.setHarvestLevel("pickaxe", 1);
		}
	}
	public static void registerBlocks() {
		GameRegistry.registerBlock(ore, "Ore");
		GameRegistry.registerBlock(ingotPile, "teloIngotPile");
	}
}
