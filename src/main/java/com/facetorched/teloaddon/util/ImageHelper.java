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
		int [] pixel = image.getRaster().getPixel(0, 0, new int[4]);
		color = new Color(pixel[0],pixel[1],pixel[2]).getRGB();
		return color;
	}
}
