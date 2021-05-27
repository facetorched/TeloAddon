package com.facetorched.teloaddon.render;

import org.lwjgl.opengl.GL11;

import com.facetorched.teloaddon.TeloMod;
import com.facetorched.teloaddon.util.ChainsawNBTHelper;

import blusunrize.immersiveengineering.client.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.model.obj.WavefrontObject;

public class ItemRenderChainsaw implements IItemRenderer{
	static WavefrontObject [] modelobjs = {ClientUtils.getModel("teloaddon:models/items/tools/chainsaw.obj"),ClientUtils.getModel("teloaddon:models/items/tools/chainsaw_2.obj")};
	static WavefrontObject modelobjNoChain = ClientUtils.getModel("teloaddon:models/items/tools/chainsaw_nochain.obj");
	static WavefrontObject modelobj;
	public static final ResourceLocation texture = new ResourceLocation(TeloMod.MODID, "textures/items/tools/Chainsaw.png");
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return true;
	}
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		
		
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		EntityClientPlayerMP player = ClientUtils.mc().thePlayer;
		boolean running = ChainsawNBTHelper.isChainsawRunning(item);
		//alternate each tick
		if(running) {
			modelobj = modelobjs[(int)Minecraft.getMinecraft().theWorld.getWorldTime()%2];
		}
		else if(ChainsawNBTHelper.getChainsawDurability(item) == 0) {
			modelobj = modelobjNoChain;
		}
		else {
			modelobj = modelobjs[0];
		}
		
		if(type==ItemRenderType.EQUIPPED) {
			GL11.glPushMatrix();
			GL11.glScalef(1.7F, 1.7F, 1.7F);
			if(running) {
				GL11.glTranslatef(0.3F, 0.1F, 0.1F);
				GL11.glRotatef(80.0F, 0.2F, 1.0F, 0.0F);
				GL11.glRotatef(-40.0F, 0.0F, 0.0F, 1.0F);
			}
			else {
				GL11.glTranslatef(0.1F, 0.4F, 0.3F);
				GL11.glRotatef(70.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(30.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
				
			}
			modelobj.renderAll();
			GL11.glPopMatrix();
		}
		//first person should look different!
		if(type==ItemRenderType.EQUIPPED_FIRST_PERSON && player!=null && player.getHeldItem() == item)
		{
			
			running = ChainsawNBTHelper.isChainsawRunning(player.getHeldItem());
			
			GL11.glPushMatrix();
			
			/*
			if(running) {
				// suppress re-equip animation. this code was copied from
				// https://www.minecraftforum.net/forums/mapping-and-modding-java-edition/minecraft-mods/modification-development/2350120-can-i-disable-the-animation-of-item-at-right-click?comment=9
				ItemRenderer ir = Minecraft.getMinecraft().entityRenderer.itemRenderer;
				if(ir != null)try {
					float equippedProgress = 1F;
					float prevEquippedProgress = 1F;
					Field field = ItemRenderer.class.getDeclaredField("equippedProgress");
					field.setAccessible(true);
					if(field.getFloat(ir) != 1f) {
						//System.out.print("a");
						//System.out.println(field.getFloat(ir));
						equippedProgress = field.getFloat(ir);
						field.setFloat(ir,1.0F);
					}
					field = ItemRenderer.class.getDeclaredField("prevEquippedProgress");
					field.setAccessible(true);
					if(field.getFloat(ir)!= 1.0f) {
						//System.out.print("b");
						//System.out.println(field.getFloat(ir));
						prevEquippedProgress = field.getFloat(ir);
						field.setFloat(ir,1.0F);
					}
					float f1 = prevEquippedProgress + (equippedProgress - prevEquippedProgress) * .369F;
					GL11.glTranslatef(0F, (1.0F - f1) * .6F, 0F);
					throw new Exception();
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}*/
			
			//GL11.glScalef(.75f,.75f,.75f);
			
			if(running) {
				GL11.glTranslatef(-0.6F, 1.5F, -0.4F);
				GL11.glRotatef(50.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(30.0F, 0.0F, 1.0F, 0.0F);
			}
			else {
				GL11.glTranslatef(-0.3F, 1.2F, -0.3F);
				GL11.glRotatef(50.0F, 1.0F, 0.0F, 0.0F);
			}
			//GL11.glRotatef(30.0F, 0.0F, 0.0F, 1.0F);
			modelobj.renderAll();
			GL11.glPopMatrix();
			
			
			Render render;
			RenderPlayer renderplayer;
			
			//right arm
			GL11.glPushMatrix();
			GL11.glScalef(0.5F, 1.2F, 1.5F);
			ClientUtils.bindTexture(player.getLocationSkin().getResourceDomain()+":"+player.getLocationSkin().getResourcePath());
			GL11.glTranslatef(-0.9F, 0.8F, 0.6F);
			if(running) {
				GL11.glTranslatef(-0.5F, 0.2F, 0.0F);
			}
			GL11.glRotatef(-70.0F, 1.0F, 0.0F, 0.0F);
			GL11.glRotatef(-30.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
			render = RenderManager.instance.getEntityRenderObject(player);
			renderplayer = (RenderPlayer)render;
			renderplayer.renderFirstPersonArm(player);
			GL11.glPopMatrix();
			
			//left arm
			if(!running) {
				GL11.glPushMatrix();
				ClientUtils.bindTexture(player.getLocationSkin().getResourceDomain()+":"+player.getLocationSkin().getResourcePath());
				GL11.glTranslatef(-1.6F, 1.0F, 0.3F);
				GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(10.0F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
				render = RenderManager.instance.getEntityRenderObject(player);
				renderplayer = (RenderPlayer)render;
				renderplayer.renderFirstPersonArm(player);
				GL11.glPopMatrix();
			}
			
			
			
			
			
		}
		if(type==ItemRenderType.INVENTORY || type ==ItemRenderType.ENTITY) {
			GL11.glPushMatrix();
			GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glTranslatef(0.1F, 0.2F, 0.0F);
			modelobj.renderAll();
			GL11.glPopMatrix();
		}
	}
}
