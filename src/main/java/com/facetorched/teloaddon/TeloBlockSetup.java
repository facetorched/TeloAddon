package com.facetorched.teloaddon;

import com.facetorched.teloaddon.blocks.TeloBlockIngotPile;
import com.facetorched.teloaddon.blocks.TeloBlockOre;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class TeloBlockSetup {
	
	public static Block ingotPile;
	public static Block bauxite;
	
	public static void loadBlocks() {
		ingotPile = new TeloBlockIngotPile().setBlockName("teloIngotPile").setHardness(3);
		bauxite = new TeloBlockOre(Material.rock).teloSetHarvestLevel("pickaxe", 1).setDroppedItem(TeloItemSetup.bauxite).setHardness(10F).setResistance(10F).setBlockName("bauxiteOre");
	}
	public static void registerBlocks() {
		GameRegistry.registerBlock(bauxite, "bauxiteOre");
		GameRegistry.registerBlock(ingotPile, "teloIngotPile");
	}
}
