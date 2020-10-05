package com.facetorched.teloaddon.optionalcompat;

import blusunrize.immersiveengineering.common.IEContent;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import net.minecraft.item.ItemStack;

public class ImmersiveEngineering {

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	if(Loader.isModLoaded("immersiveengineering")){
    		try {
    			new ItemStack(IEContent.blockMetalDecoration);
    		}
    		catch(Exception e) {}
    	}
    }
}
