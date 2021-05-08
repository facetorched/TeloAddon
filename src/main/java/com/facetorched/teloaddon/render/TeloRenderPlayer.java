package com.facetorched.teloaddon.render;

import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.util.ChainsawNBTHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderPlayer;

@SideOnly(Side.CLIENT)
public class TeloRenderPlayer extends RenderPlayer{
	@Override
	public void doRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if(TeloItemSetup.chainsaw != null && ChainsawNBTHelper.isChainsawRunning(entity.getHeldItem())){
			this.modelBipedMain.aimedBow = true;
		}
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
}
