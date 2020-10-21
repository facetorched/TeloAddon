package com.facetorched.teloaddon.items;

import com.dunk.tfc.api.HeatIndex;

import net.minecraft.item.ItemStack;

import com.dunk.tfc.api.HeatRaw;
import com.dunk.tfc.api.HeatRegistry;
import com.facetorched.teloaddon.TeloItemSetup;

public class TeloItemHeat {
	public static void setupItemHeat()
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		/**
		 * Heat now increases at a base rate of 1C per tick. Specific heat is now just a multiplier on this rate. 
		 * This means that a metlTemp of 100C will be reached in 5 seconds with a Specific Heat of 1.0 and 10 seconds at 2.0
		 */

		HeatRaw aluminumRaw = new HeatRaw(0.9, 660);
		
		manager.addIndex(new HeatIndex(new ItemStack(TeloItemSetup.bauxiteOre,1,0), aluminumRaw,new ItemStack(TeloItemSetup.aluminumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TeloItemSetup.aluminumIngot,1), aluminumRaw,new ItemStack(TeloItemSetup.aluminumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TeloItemSetup.aluminumIngot2x,1), aluminumRaw,new ItemStack(TeloItemSetup.aluminumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TeloItemSetup.aluminumUnshaped,1), aluminumRaw,new ItemStack(TeloItemSetup.aluminumUnshaped,1)));
		manager.addIndex(new HeatIndex(new ItemStack(TeloItemSetup.aluminumSheet,1), aluminumRaw,new ItemStack(TeloItemSetup.aluminumUnshaped,2,0)));
		manager.addIndex(new HeatIndex(new ItemStack(TeloItemSetup.aluminumSheet2x,1), aluminumRaw,new ItemStack(TeloItemSetup.aluminumUnshaped,4,0)));
	}
}
