package com.facetorched.teloaddon.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class ImageHelper {
	@SideOnly(Side.CLIENT)
	public static int getTextureColor(String name){
		InputStream is;
		BufferedImage image;
		int color = 0;
		try {
			is = Minecraft.getMinecraft().getResourceManager().getResource((new ResourceLocation(name))).getInputStream();
		    image = ImageIO.read(is);
		}catch(IOException e){e.printStackTrace();return color;}
		int rgb = image.getRGB(0, 0);
		int r = rgb >> 16 & 0xff;
		int g = rgb >> 8 & 0xff;
		int b = rgb & 0xff;
		color = new Color(r, g, b).getRGB();
		return color;
	}
}
