package com.facetorched.teloaddon;

import com.dunk.tfc.api.Metal;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.Core.Metal.MetalRegistry;
import com.dunk.tfc.Items.ItemIngot;
import com.dunk.tfc.Items.ItemMetalSheet;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.Core.Metal.Alloy;
import com.facetorched.teloaddon.items.TeloItemIngot;
import com.facetorched.teloaddon.items.TeloItemMeltedMetal;
import com.facetorched.teloaddon.items.TeloItemMetalSheet;
import com.facetorched.teloaddon.items.TeloItemTerra;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class TeloItemSetup {
	public static Item bauxiteOre;
	public static Item aluminumIngot;
	public static Item aluminumIngot2x;
	public static Item aluminumSheet2x;
	public static Item aluminumSheet;
	public static Item aluminumUnshaped;
	public static Item oilyMash;
	public static Metal ALUMINUM;
	
	public static void setup() {
		oilyMash = registryHelper(new TeloItemTerra(),"Oily_Mash");
		bauxiteOre = registryHelper(new TeloItemTerra(),"Bauxite_Ore");
		aluminumIngot = registryHelper(new TeloItemIngot(),"Aluminum_Ingot");
		aluminumIngot2x = registryHelper((new TeloItemIngot()).setSize(EnumSize.LARGE).setMetal("Aluminum", 200),"Aluminum_Double_Ingot");
		aluminumUnshaped = registryHelper(new TeloItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1).setBaseDamage(0),"Aluminum_Unshaped");
		aluminumSheet = registryHelper((ItemMetalSheet)new TeloItemMetalSheet(6900).setMetal("Aluminum", 200),"Aluminum_Sheet");
		aluminumSheet2x = registryHelper(new TeloItemMetalSheet(6900).setMetal("Aluminum", 400),"Aluminum_Double_Sheet");
		
		registerMetals();
	}
	public static void registerMetals() {
		ALUMINUM = (new Metal("Aluminum", aluminumIngot)).addValidMold(TFCItems.ceramicMold, aluminumUnshaped).addValidPartialMold(aluminumUnshaped, 2, aluminumUnshaped, 1, 2);
		MetalRegistry.instance.addMetal(ALUMINUM, Alloy.EnumTier.TierV);
	}
	public static Item registryHelper(Item item, String unlocName) {
		item.setUnlocalizedName(unlocName);
		GameRegistry.registerItem(item, unlocName);
		item.setTextureName("teloaddon:"+unlocName);
		return item;
	}
}
