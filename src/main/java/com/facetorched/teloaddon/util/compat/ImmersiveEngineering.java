package com.facetorched.teloaddon.util.compat;

import com.dunk.tfc.api.TFCFluids;
import com.facetorched.teloaddon.util.TeloLogger;

import blusunrize.immersiveengineering.api.energy.DieselHandler;
import blusunrize.immersiveengineering.common.IEContent;
import cpw.mods.fml.common.Loader;

public class ImmersiveEngineering {

    public static void load() {
    	//detect if IE is loaded
    	if(Loader.isModLoaded("ImmersiveEngineering")){
    		try {
    			System.out.println("chungweenisore");
    			TeloLogger.info("Loading compatability for immersive engineering");
    			//TODO make this configurable
    			DieselHandler.registerFuel(IEContent.fluidBiodiesel, 1000); //can't go higher than 1000 since it must drain at least 1 mB/s
    			DieselHandler.registerFuel(IEContent.fluidEthanol, 200);
    			DieselHandler.registerFuel(IEContent.fluidPlantoil, 200);
    			DieselHandler.registerFuel(TFCFluids.OLIVEOIL, 200);
    			DieselHandler.registerFuel(IEContent.fluidCreosote, 20);
    		}
    		catch(Exception e) {
    			TeloLogger.error("compatability for immersive engineering failed with exception: "+ e.getMessage());
    		}
    	}
    	else {
    		System.out.println("notchungweenisore");
    	}
    }
}
