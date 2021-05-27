package com.facetorched.teloaddon.tileentities;

import java.util.ArrayList;
import java.util.HashMap;

import com.dunk.tfc.Blocks.Devices.BlockAxleBearing;
import com.dunk.tfc.Core.TFC_Time;
import com.dunk.tfc.TileEntities.TERotator;
import com.dunk.tfc.TileEntities.TEWaterWheel;
import com.facetorched.teloaddon.util.Config;

import blusunrize.immersiveengineering.common.blocks.wooden.TileEntityWindmill;
import blusunrize.immersiveengineering.common.blocks.wooden.TileEntityWindmillAdvanced;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

//unfortunately need to extend TEWaterWheel because of hard-coded "instanceof" in rotator class
public class TEWindmillBearing extends TEWaterWheel{ 
	boolean overloaded;
	protected HashMap<Integer, Object> rotatorMap;
	protected float maxLoad;
	public TEWindmillBearing(){
		super();
		maxLoad = 0f;
		this.hasAxle = false;
		overloaded = false;
		rotatorMap = new HashMap<Integer, Object>();
	}
	@Override
	public void updateEntity(){
		float maxSpin = 1f;
		if (!powered || overloaded)
		{
			if (!powered)
			{
				powerParity = 0;
			}
			if (spin != 0)
			{
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
			if (Math.abs(spin) > 0.1)
			{
				spin *= (0.99f - (0.01f * Math.min(99f, Math.max(0,1))));
			}
			else
			{
				spin = 0;
			}
		}
		//THE FOLLOWING IS COPIED FROM THE TERotator class, with a few things changed
		/////////////////////////////////////////////////
		if (!worldObj.isRemote)
		{
			//sendPathData();
			validatePaths();
			if (TFC_Time.getTotalTicks() % 20 == 0)
			{
				sendPathData();
			}
			//validatePaths();
			//if we're a power source, we should notify our neighbors of this fact.
			if (isPowerSource() && TFC_Time.getTotalTicks() % 20 == 0)
			{
				int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
				int direction = BlockAxleBearing.getDirectionFromMetadata(meta);
				TileEntity te1 = worldObj.getTileEntity(xCoord + (direction == 0 ? 1 : 0), yCoord, zCoord + (direction == 1 ? 1 : 0));
				TileEntity te2 = worldObj.getTileEntity(xCoord + (direction == 0 ? -1 : 0), yCoord, zCoord + (direction == 1 ? -1 : 0));

				//Power sources never flip parity on their own.
				if (te1 instanceof TERotator)
				{
					((TERotator) te1).addPowerSource(xCoord, yCoord, zCoord, false, 1f, direction == 1 ? 4 : 2, new ArrayList<String>());
				}
				if (te2 instanceof TERotator)
				{
					((TERotator) te2).addPowerSource(xCoord, yCoord, zCoord, false, 1f, direction == 1 ? 5 : 3, new ArrayList<String>());
				}

				//sendPathData();
			}
			boolean spinUpdate = false;
			boolean blockupdate = false;
			int spinCount = 0;
			float spinChange = 0;
			overSpin = 0;
			float oldSpin = spin;
			for (int i = 0; i < 6; i++)
			{
				if (pathDirections[i] != null)
				{
					Object[] o = (Object[]) pathDirections[i];
					@SuppressWarnings("unchecked")
					ArrayList<Object> pathInfo = (ArrayList<Object>) o[0];
					for (Object info : pathInfo)
					{

						int[] path = (int[]) ((Object[]) info)[0];
						boolean parity = false;
						try
						{
							parity = (Boolean) ((Object[]) info)[1];
						}
						catch (Exception e)
						{
							//if it didn't exist, assume it was false
						}
						float speedMultiplier = 1f;
						try
						{
							speedMultiplier = (Float) ((Object[]) info)[2];
						}
						catch (Exception e)
						{
							//if it didn't exist, assume there's no change
						}

						int x = path[0];
						int y = path[1];
						int z = path[2];

						if (worldObj.blockExists(x, y, z) && (x != xCoord || y != yCoord || z != zCoord))
						{
							TileEntity te = worldObj.getTileEntity(x, y, z);

							if (te != null && te instanceof TERotator && ((TERotator) te).isPowered())
							{

								float oldInputSpin = inputSpin[i];
								spinUpdate = true;
								float paritySign = parity ? -1f : 1f;
								//speedMultiplier *= gearSizes[i];
								spinChange = speedMultiplier * paritySign * ((TEWaterWheel) te).addLoad(getLoad((TERotator) te, parity, speedMultiplier), this);
								spinCount++;
								
								
								
								
								if (spinCount > 0 && !powered)
								{

									inputSpin[i] = spinChange;

									spin = spinChange;

								}
								if (powered && Math.abs(spinChange) > Math.abs(spin) && ((TERotator) te).isPowered() && !((TEWaterWheel) te).overloaded && this instanceof TEWaterWheel && Math.abs(spin) == 1f)
								{
									overSpin = spinChange - spin;
								}
								if (oldInputSpin != inputSpin[i])
								{
									blockupdate = true;
								}
							}
						}
					}
				}

			}
			if (oldSpin != spin && !(spin == oldSpin * 0.95f && Math.abs(oldSpin)==1))
			{
				blockupdate = true;
			}
			if (!spinUpdate && spin != 0)
			{
				if (Math.abs(spin) > 0.1)
				{
					spin *= 0.95f;
				}
				else
				{
					spin = 0;
				}
				blockupdate = Math.abs(spin) != 0.95f;
			}
			if (blockupdate)
			{
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			}
		}
		/*	if (spin > 1)
			{
				spin = 1;
			}
			if (spin < -1)
			{
				spin = -1;
			}*/
		if (worldObj.isRemote)
		{

			if (isPowerSource() && powered || true)
			{
				rotation = nextRotation;

				//We calculate the spin			
				long n = oldN + 1;
				if (oldN == 0)
				{
					oldN = n;
				}
				float n2 = n / 30f;
				n2 *= Math.PI;

				float n20 = oldN / 30f;
				n20 *= Math.PI;

				double calc = n2;

				//This is where in the cycle we would have been last time.
				double calc0 = n20;

				//This is the rate of change at the current speed.
				double calcChange = calc - calc0;

				calcChange *= getSpin();

				//System.out.println(calcChange);

				//Now, we get the swimRender from the fish.

				nextRotation = (float) (rotation + calcChange);
				oldN = n;

				if ((nextRotation - rotation) == (float) ((nextRotation % (2d * Math.PI)) - (rotation % (2d * Math.PI))) && rotation != (float) (rotation % (2d * Math.PI))
				&& Math.abs(rotation) > Math.PI * 10000000)
				{

					nextRotation = (float) (nextRotation % (2d * Math.PI));
					rotation = (float) (rotation % (2d * Math.PI));
				}
			}

			if (rotation == nextRotation && rotation == 0)
			{
				//System.out.println("bad!");
			}
			//nextRotation = rotation + (nextRotation - rotation) * Math.abs(getSpin());
		}
		//////////////////////////////////////////////////
		
		
		
		//The following is modified code from the TEWaterwheel Class
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
		int dir = BlockAxleBearing.getDirectionFromMetadata(meta);
		//System.out.println(dir);
		//Find out if there is an IE windmill nextdoor. this method may need some improvement
		Integer ddir = null;
		TileEntity te = null;
		if(!worldObj.isRemote && hasAxle()) {
			if(dir == 0){
				if(te == null) {
					TileEntity tetest = worldObj.getTileEntity(xCoord-1, yCoord, zCoord);
					if(tetest != null && tetest instanceof TileEntityWindmill){
						ddir = 4; //west
						te = tetest;
					}
				}
				if(te == null) {
					TileEntity tetest = worldObj.getTileEntity(xCoord+1, yCoord, zCoord);
					if(tetest != null && tetest instanceof TileEntityWindmill){
						ddir = 5; //east
						te = tetest;
					}
				}
			}
			else if(dir == 1) {
				if(te == null) {
					TileEntity tetest = worldObj.getTileEntity(xCoord, yCoord, zCoord-1);
					if(tetest != null && tetest instanceof TileEntityWindmill){
						ddir = 2; //north
						te = tetest;
					}
				}
				if(te == null) {
					TileEntity tetest = worldObj.getTileEntity(xCoord, yCoord, zCoord+1);
					if(tetest != null && tetest instanceof TileEntityWindmill){
						ddir = 3; //south
						te = tetest;
					}
				}
			}
			
			float totalLoad = calculateTotalLoad();
			powered = false;
			overloaded = totalLoad >= maxLoad;
			
			if(ddir != null && te != null) {
				TileEntityWindmill tew = (TileEntityWindmill)te;
				if(ddir == tew.facing) {

					maxLoad = Config.mechanismsWindmillPowerFactor*tew.prevRotation*500f;
					
					//System.out.println(maxLoad);
					powered = true;
					/*
					if(overloaded) {
						tew.canTurn = false;
						tew.prevRotation = 0;
						tew.rotation = 0;
						tew.turnSpeed = 0;
						tew.writeToNBT(new NBTTagCompound());
					}
					else {
						tew.canTurn = true;
					}
					*/
					if(ddir == 5 || ddir == 3) {
						powerParity = -1;
						overloaded = totalLoad >= maxLoad;
						if (!overloaded && spin > -maxSpin)
						{
							if (spin != -maxSpin && spin != -maxSpin * 0.95f)
							{
								worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
							}
							spin = Math.max(-maxSpin, spin - 0.1f);
						}
					}
					else {
						powerParity = 1;
						overloaded = totalLoad >= maxLoad;
						if (!overloaded && spin < maxSpin)
						{
							if (spin != maxSpin)
							{
								worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
							}
							spin = Math.min(maxSpin, spin + 0.1f);
						}
					}
				}
			}
		}
		if (spin == 0 && !powered)
		{
			nextRotation = rotation;
		}
		else if (spin == 0 && powered && overloaded)
		{
			nextRotation = rotation + (nextRotation - rotation) * 0.95f;
			
		}
		//System.out.print(worldObj.isRemote);
		//System.out.println(maxLoad);
		
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
	}
}
