package com.facetorched.teloaddon.handlers;

import com.dunk.tfc.api.Crafting.AnvilManager;
import com.facetorched.teloaddon.TeloItemSetup;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.event.world.WorldEvent;

public class TeloChunkEventHandler {
	@SubscribeEvent
	public void onLoadWorld(WorldEvent.Load event)
	{
		if (!event.world.isRemote && event.world.provider.dimensionId == 0
				&& AnvilManager.getInstance().getRecipeList().size() != 0)
		{
			TeloItemSetup.registerAnvilRecipes();
		}
	}
}
