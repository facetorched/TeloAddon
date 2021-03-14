package com.facetorched.teloaddon.handlers;

import com.dunk.tfc.api.TFCFluids;
import com.facetorched.teloaddon.TeloFluidSetup;
import com.facetorched.teloaddon.TeloMod;
import com.facetorched.teloaddon.util.Config;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.TextureStitchEvent;

public class ClientEventHandler {
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void textureStich(TextureStitchEvent.Pre event)
	{
		if(Loader.isModLoaded("ImmersiveEngineering") && event.map.getTextureType()==0)
		{
			if(Config.cokeOvenPitch) {
				//use our own texture for the pitch inside the coke oven
				TFCFluids.PITCH.setIcons(event.map.registerIcon(TeloMod.MODID+":fluid/pitch"), event.map.registerIcon(TeloMod.MODID+":fluid/pitch"));
			}
			if(Config.addCreosoteFluid) {
				TeloFluidSetup.teloCreosote.setIcons(event.map.registerIcon("immersiveengineering:fluid/creosote_still"), event.map.registerIcon("immersiveengineering:fluid/creosote_flow"));
			}
		}
	}
}
