package com.facetorched.teloaddon.items;

import com.dunk.tfc.Items.ItemGem;
import com.facetorched.teloaddon.TeloMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

public class TeloItemGem extends ItemGem{
	public IIcon[] teloIcons = new IIcon[5];
	@Override
	public IIcon getIconFromDamage(int i)
	{
		return teloIcons[i];
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register)
	{
		teloIcons[0] = register.registerIcon(TeloMod.MODID+":"+metaNames[0]+"_"+getIconString());
		teloIcons[1] = register.registerIcon(TeloMod.MODID+":"+metaNames[1]+"_"+getIconString());
		teloIcons[2] = register.registerIcon(TeloMod.MODID+":"+metaNames[2]+"_"+getIconString());
		teloIcons[3] = register.registerIcon(TeloMod.MODID+":"+metaNames[3]+"_"+getIconString());
		teloIcons[4] = register.registerIcon(TeloMod.MODID+":"+metaNames[4]+"_"+getIconString());
	}
}
