package com.facetorched.teloaddon.items;

import com.dunk.tfc.Core.Metal.MetalRegistry;
import com.dunk.tfc.Items.ItemIngot;
import com.facetorched.teloaddon.TeloBlockSetup;
import com.facetorched.teloaddon.TeloMod;
import com.facetorched.teloaddon.tileentities.TeloTEIngotPile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

public class TeloItemIngot extends ItemIngot{
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register)
	{
		this.itemIcon = register.registerIcon(TeloMod.MODID+":"+getIconString());
	}
	
	private boolean createPile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, int l)
	{

		boolean fullStack = true;

		TeloTEIngotPile te = null;

		if (world.getTileEntity(x, y, z) instanceof TeloTEIngotPile && world.getBlock(x,y,z) == TeloBlockSetup.ingotPile)
		{
			te = (TeloTEIngotPile)world.getTileEntity(x, y, z);
			if (te.contentsMatch(0,itemstack) && te.getStackInSlot(0).stackSize < te.getInventoryStackLimit())
			{
				fullStack = false;
				te.injectContents(0, 1);
			}
		}
		else{fullStack = true;}

		if(fullStack && isPlaceable(itemstack))
		{
			if(side == 0 && world.isAirBlock(x, y-1, z) && isValid(world, x, y-1, z))
			{
				world.setBlock( x, y-1, z, TeloBlockSetup.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y-1, z);
				}
				te = (TeloTEIngotPile)world.getTileEntity(x, y-1, z);
			}
			else if(side == 1 && world.isAirBlock(x, y+1, z) && isValid(world, x, y+1, z))
			{
				world.setBlock( x, y+1, z, TeloBlockSetup.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y+1, z);
				}
				te = (TeloTEIngotPile)world.getTileEntity(x, y+1, z);
			}
			else if(side == 2 && world.isAirBlock(x, y, z-1) && isValid(world, x, y, z-1))
			{
				world.setBlock( x, y, z-1, TeloBlockSetup.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y, z-1);
				}
				te = (TeloTEIngotPile)world.getTileEntity(x, y, z-1);
			}
			else if(side == 3 && world.isAirBlock(x, y, z+1) && isValid(world, x, y, z+1))
			{
				world.setBlock( x, y, z+1, TeloBlockSetup.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x, y, z+1);
				}
				te = (TeloTEIngotPile)world.getTileEntity(x, y, z+1);
			}
			else if(side == 4 && world.isAirBlock(x-1, y, z) && isValid(world, x-1, y, z))
			{
				world.setBlock( x-1, y, z, TeloBlockSetup.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x-1, y, z);
				}
				te = (TeloTEIngotPile)world.getTileEntity(x-1, y, z);
			}
			else if(side == 5 && world.isAirBlock(x+1, y, z) && isValid(world, x+1, y, z))
			{
				world.setBlock( x+1, y, z, TeloBlockSetup.ingotPile, l, 0x2);
				if(world.isRemote) {
					world.markBlockForUpdate(x+1, y, z);
				}
				te = (TeloTEIngotPile)world.getTileEntity(x+1, y, z);
			}
			else
			{
				return false;
			}

			if(te != null)
			{
				te.storage[0] = new ItemStack(this,1,0);
				te.setType(MetalRegistry.instance.getMetalFromItem(this).name);

				if(entityplayer.capabilities.isCreativeMode)
				{
					te.storage[0] = new ItemStack(this,32,0);
				}
			}
		}		
		return true;
	}
	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		NBTTagCompound stackTagCompound = itemstack.getTagCompound();

		if (entityplayer.isSneaking() &&stackTagCompound == null && itemstack.getItem().getUnlocalizedName().indexOf("Double") == -1 &&
			this.isPlaceable(itemstack))
		{
			int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
			if (!world.isRemote && entityplayer.isSneaking() && (world.getBlock(x, y, z) != TeloBlockSetup.ingotPile || side != 1 && side != 0))
			{

				if(createPile(itemstack, entityplayer, world, x, y, z, side, dir))
				{

					itemstack.stackSize = itemstack.stackSize-1;
					world.addBlockEvent(x,y,z,TeloBlockSetup.ingotPile,0,0);
					return true;

				}
			}
			else if(world.getBlock(x, y, z) == TeloBlockSetup.ingotPile)
			{
				TeloTEIngotPile te = (TeloTEIngotPile)world.getTileEntity(x, y, z);
				//TileEntityIngotPile te2 = (TileEntityIngotPile)Minecraft.getMinecraft().theWorld.getTileEntity(x, y, z);
				if(te != null)
				{
					te.getBlockType().onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
					if(te.storage[0] != null && te.contentsMatch(0,itemstack) && te.storage[0].stackSize < 64) 
					{
						te.injectContents(0,1);
						te.validate();
					} 
					else if(te.storage[0] != null && !te.contentsMatch(0,itemstack) && te.storage[0].stackSize < 64) 
					{
						return false;
					}
					else if(te.storage[0] == null) 
					{
						te.addContents(0, new ItemStack(this,1));
					} 
					else
					{
						if(createPile(itemstack, entityplayer, world, x, y, z, side, dir))
						{
							itemstack.stackSize = itemstack.stackSize-1;
							/*if (world.getTileEntity(x,y,z) != null)
							{
								//((TileEntityIngotPile)world.getTileEntity(x,y,z)).setType(MetalRegistry.instance.getMetalFromItem(this).Name);
							}*/
							world.addBlockEvent(x,y,z,TeloBlockSetup.ingotPile,0,0);
							te.getBlockType().onBlockActivated(world, x, y, z, entityplayer, side, hitX, hitY, hitZ);
						}
						return true;

					}
					itemstack.stackSize = itemstack.stackSize-1;
					/*if (world.getTileEntity(x,y,z) != null)
					{
						//((TileEntityIngotPile)world.getTileEntity(x,y,z)).setType(MetalRegistry.instance.getMetalFromItem(this).Name);
					}*/
					world.addBlockEvent(x,y,z,TeloBlockSetup.ingotPile,0,0);
					return true;
				}

			}
			else
			{
				int m = itemstack.getItemDamage();
				if(side == 1)
				{
					if (m>=16){
						world.setBlock(x, y+1, z, TeloBlockSetup.ingotPile, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
					else{
						world.setBlock(x, y+1, z, TeloBlockSetup.ingotPile, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
				}
				else if(side == 0 && world.isAirBlock(x, y-1, z))
				{
					if(m >=16){
						world.setBlock(x, y-1, z, TeloBlockSetup.ingotPile, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
					else{
						world.setBlock(x, y-1, z, TeloBlockSetup.ingotPile, m, 0x2);
						itemstack.stackSize = itemstack.stackSize-1;
					}
				}
				else if(side == 2 && world.isAirBlock(x, y, z-1))
				{
					setSide(world, itemstack, m, dir, x, y, z, 0, 0, -1);
				}
				else if(side == 3 && world.isAirBlock(x, y, z+1))
				{
					setSide(world, itemstack, m, dir, x, y, z, 0, 0, 1);
				}
				else if(side == 4 && world.isAirBlock(x-1, y, z))
				{
					setSide(world, itemstack, m, dir, x, y, z, -1, 0, 0);
				}
				else if(side == 5 && world.isAirBlock(x+1, y, z))
				{
					setSide(world, itemstack, m, dir, x, y, z, 1, 0, 0);
				}
				/*if (world.getTileEntity(x,y,z) != null && world.getTileEntity(x,y,z) instanceof TeloTEIngotPile)
				{
					//((TileEntityIngotPile)world.getTileEntity(x,y,z)).setType(this.getItem() - 16028 - 256);
				}*/
				world.addBlockEvent(x,y,z,TeloBlockSetup.ingotPile,0,0);
				return true;
			}

		}
		return false;
	}

	public boolean isValid(World world, int i, int j, int k)
	{
		if(world.isSideSolid(i, j-1, k, ForgeDirection.UP) || world.getBlock(i, j-1, k)==TeloBlockSetup.ingotPile)
		{
			TileEntity te = world.getTileEntity(i, j-1, k);

			if (te instanceof TeloTEIngotPile)
			{
				TeloTEIngotPile ip = (TeloTEIngotPile)te;

				if (ip.storage[0] == null || ip.storage[0].stackSize < 64)
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}
}
