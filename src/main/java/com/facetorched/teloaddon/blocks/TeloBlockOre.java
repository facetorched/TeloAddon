package com.facetorched.teloaddon.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.dunk.tfc.Blocks.Terrain.BlockOre;
import com.dunk.tfc.Core.TFC_Core;
import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.TeloMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class TeloBlockOre extends BlockOre{
	public String[] telo_ores = {"Bauxite"};
	public TeloBlockOre(Material mat)
	{
		super(mat);
		blockNames = telo_ores;
		super.icons = new IIcon[blockNames.length];
	}
	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < blockNames.length; i++)
		{
			icons[i] = iconRegisterer.registerIcon(TeloMod.MODID + ":ores/"+ blockNames[i] + "_Ore");
		}
	}
	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			boolean dropOres = false;
			int meta = world.getBlockMetadata(x, y, z);
			ItemStack itemstack = null;
			if(player != null)
			{
				TFC_Core.addPlayerExhaustion(player, 0.001f);
				player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
				dropOres = player.canHarvestBlock(this);
			}
			if (player == null || dropOres)
			{
				itemstack = new ItemStack(getDroppedItem(meta));
			}
			if (itemstack != null)
				dropBlockAsItem(world, x, y, z, itemstack);
		}
		return world.setBlockToAir(x, y, z);
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		int count = quantityDropped(metadata, fortune, world.rand);
		for (int i = 0; i < count; i++)
		{
			ItemStack itemstack;
			itemstack = new ItemStack(getDroppedItem(metadata));
			ret.add(itemstack);
		}
		return ret;
	}

	public Item getDroppedItem(int meta)
	{
		switch(meta) {
		case 0:
			return(TeloItemSetup.bauxiteOre);
		}
		return null;
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion exp)
	{
		if(!world.isRemote)
		{
			ItemStack itemstack;
			int meta = world.getBlockMetadata(x, y, z);
			itemstack = new ItemStack(getDroppedItem(meta));
			dropBlockAsItem(world, x, y, z, itemstack);
			onBlockDestroyedByExplosion(world, x, y, z, exp);
		}
	}
}
