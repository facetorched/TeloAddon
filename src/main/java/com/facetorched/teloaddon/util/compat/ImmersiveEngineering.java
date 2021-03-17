package com.facetorched.teloaddon.util.compat;

import java.util.HashMap;

import com.dunk.tfc.Core.FluidBaseTFC;
import com.dunk.tfc.TileEntities.TEHopper;
import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Crafting.BarrelFireRecipe;
import com.dunk.tfc.api.Crafting.BarrelManager;
import com.facetorched.teloaddon.TeloFluidSetup;
import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.items.ItemBottle;
import com.facetorched.teloaddon.items.ItemCeramicBucket;
import com.facetorched.teloaddon.items.ItemChainsaw;
import com.facetorched.teloaddon.items.ItemFluidContainer;
import com.facetorched.teloaddon.items.ItemWoodenBucket;
import com.facetorched.teloaddon.items.TeloItemTerra;
import com.facetorched.teloaddon.util.Config;
import com.facetorched.teloaddon.util.TeloLogger;

import blusunrize.immersiveengineering.api.energy.DieselHandler;
import blusunrize.immersiveengineering.api.tool.ChemthrowerHandler;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.util.IEPotions;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ImmersiveEngineering {

    public static void preInit() {
    	if(Loader.isModLoaded("ImmersiveEngineering")){
    		try {
    			if(Config.cokeOvenPitch)
    				IEContent.fluidCreosote = TFCFluids.PITCH;
    			//setup compatibility IE items
    			itemSetup();
    		}
    		catch(Exception e) {
    			TeloLogger.error("preInit compatability for immersive engineering failed with exception: "+ e.getMessage());
    		}
    	}
    }
    
	public static void init() {
		if(Loader.isModLoaded("ImmersiveEngineering")){
    		try {
    			
    			TeloLogger.info("Loading init compatability for immersive engineering");
				HashMap<String,Integer> fuels = Config.dieselGeneratorFuelsMap;
				for(String key : fuels.keySet()) {
					DieselHandler.registerFuel(FluidRegistry.getFluid(key),fuels.get(key));
				}
				TeloLogger.info("Finished loading init compatability for immersive engineering");
    		}
    		catch(Exception e) {
    			TeloLogger.error("init compatability for immersive engineering failed with exception: "+ e.getMessage());
    		}
		}
	}
	
    public static void postInit() {
    	//detect if IE is loaded
    	if(Loader.isModLoaded("ImmersiveEngineering")){
    		try {
    			
    			TeloLogger.info("Loading postInit compatability for immersive engineering");
    			
    			if (Config.plantOilIE) {
    				TEHopper.registerPressableItem(TFCItems.coconutMeat,IEContent.fluidPlantoil,0.64f,10);
    				TEHopper.registerPressableItem(TFCItems.soybean,IEContent.fluidPlantoil,0.64f,10);
    			}
    			
    			if(Config.cokeOvenPitch && Config.addCreosoteFluid) {
    				//register distillation recipe
    				BarrelManager.getInstance()
    				.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.PITCH, 500), null, new FluidStack(TeloFluidSetup.teloCreosote, 250), 200))
    				.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
    			}
    			if(Config.addCreosoteFluid) {
    				ChemthrowerHandler.registerEffect(TeloFluidSetup.teloCreosote, new ChemthrowerHandler.ChemthrowerEffect_Potion(null,0, IEPotions.flammable,140,0));
    				ChemthrowerHandler.registerFlammable(TeloFluidSetup.teloCreosote);
    			}
    			
    			TeloLogger.info("Finished loading postInit compatability for immersive engineering");

    		}
    		catch(Exception e) {
    			TeloLogger.error("postinit compatability for immersive engineering failed with exception: "+ e.getMessage());
    		}
    	}
    	else {
    		TeloLogger.info("Immersive Engineering was not detected");
    	}
    }

    public static void itemSetup() {
		String path = "immersiveengineering:textures/blocks/fluid/";
		if(Config.addCreosoteFluid) {
			((FluidBaseTFC) TeloFluidSetup.teloCreosote).setBaseColor(0xFFFFFF);
			((ItemFluidContainer)TeloItemSetup.creosoteBottle).setFluidTextureLocation(path+"creosote_still.png");
			((ItemFluidContainer)TeloItemSetup.creosoteWoodenBucket).setFluidTextureLocation(path+"creosote_still.png");
			((ItemFluidContainer)TeloItemSetup.creosoteCeramicBucket).setFluidTextureLocation(path+"creosote_still.png");
		}
		else if (!Config.cokeOvenPitch) { //only case where we use normal IE creosote
			 Fluid creosote = IEContent.fluidCreosote;
			 TeloItemSetup.creosoteBottle = TeloItemSetup.registryHelper(new ItemBottle(path+"creosote_still.png").registerContainer(creosote, 250),"Creosote_Bottle");
			 TeloItemSetup.creosoteWoodenBucket = TeloItemSetup.registryHelper(new ItemWoodenBucket(path+"creosote_still.png").registerContainer(creosote, 1000),"Creosote_Wooden_Bucket");
			 TeloItemSetup.creosoteCeramicBucket = TeloItemSetup.registryHelper(new ItemCeramicBucket(path+"creosote_still.png").registerContainer(creosote, 1000),"Creosote_Ceramic_Bucket");
		}
		
		//ItemBottle() takes a texture path to the fluid block to use as the fluid color in the case that the fluid color is not defined

		TeloItemSetup.biodieselBottle = TeloItemSetup.registryHelper(new ItemBottle(path+"biodiesel_still.png").registerContainer(IEContent.fluidBiodiesel, 250),"Biodiesel_Bottle");
		TeloItemSetup.plantOilBottle = TeloItemSetup.registryHelper(new ItemBottle(path+"plantoil_still.png").registerContainer(IEContent.fluidPlantoil, 250),"Plant_Oil_Bottle");
		TeloItemSetup.ethanolBottle = TeloItemSetup.registryHelper(new ItemBottle(path+"ethanol_still.png").registerContainer(IEContent.fluidEthanol, 250),"Ethanol_Bottle");
		
		TeloItemSetup.biodieselWoodenBucket = TeloItemSetup.registryHelper(new ItemWoodenBucket(path+"biodiesel_still.png").registerContainer(IEContent.fluidBiodiesel, 1000),"Biodiesel_Wooden_Bucket");
		TeloItemSetup.plantOilWoodenBucket = TeloItemSetup.registryHelper(new ItemWoodenBucket(path+"plantoil_still.png").registerContainer(IEContent.fluidPlantoil, 1000),"Plant_Oil_Wooden_Bucket");
		TeloItemSetup.ethanolWoodenBucket = TeloItemSetup.registryHelper(new ItemWoodenBucket(path+"ethanol_still.png").registerContainer(IEContent.fluidEthanol, 1000),"Ethanol_Wooden_Bucket");
		
		TeloItemSetup.biodieselCeramicBucket = TeloItemSetup.registryHelper(new ItemCeramicBucket(path+"biodiesel_still.png").registerContainer(IEContent.fluidBiodiesel, 1000),"Biodiesel_Ceramic_Bucket");
		TeloItemSetup.plantOilCeramicBucket = TeloItemSetup.registryHelper(new ItemCeramicBucket(path+"plantoil_still.png").registerContainer(IEContent.fluidPlantoil, 1000),"Plant_Oil_Ceramic_Bucket");
		TeloItemSetup.ethanolCeramicBucket = TeloItemSetup.registryHelper(new ItemCeramicBucket(path+"ethanol_still.png").registerContainer(IEContent.fluidEthanol, 1000),"Ethanol_Ceramic_Bucket");
		
		if(Config.addChainsaw) {
			TeloItemSetup.chainsawBlade = TeloItemSetup.registryHelper(new TeloItemTerra(), "Chainsaw_Blade");
			TeloItemSetup.chainsawChain = TeloItemSetup.registryHelper(new TeloItemTerra(), "Chainsaw_Chain");
			TeloItemSetup.chainsawHousing = TeloItemSetup.registryHelper(new TeloItemTerra(), "Chainsaw_Housing");
			TeloItemSetup.redSteelChainsawChainLink = TeloItemSetup.registryHelper(new TeloItemTerra(), "Red_Steel_Chainsaw_Chain_Link");
			TeloItemSetup.blueSteelChainsawChainLink = TeloItemSetup.registryHelper(new TeloItemTerra(), "Blue_Steel_Chainsaw_Chain_Link");
			//name,mining level,durability,miningspeed,attack,enchantability
			TeloItemSetup.chainsawToolMaterial = EnumHelper.addToolMaterial("Chainsaw", 3, Config.chainsawDurability, 60, 20, 22);
			TeloItemSetup.chainsaw = TeloItemSetup.registryHelper(new ItemChainsaw(TeloItemSetup.chainsawToolMaterial,Config.chainsawDamage).setAttackSpeed(30),"Chainsaw").setTextureName("tools/Chainsaw");
			OreDictionary.registerOre("itemAxeSteel",new ItemStack(TeloItemSetup.chainsaw, 1, OreDictionary.WILDCARD_VALUE));
		}
    }
}
