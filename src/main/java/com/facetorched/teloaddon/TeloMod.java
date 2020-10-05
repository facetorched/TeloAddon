package com.facetorched.teloaddon;

import net.minecraftforge.common.MinecraftForge;

import com.facetorched.teloaddon.handlers.BlockDropHandler;
import com.facetorched.teloaddon.proxy.IProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = TeloMod.MODID, version = TeloMod.VERSION, dependencies = "required-after:terrafirmacraftplus;after:immersiveengineering;")
public class TeloMod
{
    public static final String MODID = "teloaddon";
    public static final String VERSION = "1.0";
    
    @SidedProxy(
    	      clientSide="com.facetorched.teloaddon.proxy.ClientProxy", 
    	      serverSide="com.facetorched.teloaddon.proxy.ServerProxy"
    	    )
    public static IProxy proxy;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) // register blocks, items etc
    {
    	TeloItemSetup.setup();
    	TeloBlockSetup.setup();
    	proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) //build data structures and register network handlers
    {
    	MinecraftForge.EVENT_BUS.register(new BlockDropHandler());
    	proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	proxy.postInit(event);
    }
    
}
