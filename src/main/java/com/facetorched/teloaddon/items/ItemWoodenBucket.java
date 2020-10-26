package com.facetorched.teloaddon.items;

import com.dunk.tfc.ItemSetup;
import com.facetorched.teloaddon.TeloMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemWoodenBucket extends ItemFluidContainer{
	public ItemWoodenBucket(){
		super();
		this.setContainerItem(ItemSetup.woodenBucketEmpty);
	}
	public ItemWoodenBucket(String filepath){
		super();
		this.setContainerItem(ItemSetup.woodenBucketEmpty);
		this.fluidTextureLocation = filepath;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register)
	{
		this.itemIcon = register.registerIcon(TeloMod.MODID + ":Wooden_Bucket_Overlay");
	}
}
