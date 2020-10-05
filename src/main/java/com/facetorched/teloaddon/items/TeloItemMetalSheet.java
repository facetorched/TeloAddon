package com.facetorched.teloaddon.items;

import com.dunk.tfc.Items.ItemMetalSheet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class TeloItemMetalSheet extends ItemMetalSheet{
	public TeloItemMetalSheet(int metalID){
		super(metalID);
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register)
	{
		this.itemIcon = register.registerIcon(getIconString());
	}
}
