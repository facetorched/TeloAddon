package com.facetorched.teloaddon.util;

import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.TeloReference;
import com.facetorched.teloaddon.items.ItemChainsaw;

import blusunrize.immersiveengineering.common.util.ItemNBTHelper;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class ChainsawNBTHelper {
	public static boolean isChainsawRunning(ItemStack is) {
	return is != null && is.getItem().equals(TeloItemSetup.chainsaw) &&
			is.hasTagCompound() && is.getTagCompound().hasKey("isRunning") && is.getTagCompound().getBoolean("isRunning");
	}

	/**
	 * @return true if this is indeed a chainsaw
	 */
	public static boolean setIsChainsawRunning(ItemStack is, Entity entity, boolean value) {
		World world = entity.worldObj;
		if(is != null && is.getItem().equals(TeloItemSetup.chainsaw)) {
			if(!is.hasTagCompound()) {
				is.setTagCompound(new NBTTagCompound());
			}
			ItemChainsaw chainsaw = ((ItemChainsaw)is.getItem());
			if(value && ((ItemChainsaw)is.getItem()).hasEnoughEnergy(is, world) && getChainsawDurability(is) > 0) {
				//set running and make a checkpoint
				is.getTagCompound().setBoolean("isRunning", true);
				is.getTagCompound().setLong("tickCheckpoint", world.getWorldTime());
			}
			else if(ChainsawNBTHelper.isChainsawRunning(is)){
				//otherwise we wanna turn the chainsaw off and apply energy loss
				int energy = Config.chainsawEnergyUsage*(int)(world.getWorldTime()-ItemNBTHelper.getLong(is, "tickCheckpoint"));
				if(!ItemNBTHelper.getBoolean(is, "isCutting")) {
					energy /= 2; //half energy!
				}
				chainsaw.extractEnergy(is, energy, false);
				is.getTagCompound().setBoolean("isRunning", false);
				
				int damageToTake = ItemNBTHelper.getInt(is, "hitCount");
				if(ChainsawNBTHelper.getChainsawDurability(is) <= damageToTake) { //this thing finna get broken
					entity.playSound("random.break", 1.0f, 1.0f);
				}
				TeloItemSetup.chainsaw.setDamage(is, TeloItemSetup.chainsaw.getDamage(is) + damageToTake);
				ItemNBTHelper.setInt(is, "hitCount", 0);
			}
			return true;
		}
		return false;
	}
	public static int getChainsawDurability(ItemStack is) {
		return is.getItem().getMaxDamage()-TeloReference.DURABILITY_BUFFER-is.getItemDamage()-ItemNBTHelper.getInt(is, "hitCount");
	}
	public static int getChainsawMaxDamage(ItemStack is) {
		return is.getItem().getMaxDamage()-TeloReference.DURABILITY_BUFFER;
	}
}
