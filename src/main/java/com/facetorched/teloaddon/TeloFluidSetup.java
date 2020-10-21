package com.facetorched.teloaddon;

import com.dunk.tfc.ItemSetup;
import com.dunk.tfc.Core.FluidBaseTFC;
import com.facetorched.teloaddon.util.Config;

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
			hydrofluoricAcid = registryHelper(new FluidBaseTFC("hydrofluoricacid"),0xc19fe4,TeloItemSetup.hydrofluoricAcidBottle);
			nitricAcid = registryHelper(new FluidBaseTFC("nitricacid"),0xeabb4d,TeloItemSetup.nitricAcidBottle);
			glycerol = registryHelper(new FluidBaseTFC("glycerol"),0xe9ece9,TeloItemSetup.glycerolBottle);
			nitroglycerin = registryHelper(new FluidBaseTFC("nitroglycerin"),0xf1d5ce,TeloItemSetup.nitroglycerinBottle);
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
