package com.facetorched.teloaddon.items;

import com.dunk.tfc.Entities.EntityProjectileTFC;
import com.dunk.tfc.Items.Tools.ItemCustomBow;
import com.dunk.tfc.api.TFCItems;
import com.dunk.tfc.api.Interfaces.ISize;
import com.facetorched.teloaddon.TeloMod;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;



public class TeloItemCustomBow extends ItemCustomBow{
	protected String[] bowPullIconNameArray = new String[] {"pulling_0", "pulling_1", "pulling_2", "pulling_3"};
	protected IIcon[] iconArray;
	float speedMultiplier;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(TeloMod.MODID + ":" + this.getIconString() + "_standby");
		iconArray = new IIcon[bowPullIconNameArray.length];

		for (int i = 0; i < iconArray.length; ++i)
			iconArray[i] = par1IconRegister.registerIcon(TeloMod.MODID + ":" + this.getIconString() + "_" + bowPullIconNameArray[i]);
		/*if(this == TFCItems.compositeBow)
		{
			MinecraftForgeClient.registerItemRenderer(this, new CompositeBowItemRenderer());
		}*/
	}
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int par1)
	{
		return iconArray[par1];
	}
	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int inUseCount) {
		int j = this.getMaxItemUseDuration(is) - inUseCount;

		ArrowLooseEvent event = new ArrowLooseEvent(player, is, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return;
		j = event.charge;

		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, is) > 0;

		//First we run the normal ammo check to see if the arrow is in the players inventory
		boolean hasAmmo = flag || player.inventory.hasItem(TFCItems.arrow);
		boolean hasAmmoInQuiver = false;
		//If there was no ammo in the inventory then we need to check if there is a quiver and if there is ammo inside of it.
		if(!hasAmmo)
			hasAmmoInQuiver = consumeArrowInQuiver(player, false);

		if (hasAmmo || hasAmmoInQuiver)
		{
			float forceMult = j / getUseSpeed(player);
			//f = (f * f + f * 2.0F) / 3.0F;

				
			
			forceMult = forceMult*forceMult;
			
			if (forceMult < 0.25D)
				return;		
			
			if (forceMult > (1F*getDamageMultiplier()))
				forceMult = getDamageMultiplier();

			EntityProjectileTFC entityarrow = new EntityProjectileTFC(world, player, forceMult * 2.0F);
			entityarrow.setDamage(forceMult * 80.0);
			//System.out.println(entityarrow.getDamage() + ": " + forceMult);
			if (forceMult >= 1.24F)
				entityarrow.setIsCritical(true);
			//System.out.println(forceMult);
			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, is);

			if (k > 0)
				entityarrow.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);

			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, is);

			if (l > 0)
				entityarrow.setKnockbackStrength(l);

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, is) > 0)
				entityarrow.setFire(100);

			is.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + forceMult * 0.5F);

			if (flag)
				entityarrow.canBePickedUp = 2;
			else if(hasAmmo)
				player.inventory.consumeInventoryItem(TFCItems.arrow);
			else if(hasAmmoInQuiver)
				consumeArrowInQuiver(player, true);

			if (!world.isRemote)
				world.spawnEntityInWorld(entityarrow);
		}
	}
	@Override
	public ItemCustomBow setSpeedMultiplier(float mult)
	{
		speedMultiplier = mult;
		return super.setSpeedMultiplier(mult);
	}
	@Override
	public float getUseSpeed(EntityPlayer player)
	{
		float speed = 20.0f * speedMultiplier;
		ItemStack[] armor = player.inventory.armorInventory;
		if(armor[0] != null && armor[0].getItem() instanceof ISize)
			speed += 5.0f / ((ISize)armor[0].getItem()).getWeight(armor[0]).multiplier;
		if(armor[1] != null && armor[1].getItem() instanceof ISize)
			speed += 5.0f / ((ISize)armor[1].getItem()).getWeight(armor[1]).multiplier;
		if(armor[2] != null && armor[2].getItem() instanceof ISize)
			speed += 5.0f / ((ISize)armor[2].getItem()).getWeight(armor[2]).multiplier;
		if(armor[3] != null && armor[3].getItem() instanceof ISize)
			speed += 5.0f / ((ISize)armor[3].getItem()).getWeight(armor[3]).multiplier;

		return speed;
	}
}
