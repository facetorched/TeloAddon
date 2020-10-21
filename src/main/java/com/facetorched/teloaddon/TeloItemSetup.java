package com.facetorched.teloaddon;

import com.dunk.tfc.api.Metal;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.Core.Metal.MetalRegistry;
import com.dunk.tfc.Items.ItemMetalSheet;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.ItemSetup;
import com.dunk.tfc.Core.Metal.Alloy;
import com.facetorched.teloaddon.items.TeloItemGem;
import com.facetorched.teloaddon.items.TeloItemIngot;
import com.facetorched.teloaddon.items.TeloItemMeltedMetal;
import com.facetorched.teloaddon.items.TeloItemMetalSheet;
import com.facetorched.teloaddon.items.TeloItemMetalSheet2x;
import com.facetorched.teloaddon.items.TeloItemTerra;
import com.facetorched.teloaddon.util.Config;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

public class TeloItemSetup {
	public static Item alumina;
	public static Item aluminumIngot2x;
	public static Item aluminumSheet2x;
	public static Item aluminumIngot;
	public static Item aluminumSheet;
	public static Item aluminumUnshaped;
	public static Item ammoniumDiurinate;
	public static Item bauxiteOre;
	public static Item enrichedUraniumPowder;
	public static Item fiberglass;
	public static Item fluoritePowder;
	public static Item glassFiber;
	public static Item lye;
	public static Item fluorite;
	public static Item oilyMash;
	public static Item pitchblendePowder;
	public static Item uraniumHexafluoride;
	public static Item uranylNitrate;
	public static Item yellowcake;
	
	public static Item hydrofluoricAcidBottle;
	public static Item nitricAcidBottle;
	public static Item glycerolBottle;
	public static Item nitroglycerinBottle;
	
	public static Metal ALUMINUM;
	
	public static void setup() {
		if(Config.addAluminum) {
			alumina = registryHelper(new TeloItemTerra(),"Alumina");
			aluminumIngot2x = registryHelper((new TeloItemIngot()).setSize(EnumSize.LARGE).setMetal("Aluminum", 200),"Aluminum_Double_Ingot");
			aluminumSheet2x = registryHelper(new TeloItemMetalSheet2x(20).setMetal("Aluminum", 400),"Aluminum_Double_Sheet");
			aluminumIngot = registryHelper(new TeloItemIngot(),"Aluminum_Ingot");
			aluminumSheet = registryHelper((ItemMetalSheet)new TeloItemMetalSheet(20).setMetal("Aluminum", 200),"Aluminum_Sheet");
			aluminumUnshaped = registryHelper(new TeloItemMeltedMetal().setHasSolidLiquidStates(true).setMaxUnits(100).setCounter(1).setBaseDamage(0),"Aluminum_Unshaped");
			bauxiteOre = registryHelper(new TeloItemTerra(),"Bauxite_Ore");
		}
		if(Config.addFluorite) {
			fluoritePowder = registryHelper(new TeloItemTerra(),"Fluorite_Powder");
			fluorite = registryHelper(new TeloItemGem(),"Fluorite");
		}
		if(Config.addUranium) {
			ammoniumDiurinate = registryHelper(new TeloItemTerra(),"Ammonium_Diurinate");
			enrichedUraniumPowder = registryHelper(new TeloItemTerra(),"Enriched_Uranium_Powder");
			pitchblendePowder = registryHelper(new TeloItemTerra(),"Pitchblende_Powder");
			uraniumHexafluoride = registryHelper(new TeloItemTerra(),"Uranium_Hexafluoride");
			uranylNitrate = registryHelper(new TeloItemTerra(),"Uranyl_Nitrate");
			yellowcake = registryHelper(new TeloItemTerra(),"Yellowcake");
		}
		if(Config.addFiberglass) {
			fiberglass = registryHelper(new TeloItemTerra(),"Fiberglass");
			glassFiber = registryHelper(new TeloItemTerra(),"Glass_Fiber");
		}
		if (Config.addOilyMash)
			oilyMash = registryHelper(new TeloItemTerra(),"Oily_Mash");
		if (Config.addLye)
			lye = registryHelper(new TeloItemTerra(),"Lye");
		if (Config.addFluids) {
			hydrofluoricAcidBottle = registryHelper(new TeloItemTerra().setContainerItem(ItemSetup.glassBottle),"Hydrofluoric_Acid_Bottle");
			nitricAcidBottle = registryHelper(new TeloItemTerra().setContainerItem(ItemSetup.glassBottle),"Nitric_Acid_Bottle");
			glycerolBottle = registryHelper(new TeloItemTerra().setContainerItem(ItemSetup.glassBottle),"Glycerol_Bottle");
			nitroglycerinBottle = registryHelper(new TeloItemTerra().setContainerItem(ItemSetup.glassBottle),"Nitroglycerin_Bottle");
		}
		
		registerMetals();
	}
	public static void registerMetals() {
		if(Config.addAluminum) {
			ALUMINUM = (new Metal("Aluminum", aluminumIngot)).addValidMold(TFCItems.ceramicMold, aluminumUnshaped).addValidPartialMold(aluminumUnshaped, 2, aluminumUnshaped, 1, 2);
			MetalRegistry.instance.addMetal(ALUMINUM, Alloy.EnumTier.TierV);
		}
	}
	public static Item registryHelper(Item item, String unlocName) {
		item.setUnlocalizedName(unlocName);
		GameRegistry.registerItem(item, unlocName);
		item.setTextureName(unlocName);
		return item;
	}
}
