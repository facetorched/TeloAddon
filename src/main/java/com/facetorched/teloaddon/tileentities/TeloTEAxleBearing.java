package com.facetorched.teloaddon.tileentities;

import com.dunk.tfc.Blocks.Devices.BlockAxleBearing;
import com.dunk.tfc.TileEntities.TEAxleBearing;
import com.dunk.tfc.TileEntities.TERotator;
import com.facetorched.teloaddon.util.Config;

import blusunrize.immersiveengineering.common.blocks.metal.TileEntityDynamo;
import net.minecraft.tileentity.TileEntity;

public class TeloTEAxleBearing extends TEAxleBearing{
	@Override
	public float getLoad(TERotator carrier, boolean flipParity, float speedMultiplier){
		//System.out.println(flipParity);
		float extraLoad = 0;
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		int dir = BlockAxleBearing.getDirectionFromMetadata(meta);
		//System.out.println(dir);
		//Find out if there is an IE dynamo nearby. this method may need some improvement
		Integer ddir = null;
		TileEntity te = null;
		if(hasAxle()) {
			if(dir == 0){
				if(te == null) {
					TileEntity tetest = worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
					if(tetest != null && tetest instanceof TileEntityDynamo){
						ddir = 4; //west
						te = tetest;
					}
				}
				if(te == null) {
					TileEntity tetest = worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
					if(tetest != null && tetest instanceof TileEntityDynamo){
						ddir = 5; //east
						te = tetest;
					}
				}
			}
			else if(dir == 1) {
				if(te == null) {
					TileEntity tetest = worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
					if(tetest != null && tetest instanceof TileEntityDynamo){
						ddir = 2; //north
						te = tetest;
					}
				}
				if(te == null) {
					TileEntity tetest = worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
					if(tetest != null && tetest instanceof TileEntityDynamo){
						ddir = 3; //south
						te = tetest;
					}
				}
			}
			if(ddir != null && te != null) {
				TileEntityDynamo ted = (TileEntityDynamo)te;
				ted.inputRotation(Config.mechanismsDynamoPowerFactor * Math.abs(this.getSpin()), ddir);
				//System.out.println(speedMultiplier);
				
				extraLoad = Config.mechanismsDynamoLoadFactor * (float)Math.pow(speedMultiplier, Config.mechanismsDynamoLoadExponent);
				//System.out.println(extraLoad);
			}
		}
		return extraLoad + super.getLoad(carrier, flipParity, speedMultiplier);
	}
}
