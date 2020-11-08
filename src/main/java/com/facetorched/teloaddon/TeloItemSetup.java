package com.facetorched.teloaddon;

import com.dunk.tfc.api.Metal;
import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.Core.Metal.MetalRegistry;
import com.dunk.tfc.Items.ItemMetalSheet;
import com.dunk.tfc.api.Enums.EnumSize;
import com.dunk.tfc.BlockSetup;
import com.dunk.tfc.ItemSetup;
import com.dunk.tfc.Core.Metal.Alloy;
import com.facetorched.teloaddon.items.ItemBottle;
import com.facetorched.teloaddon.items.ItemCeramicBucket;
import com.facetorched.teloaddon.items.ItemLeadBottle;
import com.facetorched.teloaddon.items.ItemWoodenBucket;
import com.facetorched.teloaddon.items.TeloItemGem;
import com.facetorched.teloaddon.items.TeloItemIngot;
import com.facetorched.teloaddon.items.TeloItemMeltedMetal;
import com.facetorched.teloaddon.items.TeloItemMetalSheet;
import com.facetorched.teloaddon.items.TeloItemMetalSheet2x;
import com.facetorched.teloaddon.items.TeloItemSteelBucket;
import com.facetorched.teloaddon.items.TeloItemTerra;
import com.facetorched.teloaddon.util.Config;

import blusunrize.immersiveengineering.common.IEContent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

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
	public static Item potash;
	public static Item uraniumHexafluoride;
	public static Item uranylNitrate;
	public static Item yellowcake;
	
	public static Item leadBottle;
	public static Item hydrofluoricAcidBottle;
	public static Item nitricAcidBottle;
	public static Item glycerolBottle;
	public static Item nitroglycerinBottle;
	
	public static Item biodieselBottle;
	public static Item plantOilBottle;
	public static Item ethanolBottle;
	public static Item creosoteBottle;
	public static Item biodieselWoodenBucket;
	public static Item plantOilWoodenBucket;
	public static Item ethanolWoodenBucket;
	public static Item creosoteWoodenBucket;
	public static Item biodieselCeramicBucket;
	public static Item plantOilCeramicBucket;
	public static Item ethanolCeramicBucket;
	public static Item creosoteCeramicBucket;
	
	public static Item oliveOilBottle;
	public static Item oliveOilWoodenBucket;
	public static Item oliveOilCeramicBucket;
	
	public static Item distilledWaterBottle;
	public static Item distilledWaterWoodenBucket;
	public static Item distilledWaterCeramicBucket;
	
	public static Item hotspringRedSteelBucket;
	
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
			
			OreDictionary.registerOre("ingotDoubleAluminum",aluminumIngot2x);
			OreDictionary.registerOre("plateDoubleAluminum",aluminumSheet2x);
			OreDictionary.registerOre("ingotAluminum",aluminumIngot);
			OreDictionary.registerOre("plateAluminum",aluminumSheet);
			OreDictionary.registerOre("ingotAluminum",aluminumIngot);
			OreDictionary.registerOre("oreAluminum",bauxiteOre);
		}
		if(Config.addFluorite) {
			fluoritePowder = registryHelper(new TeloItemTerra(),"Fluorite_Powder");
			fluorite = registryHelper(new TeloItemGem(),"Fluorite");
		}
		if(Config.addUranium) {
			ammoniumDiurinate = registryHelper(new TeloItemTerra(),"Ammonium_Diuranate");
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
			potash = registryHelper(new TeloItemTerra(),"Potash");
		if (Config.addFluids) {
			leadBottle = registryHelper(new TeloItemTerra(),"Lead_Bottle");
			hydrofluoricAcidBottle = registryHelper(new ItemLeadBottle().registerContainer(TeloFluidSetup.hydrofluoricAcid, 250),"Hydrofluoric_Acid_Bottle");
			nitricAcidBottle = registryHelper(new ItemBottle().registerContainer(TeloFluidSetup.nitricAcid, 250),"Nitric_Acid_Bottle");
			glycerolBottle = registryHelper(new ItemBottle().registerContainer(TeloFluidSetup.glycerol, 250),"Glycerol_Bottle");
			nitroglycerinBottle = registryHelper(new ItemBottle().registerContainer(TeloFluidSetup.nitroglycerin, 250),"Nitroglycerin_Bottle");
		}
		//Just some fluid container additions. should be pretty benign
		oliveOilBottle = registryHelper(new ItemBottle().registerContainer(TFCFluids.OLIVEOIL, 250),"Olive_Oil_Bottle");
		oliveOilWoodenBucket = registryHelper(new ItemWoodenBucket().registerContainer(TFCFluids.OLIVEOIL, 1000),"Olive_Oil_Wooden_Bucket");
		oliveOilCeramicBucket = registryHelper(new ItemCeramicBucket().registerContainer(TFCFluids.OLIVEOIL, 1000),"Olive_Oil_Ceramic_Bucket");
		
		distilledWaterBottle = registryHelper(new ItemBottle("minecraft:textures/blocks/water_still.png").registerContainer(FluidRegistry.WATER, 250),"Distilled_Water_Bottle");
		distilledWaterWoodenBucket = registryHelper(new ItemCeramicBucket("minecraft:textures/blocks/water_still.png").registerContainer(FluidRegistry.WATER, 1000),"Distilled_Water_Wooden_Bucket");
		distilledWaterCeramicBucket = registryHelper(new ItemWoodenBucket("minecraft:textures/blocks/water_still.png").registerContainer(FluidRegistry.WATER, 1000),"Distilled_Water_Ceramic_Bucket");
		
		if(Config.hotspringBucket) {
			hotspringRedSteelBucket = registryHelper(new TeloItemSteelBucket(BlockSetup.hotWater).setContainerItem(ItemSetup.redSteelBucketEmpty).registerContainer(TFCFluids.HOTWATER,1000),"Hotspring_Red_Steel_Bucket");
		}
		
		registerMetals();
	}
	public static void setupIE() {
		Fluid creosote = null;
		String path = "immersiveengineering:textures/blocks/fluid/";
		
		//should we use IE creosote or TeloAddon creosote?
		if (Config.addCreosoteFluid) {
			creosote = TeloFluidSetup.teloCreosote;
		}
		else if (!Config.cokeOvenPitch) {
			 creosote = IEContent.fluidCreosote;
		}
		if (creosote!=null) {
			creosoteBottle = registryHelper(new ItemBottle(path+"creosote_still.png").registerContainer(creosote, 250),"Creosote_Bottle");
			creosoteWoodenBucket = registryHelper(new ItemWoodenBucket(path+"creosote_still.png").registerContainer(creosote, 1000),"Creosote_Wooden_Bucket");
			creosoteCeramicBucket = registryHelper(new ItemCeramicBucket(path+"creosote_still.png").registerContainer(creosote, 1000),"Creosote_Ceramic_Bucket");
		}
		//ItemBottle() takes a texture path to the fluid block to use as the fluid color in the case that the fluid color is not defined

		biodieselBottle = registryHelper(new ItemBottle(path+"biodiesel_still.png").registerContainer(IEContent.fluidBiodiesel, 250),"Biodiesel_Bottle");
		plantOilBottle = registryHelper(new ItemBottle(path+"plantoil_still.png").registerContainer(IEContent.fluidPlantoil, 250),"Plant_Oil_Bottle");
		ethanolBottle = registryHelper(new ItemBottle(path+"ethanol_still.png").registerContainer(IEContent.fluidEthanol, 250),"Ethanol_Bottle");
		
		biodieselWoodenBucket = registryHelper(new ItemWoodenBucket(path+"biodiesel_still.png").registerContainer(IEContent.fluidBiodiesel, 1000),"Biodiesel_Wooden_Bucket");
		plantOilWoodenBucket = registryHelper(new ItemWoodenBucket(path+"plantoil_still.png").registerContainer(IEContent.fluidPlantoil, 1000),"Plant_Oil_Wooden_Bucket");
		ethanolWoodenBucket = registryHelper(new ItemWoodenBucket(path+"ethanol_still.png").registerContainer(IEContent.fluidEthanol, 1000),"Ethanol_Wooden_Bucket");
		
		biodieselCeramicBucket = registryHelper(new ItemCeramicBucket(path+"biodiesel_still.png").registerContainer(IEContent.fluidBiodiesel, 1000),"Biodiesel_Ceramic_Bucket");
		plantOilCeramicBucket = registryHelper(new ItemCeramicBucket(path+"plantoil_still.png").registerContainer(IEContent.fluidPlantoil, 1000),"Plant_Oil_Ceramic_Bucket");
		ethanolCeramicBucket = registryHelper(new ItemCeramicBucket(path+"ethanol_still.png").registerContainer(IEContent.fluidEthanol, 1000),"Ethanol_Ceramic_Bucket");
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
