package com.facetorched.teloaddon.items;

import com.dunk.tfc.Items.ItemTerra;
import com.facetorched.teloaddon.util.ImageHelper;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;

public abstract class ItemFluidContainer extends ItemTerra{
	public String fluidTextureLocation;
	public ItemFluidContainer setFluidTextureLocation(String s) {
		fluidTextureLocation = s;
		return this;
	}
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return pass == 1 ? this.itemIcon : this.getContainerItem().getIcon(stack, pass);

	}
	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int pass)
	{
		if (fluidTextureLocation != null) {
			return pass == 1 ? ImageHelper.getTextureColor(fluidTextureLocation) : super.getColorFromItemStack(is, pass);
		}
		return pass == 1 ? FluidContainerRegistry.getFluidForFilledItem(is).getFluid().getColor() : super.getColorFromItemStack(is, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}
	public ItemFluidContainer registerContainer(Fluid fluid, int amount) {
		FluidContainerRegistry.registerFluidContainer(new FluidStack(fluid, amount), new ItemStack(this),new ItemStack(this.getContainerItem()));
		return this;
	}
}