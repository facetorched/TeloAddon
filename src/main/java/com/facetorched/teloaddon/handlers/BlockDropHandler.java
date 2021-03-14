package com.facetorched.teloaddon.handlers;

import java.util.Random;

import com.dunk.tfc.BlockSetup;
import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.util.ChainsawNBTHelper;
import com.facetorched.teloaddon.util.Config;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;

public class BlockDropHandler {
	@SubscribeEvent
    public void onBreakEvent(BlockEvent.BreakEvent event) {
		World world = event.world;
        if(world.isRemote)
            return;
        //did a stone block get broken and should it drop fluorite?
		if((event.block.equals(BlockSetup.stoneIgEx) || event.block.equals(BlockSetup.stoneIgIn) || event.block.equals(BlockSetup.stoneMM) || 
				event.block.equals(BlockSetup.stoneSed)) && Config.addFluorite && Config.fluoriteRarity != -1)
		{
			int rockTypeChanceModifier = 1;
			//fluorite is more common in intrusive and sedimentary rocks
			if (event.block.equals(BlockSetup.stoneIgIn)) rockTypeChanceModifier = 2;
			else if (event.block.equals(BlockSetup.stoneSed)) rockTypeChanceModifier = 2;
			ItemStack randGem = teloRandomGem(world.rand,rockTypeChanceModifier);
			if(randGem != null) {
				//spawn in a gem item at the location of destroyed stone block
				EntityItem item = new EntityItem(world, event.x, event.y, event.z, randGem);
				world.spawnEntityInWorld(item);
			}
		}
		
		ItemStack equip = event.getPlayer().getCurrentEquippedItem();
		if (equip != null)
		{
			if(TeloItemSetup.chainsaw != null && equip.getItem().equals(TeloItemSetup.chainsaw) && event.isCancelable()) {
				if(!ChainsawNBTHelper.isChainsawRunning(equip)) {
					event.setCanceled(true);
				}
			}
			
			/*
			int[] equipIDs = OreDictionary.getOreIDs(equip);
			for (int id : equipIDs)
			{
				String name = OreDictionary.getOreName(id);
				System.out.println(name);
				if (name.startsWith("itemAxe"))
				{
					System.out.println("axe");
				}
			}*/
		}
	}
	@SubscribeEvent
	public void onDrops(BlockEvent.HarvestDropsEvent event) {
		if(event.block.equals(Blocks.clay) && Config.clayBlockDropsItself) {
			event.drops.clear();
			event.drops.add(new ItemStack(Blocks.clay,1));
		}
	}
	//Method to get random gem. Modified from tfc src
	public static ItemStack teloRandomGem(Random random, int rockType)
	{
		ItemStack item = null;
		if (random.nextInt(1*Config.fluoriteRarity/rockType) == 0)
		{
			item = new ItemStack(TeloItemSetup.fluorite, 1, 0);
		}
		else if (random.nextInt(2*Config.fluoriteRarity/rockType) == 0)
		{
			item = new ItemStack(TeloItemSetup.fluorite, 1, 1);
		}
		else if (random.nextInt(4*Config.fluoriteRarity/rockType) == 0)
		{
			item = new ItemStack(TeloItemSetup.fluorite, 1, 2);
		}
		else if (random.nextInt(8*Config.fluoriteRarity/rockType) == 0)
		{
			item = new ItemStack(TeloItemSetup.fluorite, 1, 3);
		}
		else if (random.nextInt(16*Config.fluoriteRarity/rockType) == 0)
		{
			item = new ItemStack(TeloItemSetup.fluorite, 1, 4);
		}
		return item;
	}
}
