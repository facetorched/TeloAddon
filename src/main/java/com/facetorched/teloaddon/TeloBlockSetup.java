package com.facetorched.teloaddon;

import com.facetorched.teloaddon.blocks.TeloBlockIngotPile;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class TeloBlockSetup {
	public static Block ingotPile;
	public static void setup() {
		ingotPile = new TeloBlockIngotPile().setBlockName("ingotpile").setHardness(3);
		GameRegistry.registerBlock(ingotPile, "teloIngotPile");
		
	}
}
