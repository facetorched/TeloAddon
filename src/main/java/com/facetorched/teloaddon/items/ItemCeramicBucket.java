package com.facetorched.teloaddon.items;

import com.dunk.tfc.ItemSetup;
import com.facetorched.teloaddon.TeloMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;

public class ItemCeramicBucket extends ItemFluidContainer{
	public ItemCeramicBucket(){
		super();
		stackable=false;
		this.setContainerItem(ItemSetup.clayBucketEmpty);
	}
	public ItemCeramicBucket(String filepath){
		super();
		stackable=false;
		this.setContainerItem(ItemSetup.clayBucketEmpty);
		this.fluidTextureLocation = filepath;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register)
	{
		this.itemIcon = register.registerIcon(TeloMod.MODID + ":Ceramic_Bucket_Overlay");
	}
}
