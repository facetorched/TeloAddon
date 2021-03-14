package com.facetorched.teloaddon.util;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class TeloRayTracer {
	/*
}
	public static List<Entity> rayTraceEntities(World w, Vec3 pos, Vec3 ray, double len)
	{
		
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(pos.xCoord, pos.yCoord, pos.zCoord, ray.xCoord, ray.yCoord, ray.zCoord).expand(len,len,len);
		
		List<Entity> ret = new ArrayList<Entity>();
		
		for (Object t : w.getEntitiesWithinAABBExcludingEntity(null, aabb))
		{
			if(t instanceof Entity) {
				AxisAlignedBB entityBB = ((Entity)t).getBoundingBox();
				if (entityBB == null)
					continue;
				entityBB.
				if (entityBB.intersects(Math.min(pos.xCoord, checkVec.xCoord), Math.min(pos.yCoord, checkVec.yCoord), Math.min(pos.zCoord, checkVec.zCoord), Math.max(pos.xCoord, checkVec.xCoord), Math.max(pos.yCoord, checkVec.yCoord), Math.max(pos.zCoord, checkVec.zCoord)))
				{
				ret.add((Entity) t);
			}
		}
		
		return ret;
	}
	*/
	
	// method for poor man's ray tracing. works well enough for my purposes
	@SuppressWarnings("unchecked")
	public static List<Entity> boundingEntities(World w, Vec3 pos, Vec3 ray, float len, float size){
		//List<Entity> ret = new ArrayList<Entity>();
		ray.xCoord *= len; ray.yCoord *= len; ray.zCoord *= len;
		ray = ray.addVector(pos.xCoord, pos.yCoord, pos.zCoord);
		AxisAlignedBB aabb = AxisAlignedBB.getBoundingBox(ray.xCoord-size, ray.yCoord-size, ray.zCoord-size, ray.xCoord+size, ray.yCoord+size, ray.zCoord+size);
		return w.getEntitiesWithinAABBExcludingEntity(null, aabb);
	}
}
