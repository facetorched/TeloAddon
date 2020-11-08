package com.facetorched.teloaddon;

import net.minecraftforge.common.MinecraftForge;

import com.facetorched.teloaddon.handlers.BlockDropHandler;
import com.facetorched.teloaddon.items.TeloItemHeat;
import com.facetorched.teloaddon.proxy.IProxy;
import com.facetorched.teloaddon.util.Config;
import com.facetorched.teloaddon.util.compat.ImmersiveEngineering;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

@Mod(modid = TeloMod.MODID, version = TeloMod.VERSION, dependencies = "required-after:terrafirmacraftplus;after:ImmersiveEngineering;")
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
    	Config.preInit(event.getModConfigurationDirectory());
    	Config.reload();
    	TeloFluidSetup.setup();
    	TeloItemSetup.setup();
    	TeloBlockSetup.loadBlocks();
    	TeloBlockSetup.registerBlocks();
    	ImmersiveEngineering.preInit();
    	proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) //build data structures and register network handlers
    {
    	MinecraftForge.EVENT_BUS.register(new BlockDropHandler());
    	TeloItemHeat.setupItemHeat();
    	Config.reloadOres();
    	proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	ImmersiveEngineering.postInit();
    	
    	proxy.postInit(event);
    }
    
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if (eventArgs.modID.equalsIgnoreCase(MODID));
		Config.reload();
	}
}
