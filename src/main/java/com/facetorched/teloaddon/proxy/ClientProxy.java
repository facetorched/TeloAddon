package com.facetorched.teloaddon.proxy;

import com.dunk.tfc.Render.Item.CompositeBowItemRenderer;
import com.dunk.tfc.Render.TESR.TESRAxleBearing;
import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.handlers.ClientEventHandler;
import com.facetorched.teloaddon.handlers.MouseEventHandler;
import com.facetorched.teloaddon.handlers.TeloRenderPlayerHandler;
import com.facetorched.teloaddon.render.ItemRenderChainsaw;
import com.facetorched.teloaddon.render.TeloRenderPlayer;
import com.facetorched.teloaddon.render.TeloTESRIngotPile;
import com.facetorched.teloaddon.tileentities.TEWindmillBearing;
import com.facetorched.teloaddon.tileentities.TeloTEIngotPile;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy implements IProxy {

	@Override
    public void preInit(FMLPreInitializationEvent event)
    {
        // DEBUG
        //System.out.println("on Client side");
        ClientRegistry.registerTileEntity(TeloTEIngotPile.class, "teloIngotPile", new TeloTESRIngotPile()); //register the TileEntitySpecialRenderer on client
        ClientRegistry.registerTileEntity(TEWindmillBearing.class, "windmillBearing", new TESRAxleBearing());
        MinecraftForgeClient.registerItemRenderer(TeloItemSetup.compoundBow, new CompositeBowItemRenderer());
        //RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new TeloRenderPlayer());
        MinecraftForge.EVENT_BUS.register(new MouseEventHandler());
        //Minecraft.getMinecraft().mouseHelper = ClientProxy.mouseHelperAI;
        //register entity renderer here
        //FMLCommonHandler.instance().bus().register(new TeloRenderPlayerHandler());
        TeloRenderPlayerHandler pRHandler = new TeloRenderPlayerHandler();
		MinecraftForge.EVENT_BUS.register(pRHandler);
		FMLCommonHandler.instance().bus().register(pRHandler);
        //MinecraftForge.EVENT_BUS.register(new WorldRenderHandler());
        
    }

    @Override
    public void init(FMLInitializationEvent event)
    {
        // DEBUG
    	MinecraftForge.EVENT_BUS.register(new ClientEventHandler());
        //System.out.println("on Client side");

        // register key bindings
    }

    @Override
    public void postInit(FMLPostInitializationEvent event)
    {
    	// needs to register after items have been registered
    	if(TeloItemSetup.chainsaw != null) {
        	MinecraftForgeClient.registerItemRenderer(TeloItemSetup.chainsaw, new ItemRenderChainsaw());
        }
        // DEBUG
        //System.out.println("on Client side");
    }

}
