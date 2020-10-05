package com.facetorched.teloaddon.items;

import com.dunk.tfc.Items.ItemMeltedMetal;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class TeloItemMeltedMetal extends ItemMeltedMetal{
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register)
	{
		this.itemIcon = register.registerIcon(getIconString());
	}
}
