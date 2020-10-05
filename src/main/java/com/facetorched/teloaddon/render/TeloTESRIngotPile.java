package com.facetorched.teloaddon.render;

import org.lwjgl.opengl.GL11;

import com.dunk.tfc.Blocks.BlockIngotPile;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.Render.Models.ModelIngotPile;
import com.dunk.tfc.Render.TESR.TESRIngotPile;
import com.dunk.tfc.TileEntities.TEIngotPile;
import com.facetorched.teloaddon.TeloBlockSetup;

import net.minecraft.block.Block;
import net.minecraft.util.ResourceLocation;

public class TeloTESRIngotPile extends TESRIngotPile{
	private final ModelIngotPile ingotModel = new ModelIngotPile();
	
	public void renderTileEntityIngotPileAt(TEIngotPile tep, double d, double d1, double d2, float f)
	{
		Block block = tep.getBlockType();
		if (tep.getWorldObj() != null && tep.getStackInSlot(0) != null && block == TeloBlockSetup.ingotPile)
		{
			int i = ((BlockIngotPile) block).getStack(tep.getWorldObj(), tep);
			TFC_Core.bindTexture(new ResourceLocation("teloaddon", "textures/blocks/metal/" + tep.type + ".png")); //texture

			GL11.glPushMatrix(); //start
			GL11.glTranslatef((float)d + 0.0F, (float)d1 + 0F, (float)d2 + 0.0F); //size
			ingotModel.renderIngots(i);
			GL11.glPopMatrix(); //end
		}
	}
}
