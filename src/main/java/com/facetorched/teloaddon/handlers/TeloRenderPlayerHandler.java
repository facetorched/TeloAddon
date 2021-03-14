package com.facetorched.teloaddon.handlers;

import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.util.ChainsawNBTHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.client.event.RenderPlayerEvent;

public class TeloRenderPlayerHandler {
	@SubscribeEvent
	public void onPlayerRender(RenderPlayerEvent.Post event) {
		if(TeloItemSetup.chainsaw != null && ChainsawNBTHelper.isChainsawRunning(event.entityPlayer.getHeldItem())){
			event.renderer.modelBipedMain.aimedBow = true;
		}
	}
}
