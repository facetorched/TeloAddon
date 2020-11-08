package com.facetorched.teloaddon.items;

import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.TeloMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemLeadBottle extends ItemFluidContainer{
	public ItemLeadBottle(){
		super();
		this.setContainerItem(TeloItemSetup.leadBottle);
		
	}
	public ItemLeadBottle(String filepath){
		super();
		this.setContainerItem(TeloItemSetup.leadBottle);
		this.fluidTextureLocation = filepath;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register)
	{
		this.itemIcon = register.registerIcon(TeloMod.MODID + ":Lead_Bottle_Overlay");
	}
}

