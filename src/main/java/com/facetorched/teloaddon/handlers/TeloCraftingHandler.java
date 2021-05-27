package com.facetorched.teloaddon.handlers;

import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.items.ItemChainsaw;
import com.facetorched.teloaddon.util.Config;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import net.minecraft.item.ItemStack;

public class TeloCraftingHandler {
	@SubscribeEvent
	public void onCrafting(PlayerEvent.ItemCraftedEvent e) {
		if(Config.preventFoodDupe) {
			ItemStack is = e.crafting;
			if(is != null && is.hasTagCompound() && is.getTagCompound().hasKey("foodWeight")) {
				if(is.getTagCompound().getFloat("foodWeight") > 161.0f) { //slight buffer
					is.getTagCompound().setFloat("foodDecay", 10000.0f);
	    		
	    		//System.out.println(is.getTagCompound());
				}
			}
		}
		// crafting the chainsaw should retain any charge! (super hacky but whatever, I want recipes from minetweaker)
		if(TeloItemSetup.chainsaw != null && e.crafting.getItem() == TeloItemSetup.chainsaw) {
			ItemChainsaw chainsaw = (ItemChainsaw)(TeloItemSetup.chainsaw);
			Integer energy = null;
			int size = e.craftMatrix.getSizeInventory();
			for(int i = 0; i<size; i++) {
				ItemStack is = e.craftMatrix.getStackInSlot(i);
				if(is != null && is.getItem() == TeloItemSetup.chainsaw) {
					energy = chainsaw.getEnergyStored(is);
				}
			}
			if(energy != null) {
				chainsaw.receiveEnergy(e.crafting, energy, false);
			}
		}
	}
}
