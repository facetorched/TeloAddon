package com.facetorched.teloaddon.proxy;

import com.facetorched.teloaddon.tileentities.TeloTEIngotPile;

import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
public class ServerProxy implements IProxy {

	@Override
	public void preInit(FMLPreInitializationEvent event) {
		GameRegistry.registerTileEntity(TeloTEIngotPile.class, "teloIngotPile"); //register tile entity on the server
	}

	@Override
	public void init(FMLInitializationEvent event) {
		//This probably will stay empty

	}

	@Override
	public void postInit(FMLPostInitializationEvent event) {
		//register server commands?
	}
}
