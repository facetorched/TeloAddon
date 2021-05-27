package com.facetorched.teloaddon;

import com.facetorched.teloaddon.handlers.BlockDropHandler;
import com.facetorched.teloaddon.handlers.TeloChunkEventHandler;
import com.facetorched.teloaddon.handlers.TeloCraftingHandler;
import com.facetorched.teloaddon.handlers.network.TeloPacketPipeline;
import com.facetorched.teloaddon.items.TeloItemHeat;
import com.facetorched.teloaddon.proxy.IProxy;
import com.facetorched.teloaddon.util.Config;
import com.facetorched.teloaddon.util.compat.ImmersiveEngineering;

import cpw.mods.fml.client.event.ConfigChangedEvent;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = TeloMod.MODID, version = TeloMod.VERSION, dependencies = "required-after:terrafirmacraftplus;after:ImmersiveEngineering;")
public class TeloMod
{
    public static final String MODID = "teloaddon";
    public static final String VERSION = "1.0.5";
    
    @SidedProxy(
    	      clientSide="com.facetorched.teloaddon.proxy.ClientProxy", 
    	      serverSide="com.facetorched.teloaddon.proxy.ServerProxy"
    	    )
    public static IProxy proxy;
    public static TeloPacketPipeline packetPipeline = new TeloPacketPipeline();
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) // register blocks, items etc
    {
    	Config.preInit(event.getModConfigurationDirectory());
    	Config.reload();
    	TeloFluidSetup.setup();
    	TeloItemSetup.setup();
    	TeloBlockSetup.loadBlocks();
    	TeloBlockSetup.registerBlocks();
    	if(Loader.isModLoaded("ImmersiveEngineering"))
    		ImmersiveEngineering.preInit();
    	proxy.preInit(event);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) //build data structures and register network handlers
    {
    	MinecraftForge.EVENT_BUS.register(new BlockDropHandler());
    	MinecraftForge.EVENT_BUS.register(new TeloChunkEventHandler());
    	FMLCommonHandler.instance().bus().register(new TeloCraftingHandler());
    	
    	TeloItemHeat.setupItemHeat();
    	Config.reloadOres();
    	if(Loader.isModLoaded("ImmersiveEngineering"))
    		ImmersiveEngineering.init();
    	proxy.init(event);
    	packetPipeline.initalise();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
    	if(Loader.isModLoaded("ImmersiveEngineering"))
    		ImmersiveEngineering.postInit();
    	
    	proxy.postInit(event);
    	packetPipeline.postInitialise();
    }
    
	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
	{
		if (eventArgs.modID.equalsIgnoreCase(MODID));
		Config.reload();
	}
}
