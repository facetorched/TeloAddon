package com.facetorched.teloaddon.handlers;

import java.util.Random;

import com.dunk.tfc.BlockSetup;
import com.facetorched.teloaddon.TeloItemSetup;
import com.facetorched.teloaddon.util.Config;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.item.EntityItem;
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
				event.block.equals(BlockSetup.stoneSed)) && Config.addFluorite && Config.stoneDropFluorite)
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
	}
	//Method to get random gem. Copied from tfc src
	public static ItemStack teloRandomGem(Random random, int rockType)
	{
		ItemStack item = null;
		if (random.nextInt(500/rockType) == 0)
		{
			item = new ItemStack(TeloItemSetup.fluorite, 1, 0);
		}
		else if (random.nextInt(1000/rockType) == 0)
		{
			item = new ItemStack(TeloItemSetup.fluorite, 1, 1);
		}
		else if (random.nextInt(2000/rockType) == 0)
		{
			item = new ItemStack(TeloItemSetup.fluorite, 1, 2);
		}
		else if (random.nextInt(4000/rockType) == 0)
		{
			item = new ItemStack(TeloItemSetup.fluorite, 1, 3);
		}
		else if (random.nextInt(8000/rockType) == 0)
		{
			item = new ItemStack(TeloItemSetup.fluorite, 1, 4);
		}
		return item;
	}
}
