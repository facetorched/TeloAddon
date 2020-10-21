package com.facetorched.teloaddon;

import com.facetorched.teloaddon.blocks.TeloBlockIngotPile;
import com.facetorched.teloaddon.util.Config;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class TeloBlockSetup {
	
	public static Block ingotPile;
	
	public static void setup() {
		if (Config.addAluminum) {
			ingotPile = new TeloBlockIngotPile().setBlockName("ingotpile").setHardness(3);
			GameRegistry.registerBlock(ingotPile, "teloIngotPile");
		}
	}
}
