package com.facetorched.teloaddon.util.compat;

import java.util.HashMap;

import com.dunk.tfc.TileEntities.TEHopper;
import com.dunk.tfc.api.TFCItems;
import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.util.Config;
import com.facetorched.teloaddon.util.TeloLogger;

import blusunrize.immersiveengineering.api.energy.DieselHandler;
import blusunrize.immersiveengineering.common.IEContent;
import cpw.mods.fml.common.Loader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class ImmersiveEngineering {

    public static void load() {
    	//detect if IE is loaded
    	if(Loader.isModLoaded("ImmersiveEngineering")){
    		try {
    			System.out.println("chungweenisore");
    			TeloLogger.info("Loading compatability for immersive engineering");
    			//TODO make this configurable
    			//DieselHandler.registerFuel(IEContent.fluidBiodiesel, 1000); //can't go higher than 1000 since it must drain at least 1 mB/s
    			//DieselHandler.registerFuel(IEContent.fluidEthanol, 200);
    			//DieselHandler.registerFuel(IEContent.fluidPlantoil, 200);
    			//DieselHandler.registerFuel(TFCFluids.OLIVEOIL, 200);
    			//DieselHandler.registerFuel(IEContent.fluidCreosote, 20);
    			HashMap<String,Integer> fuels = Config.dieselGeneratorFuelsMap;
    			for(String key : fuels.keySet()) {
    				DieselHandler.registerFuel(FluidRegistry.getFluid(key),fuels.get(key));
    			}
    			
    			
    			if (Config.plantOilIE) {
    				TEHopper.registerPressableItem(TFCItems.coconutMeat,IEContent.fluidPlantoil,0.64f,10);
    				TEHopper.registerPressableItem(TFCItems.soybean,IEContent.fluidPlantoil,0.64f,10);
    			}
    			
    			TeloItemSetup.setupIE();
    			/*
    			registerContainerHelper(IEContent.fluidBiodiesel, 250, TeloItemSetup.biodieselBottle);
    			registerContainerHelper(IEContent.fluidPlantoil, 250, TeloItemSetup.plantOilBottle);
    			registerContainerHelper(IEContent.fluidEthanol, 250, TeloItemSetup.ethanolBottle);
    			registerContainerHelper(IEContent.fluidCreosote, 250, TeloItemSetup.creosoteBottle);

    			registerContainerHelper(IEContent.fluidBiodiesel, 1000, TeloItemSetup.biodieselWoodenBucket);
    			registerContainerHelper(IEContent.fluidPlantoil, 1000, TeloItemSetup.plantOilWoodenBucket);
    			registerContainerHelper(IEContent.fluidEthanol, 1000, TeloItemSetup.ethanolWoodenBucket);
    			registerContainerHelper(IEContent.fluidCreosote, 1000, TeloItemSetup.creosoteWoodenBucket);
    			
    			registerContainerHelper(IEContent.fluidBiodiesel, 1000, TeloItemSetup.biodieselCeramicBucket);
    			registerContainerHelper(IEContent.fluidPlantoil, 1000, TeloItemSetup.plantOilCeramicBucket);
    			registerContainerHelper(IEContent.fluidEthanol, 1000, TeloItemSetup.ethanolCeramicBucket);
    			registerContainerHelper(IEContent.fluidCreosote, 1000, TeloItemSetup.creosoteCeramicBucket);
    			*/
    			//System.out.println("poggers"+IEContent.fluidBiodiesel.getIcon().getIconName());
    		}
    		catch(Exception e) {
    			TeloLogger.error("compatability for immersive engineering failed with exception: "+ e.getMessage());
    		}
    	}
    	else {
    		TeloLogger.info("Immersive Engineering was not detected");
    	}
    }
    public static void registerContainerHelper(Fluid fluid, int amount,Item fullContainer) {
    	FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, amount), new ItemStack(fullContainer),new ItemStack(fullContainer.getContainerItem()));
    }
}
