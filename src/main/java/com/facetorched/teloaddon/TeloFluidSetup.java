package com.facetorched.teloaddon;

import com.dunk.tfc.Core.FluidBaseTFC;
import com.dunk.tfc.TileEntities.TEHopper;
import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.TFCItems;
import com.facetorched.teloaddon.util.Config;

import cpw.mods.fml.common.Loader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class TeloFluidSetup {
	public static Fluid hydrofluoricAcid;
	public static Fluid nitricAcid;
	public static Fluid glycerol;
	public static Fluid nitroglycerin;
	public static Fluid teloCreosote;

	public static void setup() {
		if (Config.addFluids) {
			hydrofluoricAcid = registryHelper(new FluidBaseTFC("hydrofluoricacid"),0xc19fe4).setTemperature(386); //can't go in barrels
			nitricAcid = registryHelper(new FluidBaseTFC("nitricacid"),0xeabb4d).setTemperature(386); //can't go in barrels
			glycerol = registryHelper(new FluidBaseTFC("glycerol"),0xe9ece9);
			nitroglycerin = registryHelper(new FluidBaseTFC("nitroglycerin"),0xf1d5ce);
		}
		if (Config.moreOil && !(Config.plantOilIE && Loader.isModLoaded("ImmersiveEngineering"))) {
			TEHopper.registerPressableItem(TFCItems.coconutMeat,TFCFluids.OLIVEOIL,0.64f,10);
			TEHopper.registerPressableItem(TFCItems.soybean,TFCFluids.OLIVEOIL,0.64f,10);
		}
		if(Config.addCreosoteFluid) {
			teloCreosote = registryHelper(new FluidBaseTFC("telocreosote"));
		}
	}
	public static Fluid registryHelper(FluidBaseTFC fluid, int color) {
		fluid.setBaseColor(color);
		FluidRegistry.registerFluid(fluid);
		return fluid;
	}
	public static Fluid registryHelper(FluidBaseTFC fluid) {
		FluidRegistry.registerFluid(fluid);
		return fluid;
	}
}
