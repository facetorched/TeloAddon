package com.facetorched.teloaddon.blocks;

import java.util.ArrayList;
import java.util.Random;

import com.dunk.tfc.TerraFirmaCraft;
import com.dunk.tfc.Blocks.Terrain.BlockOre;
import com.dunk.tfc.Core.TFC_Core;
import com.dunk.tfc.TileEntities.TEOre;
import com.dunk.tfc.api.TFCOptions;
import com.facetorched.teloaddon.TeloMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class TeloBlockOre extends BlockOre{
	public Item droppedItem;
	public TeloBlockOre(Material mat)
	{
		super(mat);
		//blockNames = TeloMod.teloOres;
		//super.icons = new IIcon[blockNames.length];
	}
	public TeloBlockOre teloSetHarvestLevel(String toolClass, int level) {
		this.setHarvestLevel(toolClass, level);
		return this;
	}
	public TeloBlockOre setDroppedItem(Item item) {
		droppedItem = item;
		return this;
	}
	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random)
	{
		return 1;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		icons[0] = iconRegisterer.registerIcon(TeloMod.MODID + ":ores/"+ getUnlocalizedName().substring(5));
		/*
		for(int i = 0; i < blockNames.length; i++)
		{
			icons[i] = iconRegisterer.registerIcon(TeloMod.MODID + ":ores/"+ blockNames[i] + "_Ore");
			
		}*/
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
				TEOre te = (TEOre) world.getTileEntity(x, y, z);
				TerraFirmaCraft.LOG.info("Ore  extraData = " + te.extraData + "grade" +getOreGrade(te,meta));
				itemstack = new ItemStack(this.droppedItem, 1, getOreGrade(te,meta));
				
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
		TEOre te = (TEOre) world.getTileEntity(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		int count = quantityDropped(metadata, fortune, world.rand);
		for (int i = 0; i < count; i++)
		{
			ItemStack itemstack;
			TerraFirmaCraft.LOG.info("Ore  extraData = " + te.extraData + "grade" +getOreGrade(te,meta));
			itemstack = new ItemStack(this.droppedItem, 1, getOreGrade(te,meta));
			ret.add(itemstack);
		}
		return ret;
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion exp)
	{
		if(!world.isRemote)
		{
			ItemStack itemstack;
			TEOre te = (TEOre) world.getTileEntity(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			itemstack = new ItemStack(this.droppedItem, 1, getOreGrade(te,meta));
			dropBlockAsItem(world, x, y, z, itemstack);
			onBlockDestroyedByExplosion(world, x, y, z, exp);
		}
	}
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if(TFCOptions.enableDebugMode && !world.isRemote)
		{
			TEOre te = (TEOre)world.getTileEntity(x, y, z);
			int meta = world.getBlockMetadata(x, y, z);
			if(te != null) {
				if (entityplayer.isSneaking())
					te.extraData++;
				TerraFirmaCraft.LOG.info("Ore  extraData = " + te.extraData + ", Grade = " +getOreGrade(te,meta));

			}
		}
		return super.onBlockActivated(world,x,y,z,entityplayer,par6,par7,par8,par9);
	}
}
