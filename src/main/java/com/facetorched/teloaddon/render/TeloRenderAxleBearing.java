package com.facetorched.teloaddon.render;

import com.dunk.tfc.Blocks.BlockWoodSupport;
import com.dunk.tfc.Blocks.Devices.BlockAxleBearing;
import com.dunk.tfc.Render.RenderBlocksWithRotation;
import com.dunk.tfc.Render.Blocks.RenderAxleBearing;
import com.dunk.tfc.api.TFCBlocks;

import blusunrize.immersiveengineering.common.IEContent;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

public class TeloRenderAxleBearing extends RenderAxleBearing
{
	static boolean enableBrightness = false;


	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{

		//forward
		renderer.overrideBlockTexture = IEContent.blockTreatedWood.getIcon(0, 0);
		
		renderer.setRenderBounds(0.1F, 0F, 0.2F, 0.9F, 0.4F, 0.8F);
		renderInvBlock(block, renderer);
		//mid
		//renderer.clearOverrideBlockTexture();
		renderer.setRenderBounds(0.1F, 0.4F, 0.2F, 0.2F, 0.9F, 0.8F);
		renderInvBlock(block, renderer);

		renderer.setRenderBounds(0.8F, 0.4F, 0.2F, 0.9F, 0.9F, 0.8F);
		renderInvBlock(block, renderer);
		//back
		//renderer.overrideBlockTexture = BlockBellows.bellowsBack;
		renderer.setRenderBounds(0.1F, 0.9F, 0.2, 0.9F, 1F, 0.8F);
		renderInvBlock(block, renderer);
		
		renderer.clearOverrideBlockTexture();
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer)
	{
		if (!(block instanceof BlockAxleBearing))
		{
			return true;
		}
		int meta = world.getBlockMetadata(x, y, z);
		int direction = BlockAxleBearing.getDirectionFromMetadata(meta);
		//renderer = new RenderBlocksWithRotation(renderer);
		//renderer.renderAllFaces = true;

		renderer.overrideBlockTexture = IEContent.blockTreatedWood.getIcon(0, 0);
		renderer.setRenderAllFaces(true);
		if (direction == 0)
		{
			renderer.setRenderBounds(0.25, 0, 0.2, 0.75, 0.3, 0.8);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.25, 0.7, 0.2, 0.75, 0.8, 0.8);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.25, 0.3, 0.2, 0.75, 0.7, 0.3);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.25, 0.3, 0.7, 0.75, 0.7, 0.8);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

		}
		else if (direction == 1)
		{
			renderer.setRenderBounds(0.2, 0, 0.25, 0.8, 0.3, 0.75);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.2, 0.7, 0.25, 0.8, 0.8, 0.75);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.2, 0.3, 0.25, 0.3, 0.7, 0.75);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.7, 0.3, 0.25, 0.8, 0.7, 0.75);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);
		}
		else if (direction == 2)
		{
			boolean b1 = world.getBlock(x, y, z + 1) instanceof BlockWoodSupport;
			boolean b2 = world.getBlock(x, y, z - 1) instanceof BlockWoodSupport;

			renderer.setRenderBounds(0.2, 0.25, 0, 0.8, 0.75, 0.3);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.2, 0.25, 0.7, 0.8, 0.75, 1);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.2, 0.25, 0.3, 0.3, 0.75, 0.7);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.7, 0.25, 0.3, 0.8, 0.75, 0.7);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			if (b1 || b2)
			{
				RenderBlocks oldRenderer = renderer;
				renderer = new RenderBlocksWithRotation(renderer);
				renderer.overrideBlockTexture = IEContent.blockTreatedWood.getIcon(0, 0);
				renderer.setRenderAllFaces(true);
				((RenderBlocksWithRotation) renderer).staticTexture = true;
				if (b2)
				{
					renderer.setRenderBounds(0.25, 0.25, -0.25, 0.75, 0.75, 0);
					renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);
				}
				if (b1)
				{
					renderer.setRenderBounds(0.25, 0.25, 1, 0.75, 0.75, 1.25);
					renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);
				}
				((RenderBlocksWithRotation) renderer).staticTexture = false;
				renderer.setRenderAllFaces(false);
				((RenderBlocksWithRotation) renderer).clearOverrideBlockTexture();
				renderer = oldRenderer;
			}
		}
		else if (direction == 3)
		{
			boolean b1 = world.getBlock(x + 1, y, z) instanceof BlockWoodSupport;
			boolean b2 = world.getBlock(x - 1, y, z) instanceof BlockWoodSupport;

			renderer.setRenderBounds(0, 0.25, 0.2, 0.3, 0.75, 0.8);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.7, 0.25, 0.2, 1, 0.75, 0.8);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.3, 0.25, 0.2, 0.7, 0.75, 0.3);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			renderer.setRenderBounds(0.3, 0.25, 0.7, 0.7, 0.75, 0.8);
			renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

			if (b1 || b2)
			{
				RenderBlocks oldRenderer = renderer;
				renderer = new RenderBlocksWithRotation(renderer);
				renderer.overrideBlockTexture = IEContent.blockTreatedWood.getIcon(0, 0);
				renderer.setRenderAllFaces(true);
				((RenderBlocksWithRotation) renderer).staticTexture = true;
				if (b2)
				{
					((RenderBlocksWithRotation) renderer).absoluteOffsetX = -0.25;
					renderer.setRenderBounds(0, 0.25, 0.25, 0.25, 0.75, 0.75);
					renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);

				}
				if (b1)
				{
					((RenderBlocksWithRotation) renderer).absoluteOffsetX = 0.25;
					renderer.setRenderBounds(0.75, 0.25, 0.25, 1, 0.75, 0.75);
					renderer.renderStandardBlock(TFCBlocks.planks, x, y, z);
				}
				((RenderBlocksWithRotation) renderer).staticTexture = false;
				renderer.setRenderAllFaces(false);
				((RenderBlocksWithRotation) renderer).clearOverrideBlockTexture();
				renderer = oldRenderer;
			}
		}
		renderer.renderAllFaces = false;
		renderer.clearOverrideBlockTexture();
		return true;
	}
}

