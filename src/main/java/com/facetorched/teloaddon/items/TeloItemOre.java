package com.facetorched.teloaddon.items;


import java.util.List;

import com.dunk.tfc.Items.ItemOre;
import com.dunk.tfc.api.Metal;
import com.dunk.tfc.api.Constant.Global;
import com.facetorched.teloaddon.TeloMod;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class TeloItemOre extends ItemOre{
	public EnumTier smeltTier;
	public boolean isSmeltable;
	public Metal metalType;
	public TeloItemOre()
	{
		super();
		setFolder("ore/");
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for (int j = 0; j < 3; j++)
		{
			list.add(new ItemStack(this, 1,(j==1?Global.oreGrade1Offset:j==2?Global.oreGrade2Offset:0)));
		}
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		metaIcons = new IIcon[3];
		for (int j = 0; j < 3; j++)
		{
			metaIcons[j] = registerer
					.registerIcon(TeloMod.MODID + ":" + textureFolder + getMetaname((j==1?Global.oreGrade1Offset:j==2?Global.oreGrade2Offset:0)));
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return getUnlocalizedName().concat("." + getMetaname(itemstack.getItemDamage()));
	}
	
	public int getMetaIconFromDamage(int i)
	{
		return i/100;
	}

	protected String getMetaname(int i)
	{
		switch(i) {
		case 0:
			return getUnlocalizedName().substring(5);
		case 100:
			return "Rich_" + getUnlocalizedName().substring(5);
		case 200:
			return "Poor_" + getUnlocalizedName().substring(5);
		}
		return "";
	}

	@Override
	public Metal getMetalType(ItemStack is)
	{
		return metalType;
	}
	public TeloItemOre setMetalType(Metal metal) {
		metalType = metal;
		return this;
	}
	
	@Override
	public boolean isSmeltable(ItemStack is) {
		//TODO set this as a config
		return this.isSmeltable;
	}
	
	public TeloItemOre setSmeltable(boolean b) {
		this.isSmeltable = b;
		return this;
	}
	
	public TeloItemOre setSmeltTier(EnumTier tier) {
		smeltTier = tier;
		return this;
	}
	@Override
	public EnumTier getSmeltTier(ItemStack is)
	{
		return smeltTier;
	}
}
/*
public class TeloItemOre extends ItemOre{
	public TeloItemOre()
	{
		super();
		setFolder("ore/");
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		if (getMetanamesLength() != 0)
		{
			for (int j = 0; j < 3; j++)
			{
				for (int i = 0; i < TeloMod.teloOres.length; i++)
				{
					list.add(new ItemStack(this, 1,i + (j==1?Global.oreGrade1Offset:j==2?Global.oreGrade2Offset:0)));
				}
			}
		}
		else
		{
			list.add(new ItemStack(this, 1));
		}
	}
	
	private int getMetanamesLength()
	{
		return TeloMod.teloOres.length * 3;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		metaIcons = new IIcon[getMetanamesLength()];
		for (int j = 0; j < 3; j++)
		{
			for (int i = 0; i < TeloMod.teloOres.length; i++)
			{
				metaIcons[i + j * TeloMod.teloOres.length] = registerer
						.registerIcon(TeloMod.MODID + ":" + textureFolder + getMetaname(i + (j==1?Global.oreGrade1Offset:j==2?Global.oreGrade2Offset:0)) + "_Ore");
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return getUnlocalizedName().concat("." + getMetaname(itemstack.getItemDamage()));
	}
	
	public int getMetaIconFromDamage(int i)
	{
		if (i < TeloMod.teloOres.length)
		{
			return i;
		}
		else if (i >= Global.oreGrade1Offset && i < Global.oreGrade1Offset + TeloMod.teloOres.length)
		{
			return (i-Global.oreGrade1Offset) + TeloMod.teloOres.length;
		}
		else if (i >= Global.oreGrade2Offset && i < Global.oreGrade2Offset + TeloMod.teloOres.length)
		{
			return  (i-Global.oreGrade2Offset )+ TeloMod.teloOres.length*2;
		}
		else if (i >= Global.oreSize)
		{
			return  (i-Global.oreSize) + TeloMod.teloOres.length * 3;
		}
		return 0;
	}

	protected String getMetaname(int i)
	{
		if (i < TeloMod.teloOres.length)
		{
			return TeloMod.teloOres[i];
		}
		else if (i >= Global.oreGrade1Offset && i < Global.oreGrade1Offset + TeloMod.teloOres.length)
		{
			return "Rich_" + TeloMod.teloOres[i - Global.oreGrade1Offset];
		}
		else if (i >= Global.oreGrade2Offset && i < Global.oreGrade2Offset + TeloMod.teloOres.length)
		{
			return "Poor_" + TeloMod.teloOres[i - Global.oreGrade2Offset];
		}
		return "";
	}

	@Override
	public Metal getMetalType(ItemStack is)
	{
		int dam = is.getItemDamage();
		switch (dam)
		{
		case 0:
			return TeloItemSetup.ALUMINUM;
		// Rich Ores
		case Global.oreGrade1Offset + 0:
			return TeloItemSetup.ALUMINUM;
		// Poor Ores
		case Global.oreGrade2Offset + 0:
			return TeloItemSetup.ALUMINUM;
			}
		return null;
	}
	
	@Override
	public boolean isSmeltable(ItemStack is) {
		//TODO set this as a config
		return true;
	}
	@Override
	public EnumTier getSmeltTier(ItemStack is)
	{
		int dam = is.getItemDamage();
		switch (dam)
		{
		case 0:
			return EnumTier.TierIII;
		// Rich Ores
		case Global.oreGrade1Offset + 0:
			return EnumTier.TierIII;
		// Poor Ores
		case Global.oreGrade2Offset + 0:
			return EnumTier.TierIII;
		}
		return EnumTier.TierX;
	}
}*/
