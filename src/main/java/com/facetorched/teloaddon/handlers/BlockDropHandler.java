package com.facetorched.teloaddon.handlers;

import com.dunk.tfc.ItemSetup;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class BlockDropHandler {
	@SubscribeEvent
    public void onHarvestBlock(HarvestDropsEvent event)
    {
        World world = event.world;

        if(world.isRemote)
            return;
		if(event.block == Blocks.sand)
		{
			event.drops.add(new ItemStack(ItemSetup.gemDiamond));
			event.drops.remove(0); //remove the original block drop
		}
    }
}
