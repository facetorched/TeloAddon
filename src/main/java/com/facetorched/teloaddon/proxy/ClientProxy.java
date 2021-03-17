package com.facetorched.teloaddon.proxy;

import com.dunk.tfc.Render.Item.CompositeBowItemRenderer;
import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.handlers.ClientEventHandler;
import com.facetorched.teloaddon.handlers.MouseEventHandler;
import com.facetorched.teloaddon.handlers.TeloRenderPlayerHandler;
import com.facetorched.teloaddon.render.ItemRenderChainsaw;
import com.facetorched.teloaddon.render.TeloTESRIngotPile;
import com.facetorched.teloaddon.tileentities.TeloTEIngotPile;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy implements IProxy {

	@Override
    public void preInit(FMLPreInitializationEvent event)
    {
        // DEBUG
        //System.out.println("on Client side");
        ClientRegistry.registerTileEntity(TeloTEIngotPile.class, "teloIngotPile", new TeloTESRIngotPile()); //register the TileEntitySpecialRenderer on client
        MinecraftForgeClient.registerItemRenderer(TeloItemSetup.compoundBow, new CompositeBowItemRenderer());
        //Minecraft.getMinecraft().mouseHelper = ClientProxy.mouseHelperAI;
        //register entity renderer here
        MinecraftForge.EVENT_BUS.register(new TeloRenderPlayerHandler());
        MinecraftForge.EVENT_BUS.register(new MouseEventHandler());
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
    	if(TeloItemSetup.chainsaw != null) {
        	MinecraftForgeClient.registerItemRenderer(TeloItemSetup.chainsaw, new ItemRenderChainsaw());
        }
        // DEBUG
        //System.out.println("on Client side");
    }

}
