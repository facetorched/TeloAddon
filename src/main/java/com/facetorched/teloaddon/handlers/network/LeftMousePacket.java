package com.facetorched.teloaddon.handlers.network;

import com.dunk.tfc.Handlers.Network.AbstractPacket;
import com.facetorched.teloaddon.util.ChainsawNBTHelper;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class LeftMousePacket extends AbstractPacket{
	protected boolean isDown;
	
	public LeftMousePacket(){}
	
	public LeftMousePacket(boolean val) {
		isDown = val;
	}
	
	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		isDown = buffer.readBoolean();
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		buffer.writeBoolean(isDown);
	}

	@Override
	public void handleClientSide(EntityPlayer player) {
		// Intentionally left empty
		
	}

	@Override
	public void handleServerSide(EntityPlayer player) {
		
		// uhhh do the same thing on the server. I think
		ItemStack held = player.getHeldItem();
		ChainsawNBTHelper.setIsChainsawRunning(held, player.worldObj, isDown);
		
	}

}
