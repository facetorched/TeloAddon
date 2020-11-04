package com.facetorched.teloaddon.items;

import com.dunk.tfc.Items.Tools.ItemSteelBucket;
import com.facetorched.teloaddon.TeloMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TeloItemSteelBucket extends ItemSteelBucket{

	public TeloItemSteelBucket(Block fluid) {
		super(fluid);
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register)
	{
		this.itemIcon = register.registerIcon(TeloMod.MODID+":"+getIconString());
	}
	public TeloItemSteelBucket registerContainer(Fluid fluid, int amount) {
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, amount), new ItemStack(this),new ItemStack(this.getContainerItem()));
		return this;
	}
	@Override
	public TeloItemSteelBucket setContainerItem(Item container) {
		super.setContainerItem(container);
		return this;
	}
}
