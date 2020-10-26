package com.facetorched.teloaddon;

import com.dunk.tfc.ItemSetup;
import com.dunk.tfc.Core.FluidBaseTFC;
import com.dunk.tfc.TileEntities.TEHopper;
import com.dunk.tfc.api.TFCFluids;
import com.dunk.tfc.api.TFCItems;
import com.facetorched.teloaddon.util.Config;

import cpw.mods.fml.common.Loader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TeloFluidSetup {
	public static Fluid hydrofluoricAcid;
	public static Fluid nitricAcid;
	public static Fluid glycerol;
	public static Fluid nitroglycerin;

	public static void setup() {
		if (Config.addFluids) {
			hydrofluoricAcid = registryHelper(new FluidBaseTFC("hydrofluoricacid"),0xc19fe4,TeloItemSetup.hydrofluoricAcidBottle).setTemperature(386); //can't go in barrels
			nitricAcid = registryHelper(new FluidBaseTFC("nitricacid"),0xeabb4d,TeloItemSetup.nitricAcidBottle).setTemperature(386); //can't go in barrels
			glycerol = registryHelper(new FluidBaseTFC("glycerol"),0xe9ece9,TeloItemSetup.glycerolBottle);
			nitroglycerin = registryHelper(new FluidBaseTFC("nitroglycerin"),0xf1d5ce,TeloItemSetup.nitroglycerinBottle);
		}
		if (Config.moreOil && !(Config.plantOilIE && Loader.isModLoaded("ImmersiveEngineering"))) {
			TEHopper.registerPressableItem(TFCItems.coconutMeat,TFCFluids.OLIVEOIL,0.64f,10);
			TEHopper.registerPressableItem(TFCItems.soybean,TFCFluids.OLIVEOIL,0.64f,10);
		}
	}
	public static Fluid registryHelper(FluidBaseTFC fluid, int color, Item fullBottle) {
		fluid.setBaseColor(color);
		FluidRegistry.registerFluid(fluid);
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, 250), new ItemStack(fullBottle),new ItemStack(ItemSetup.glassBottle));
		return fluid;
	}
	public static Fluid registryHelper(FluidBaseTFC fluid, int color) {
		fluid.setBaseColor(color);
		FluidRegistry.registerFluid(fluid);
		return fluid;
	}
}
