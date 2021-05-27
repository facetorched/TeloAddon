package com.facetorched.teloaddon.blocks;

import com.dunk.tfc.Blocks.Devices.BlockAxleBearing;
import com.facetorched.teloaddon.tileentities.TEWindmillBearing;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWindmillBearing extends BlockAxleBearing{

	public BlockWindmillBearing(Material m) {
		super(m);
		// TODO Auto-generated constructor stub
	}
	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TEWindmillBearing();
	}
	//@Override
	//public void onBlockPreDestroy(World world, int x, int y, int z, int oldMeta)
	//{
		// override dropping an axle on destroy
	//}
}
