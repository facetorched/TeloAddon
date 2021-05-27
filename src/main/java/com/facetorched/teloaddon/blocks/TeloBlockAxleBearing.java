package com.facetorched.teloaddon.blocks;

import com.dunk.tfc.Blocks.Devices.BlockAxleBearing;
import com.facetorched.teloaddon.tileentities.TeloTEAxleBearing;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TeloBlockAxleBearing extends BlockAxleBearing{

	public TeloBlockAxleBearing(Material m) {
		super(m);
		// TODO Auto-generated constructor stub
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TeloTEAxleBearing();
	}
}
