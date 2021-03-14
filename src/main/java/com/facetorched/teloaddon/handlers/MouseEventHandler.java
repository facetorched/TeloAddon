package com.facetorched.teloaddon.handlers;

import com.dunk.tfc.Handlers.Network.AbstractPacket;
import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.TeloMod;
import com.facetorched.teloaddon.handlers.network.LeftMousePacket;
import com.facetorched.teloaddon.util.ChainsawNBTHelper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.MouseEvent;

public class MouseEventHandler {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onMouse(MouseEvent event) {
		if (event.button == 0 && TeloItemSetup.chainsaw != null) {
			ItemStack held = Minecraft.getMinecraft().thePlayer.getHeldItem();
			if(ChainsawNBTHelper.setIsChainsawRunning(held, Minecraft.getMinecraft().theWorld, event.buttonstate)){
				AbstractPacket pkt = new LeftMousePacket(event.buttonstate);
				TeloMod.packetPipeline.sendToServer(pkt);
			}
		}
	}
}
