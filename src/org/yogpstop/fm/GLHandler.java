package org.yogpstop.fm;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public abstract class GLHandler {
	private static final IntBuffer textureIDBuffer = BufferUtils
			.createIntBuffer(1);
	private static ByteBuffer imageData = GLAllocation
			.createDirectByteBuffer(16777216);
	private static HashMap<String, Integer> textures = new HashMap<String, Integer>();
	private static HashMap<String, int[]> wh = new HashMap<String, int[]>();

	private static int createTextureID() {
		GL11.glGenTextures(textureIDBuffer);
		return textureIDBuffer.get(0);
	}

	public static int[] getWH(String s) {
		if (!wh.containsKey(s)) {
			initTexture(s);
		}
		return wh.get(s);
	}

	public static void loadTexture(String f) {
		if (!textures.containsKey(f)) {
			initTexture(f);
		}
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textures.get(f));
	}

	private static void initTexture(String s) {
		try {
			int c = createTextureID();
			wh.put(s, loadTexture(ImageIO.read(new FileInputStream(s)), c));
			textures.put(s, c);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static int[] loadTexture(BufferedImage bi, int par2) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, par2);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_NEAREST);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S,
				GL11.GL_REPEAT);
		GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T,
				GL11.GL_REPEAT);
		int var3 = bi.getWidth();
		int var4 = bi.getHeight();
		int[] var5 = new int[var3 * var4];
		byte[] var6 = new byte[var3 * var4 * 4];
		bi.getRGB(0, 0, var3, var4, var5, 0, var3);
		for (int var7 = 0; var7 < var5.length; ++var7) {
			int var8 = var5[var7] >> 24 & 255;
			int var9 = var5[var7] >> 16 & 255;
			int var10 = var5[var7] >> 8 & 255;
			int var11 = var5[var7] & 255;
			if (Minecraft.getMinecraft().gameSettings.anaglyph) {
				int var12 = (var9 * 30 + var10 * 59 + var11 * 11) / 100;
				int var13 = (var9 * 30 + var10 * 70) / 100;
				int var14 = (var9 * 30 + var11 * 70) / 100;
				var9 = var12;
				var10 = var13;
				var11 = var14;
			}
			var6[var7 * 4 + 0] = (byte) var9;
			var6[var7 * 4 + 1] = (byte) var10;
			var6[var7 * 4 + 2] = (byte) var11;
			var6[var7 * 4 + 3] = (byte) var8;
		}
		imageData.clear();
		imageData.put(var6);
		imageData.position(0).limit(var6.length);
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, var3, var4, 0,
				GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, imageData);
		return new int[] { var3, var4 };
	}
}
