package com.facetorched.teloaddon.items;

import com.dunk.tfc.Items.ItemMetalSheet2x;
import com.facetorched.teloaddon.TeloMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class TeloItemMetalSheet2x extends ItemMetalSheet2x{
	public TeloItemMetalSheet2x(int metalID){
		super(metalID);
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register)
	{
		this.itemIcon = register.registerIcon(TeloMod.MODID+":"+getIconString());
	}
}
