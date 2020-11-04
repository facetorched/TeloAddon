package com.facetorched.teloaddon.util.compat;

import java.util.HashMap;

import com.dunk.tfc.TileEntities.TEHopper;
import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Crafting.BarrelFireRecipe;
import com.dunk.tfc.api.Crafting.BarrelManager;
import com.facetorched.teloaddon.TeloFluidSetup;
import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.util.Config;
import com.facetorched.teloaddon.util.TeloLogger;

import blusunrize.immersiveengineering.api.energy.DieselHandler;
import blusunrize.immersiveengineering.api.tool.ChemthrowerHandler;
import blusunrize.immersiveengineering.common.IEContent;
import blusunrize.immersiveengineering.common.util.IEPotions;
import blusunrize.immersiveengineering.api.tool.ChemthrowerHandler.ChemthrowerEffect_Potion;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ImmersiveEngineering {

    public static void postInit() {
    	//detect if IE is loaded
    	if(Loader.isModLoaded("ImmersiveEngineering")){
    		try {
    			
    			TeloLogger.info("Loading compatability for immersive engineering");
    			HashMap<String,Integer> fuels = Config.dieselGeneratorFuelsMap;
    			for(String key : fuels.keySet()) {
    				DieselHandler.registerFuel(FluidRegistry.getFluid(key),fuels.get(key));
    			}
    			
    			if (Config.plantOilIE) {
    				TEHopper.registerPressableItem(TFCItems.coconutMeat,IEContent.fluidPlantoil,0.64f,10);
    				TEHopper.registerPressableItem(TFCItems.soybean,IEContent.fluidPlantoil,0.64f,10);
    			}
    			
    			//setup compatibility IE items
    			TeloItemSetup.setupIE();
    			
    			if(Config.cokeOvenPitch && Config.addCreosoteFluid) {
    				//register distillation recipe
    				BarrelManager.getInstance()
    				.addRecipe(((BarrelFireRecipe) new BarrelFireRecipe(null, new FluidStack(TFCFluids.PITCH, 500), null, new FluidStack(TeloFluidSetup.teloCreosote, 250), 200))
    				.setDistillationRecipe(true).setFireTicksScale(false).setSealTime(0).setSealedRecipe(true));
    			}
    			if(Config.addCreosoteFluid) {
    				ChemthrowerHandler.registerEffect(TeloFluidSetup.teloCreosote, new ChemthrowerEffect_Potion(null,0, IEPotions.flammable,140,0));
    				ChemthrowerHandler.registerFlammable(TeloFluidSetup.teloCreosote);
    			}

    		}
    		catch(Exception e) {
    			TeloLogger.error("postinit compatability for immersive engineering failed with exception: "+ e.getMessage());
    		}
    	}
    	else {
    		TeloLogger.info("Immersive Engineering was not detected");
    	}
    }
    public static void preInit() {
    	if(Loader.isModLoaded("ImmersiveEngineering")){
    		try {
    			if(Config.cokeOvenPitch)
    				IEContent.fluidCreosote = TFCFluids.PITCH;
    		}
    		catch(Exception e) {
    			TeloLogger.error("preinit compatability for immersive engineering failed with exception: "+ e.getMessage());
    		}
    	}
    }
}
