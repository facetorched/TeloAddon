package com.facetorched.teloaddon.items;

import java.util.List;

import com.dunk.tfc.Items.Tools.ItemCustomAxe;
import com.dunk.tfc.api.Util.Helper;
import com.facetorched.teloaddon.TeloMod;
import com.facetorched.teloaddon.TeloReference;
import com.facetorched.teloaddon.util.ChainsawNBTHelper;
import com.facetorched.teloaddon.util.Config;
import com.facetorched.teloaddon.util.TeloRayTracer;

import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import cofh.api.energy.IEnergyContainerItem;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

//TODO crate can't store barrel :(

public class ItemChainsaw extends ItemCustomAxe implements IEnergyContainerItem{

	public int chainsawDamage;
	public ItemChainsaw(ToolMaterial e, int chainsawDamage) {
		super(e, e.getDamageVsEntity());
		this.chainsawDamage = chainsawDamage;
		this.setMaxDamage(this.getMaxDamage()+TeloReference.DURABILITY_BUFFER);
	}
	@Override
	public double getDurabilityForDisplay(ItemStack is) {
		return 1.0 - ((double)ChainsawNBTHelper.getChainsawDurability(is)) / ChainsawNBTHelper.getChainsawMaxDamage(is);
	}
	@Override
	public void registerIcons(IIconRegister register)
	{
		this.itemIcon = register.registerIcon(TeloMod.MODID+":"+getIconString());
	}
	@Override
	public void setDamage(ItemStack is, int damage) {
		if(damage < ChainsawNBTHelper.getChainsawMaxDamage(is)) {
			super.setDamage(is, damage);
		}
		else if(damage > ChainsawNBTHelper.getChainsawMaxDamage(is)){ //took more than 1 damage at once!
			super.setDamage(is, TeloReference.DURABILITY_BUFFER); //set damage to this exact value so we can detect and make sound
		}
		else {
			super.setDamage(is, ChainsawNBTHelper.getChainsawMaxDamage(is));
		}
	}
	@Override
	public boolean onEntitySwing(EntityLivingBase entity, ItemStack is)
	{
		//we don't want to swing the chainsaw!
		return true;
	}
	@Override
	public boolean onBlockStartBreak(ItemStack is, int x, int y, int z, EntityPlayer player) {
		if(ChainsawNBTHelper.isChainsawRunning(is)) {
			if(ChainsawNBTHelper.getChainsawDurability(is) == 1) {
				player.playSound("random.break", 1.0f, 1.0f);
			}
			return false; //continue as normal
		}
		return true; //cancel the break!
	}
	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int itemSlot, boolean isSelected) {
		if (! (entity instanceof EntityPlayer)) {
			return;
		}
		EntityPlayer player = (EntityPlayer)entity;
		//should we turn the darn thing off?
		if(!isSelected || !this.hasEnoughEnergy(is,world) || ChainsawNBTHelper.getChainsawDurability(is) <= 0) {
			ChainsawNBTHelper.setIsChainsawRunning(is, player, false);
			
			// i use this to delay the break sound since you can't hear it otherwise
			if(this.getDamage(is) > TeloReference.DURABILITY_BUFFER-2) {
				super.setDamage(is, this.getDamage(is)-1);
			}
			else if(this.getDamage(is) == TeloReference.DURABILITY_BUFFER-2) {
				entity.playSound("random.break", 1.0f, 1.0f);
				super.setDamage(is, ChainsawNBTHelper.getChainsawMaxDamage(is));
			}
		}
		else  {
			
			EntityPlayer p = (EntityPlayer) entity;
			if(ChainsawNBTHelper.isChainsawRunning(is)){
				boolean isCutting = false;
				for(Entity e:TeloRayTracer.boundingEntities(world, Vec3.createVectorHelper(p.posX,p.posY,p.posZ), p.getLookVec(), 1.0F, 1.0F)) { //1 block reach 1 block radius

					if(e!=p && e instanceof EntityLivingBase) {
						DamageSource dmgSrc = DamageSource.causePlayerDamage(p);
						dmgSrc.damageType = "teloChainsaw";
						if(e.attackEntityFrom(dmgSrc, chainsawDamage)) {
							
							//keep track of enemies hit to apply damage to the item
							if(!p.capabilities.isCreativeMode)
								ItemNBTHelper.modifyInt(is, "hitCount", 1);
						}
						isCutting = true;
					}
				}
				if (Helper.getMovingObjectPositionFromPlayer(world, p, false, 4) != null) {
					isCutting = true;
				}
				boolean wasCutting = ItemNBTHelper.getBoolean(is, "isCutting");
				if(isCutting && !wasCutting) { // we just started cutting!
					ItemNBTHelper.setBoolean(is, "isCutting", true);
					//extract half energy for time while idle
					int energy = Config.chainsawEnergyUsage*(int)(world.getWorldTime()-ItemNBTHelper.getLong(is, "tickCheckpoint"))/2;
					this.extractEnergy(is, energy, false);
					//set a new checkpoint so that the cutting sound starts this tick
					ItemNBTHelper.setLong(is, "tickCheckpoint", world.getWorldTime());
				}
				else if(wasCutting && !isCutting) { // we just stopped cutting
					ItemNBTHelper.setBoolean(is, "isCutting", false);
					//extract full energy for time while cutting
					int energy = Config.chainsawEnergyUsage*(int)(world.getWorldTime()-ItemNBTHelper.getLong(is, "tickCheckpoint"));
					this.extractEnergy(is, energy, false);
					//set a new checkpoint so that the idle sound starts this tick
					ItemNBTHelper.setLong(is, "tickCheckpoint", world.getWorldTime());
				}
				
				//play sound every 4 ticks
				if((world.getWorldTime() - ItemNBTHelper.getLong(is, "tickCheckpoint"))%4 == 0) {
					if(isCutting) {
						p.playSound("teloaddon:chainsaw_cut", 0.9f, 1.0f);
					}
					else {
						p.playSound("teloaddon:chainsaw_standby", 0.9f, 1.0f);
					}
				}
			}
		}
	}
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List list, boolean adv)
	{
		if(!ChainsawNBTHelper.isChainsawRunning(is)) {
			String stored = this.getEnergyStored(is)+"/"+this.getMaxEnergyStored(is);
			list.add(StatCollector.translateToLocalFormatted("desc.ImmersiveEngineering.info.energyStored", stored));
		}
	}
	@Override
	public Entity createEntity(World world, Entity location, ItemStack is) {
		ChainsawNBTHelper.setIsChainsawRunning(is, location, false);
		return super.createEntity(world, location, is);
	}
	@Override
	public boolean hasCustomEntity(ItemStack is) {
		return true;
	}
	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate)
	{
		int stored = getEnergyStored(container);
		int accepted = Math.min(maxReceive, getMaxEnergyStored(container)-stored);
		if(!simulate)
		{
			stored += accepted;
			ItemNBTHelper.setInt(container, "energy", stored);
		}
		return accepted;
	}
	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate)
	{
		int stored = getEnergyStored(container);
		int extracted = Math.min(maxExtract, stored);
		if(!simulate)
		{
			stored -= extracted;
			ItemNBTHelper.setInt(container, "energy", stored);
		}
		return extracted;
	}

	@Override
	public int getEnergyStored(ItemStack container)
	{
		return ItemNBTHelper.getInt(container, "energy");
	}
	@Override
	public int getMaxEnergyStored(ItemStack is) {
		return Config.chainsawMaxEnergy;
	}
	public boolean hasEnoughEnergy(ItemStack is, World world){
		// energy per tick
		if(ItemNBTHelper.getBoolean(is, "isRunning")) {
			int energy = Config.chainsawEnergyUsage*(int)(world.getWorldTime()-ItemNBTHelper.getLong(is, "tickCheckpoint"));
			if(!ItemNBTHelper.getBoolean(is, "isCutting")) {
				energy /= 2; //half energy!
			}
			return this.extractEnergy(is, energy, true) == energy;
		}
		return this.extractEnergy(is, Config.chainsawEnergyUsage, true)==Config.chainsawEnergyUsage;
	}
	
}
