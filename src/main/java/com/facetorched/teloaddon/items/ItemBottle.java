package com.facetorched.teloaddon.items;

import com.dunk.tfc.ItemSetup;
import com.dunk.tfc.Reference;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemBottle extends ItemFluidContainer{
	public ItemBottle(){
		super();
		this.setContainerItem(ItemSetup.glassBottle);
		
	}
	public ItemBottle(String filepath){
		super();
		this.setContainerItem(ItemSetup.glassBottle);
		this.fluidTextureLocation = filepath;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register)
	{
		this.itemIcon = register.registerIcon(Reference.MOD_ID + ":Glass Bottle Overlay");
	}
}
