package org.yogpstop.fm;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class ItemRenderer implements IItemRenderer {
	public static ItemRenderer instance = new ItemRenderer();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return type == ItemRenderType.INVENTORY
				|| type == ItemRenderType.ENTITY
				|| type == ItemRenderType.EQUIPPED;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		return helper == ItemRendererHelper.ENTITY_ROTATION
				|| helper == ItemRendererHelper.ENTITY_BOBBING;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		int[] iconC = DB.getTextureCoordFromDamage(item.getItemDamage());
		int[] textureC = GLHandler.getWH(DB.getTextureFileFromDamage(item
				.getItemDamage()));
		String texture = DB.getTextureFileFromDamage(item.getItemDamage());
		GLHandler.loadTexture(texture);
		float xs = (float) iconC[0] / (float) textureC[0];
		float xe = (float) (iconC[0] + iconC[2]) / (float) textureC[0];
		float ys = (float) iconC[1] / (float) textureC[1];
		float ye = (float) (iconC[1] + iconC[2]) / (float) textureC[1];
		switch (type) {
		case INVENTORY:
			renderInventoryItem(xs, xe, ys, ye);
			break;
		case ENTITY:
			renderEntityItem(getMiniItemCountForItemStack(item), xs, xe, ys, ye);
			break;
		case EQUIPPED:
			renderEquippedItem(xs, xe, ys, ye);
			break;
		default:
		}

	}

	private static void renderInventoryItem(float xs, float xe, float ys,
			float ye) {
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		t.addVertexWithUV(0, 16, 0, xs, ye);
		t.addVertexWithUV(16, 16, 0, xe, ye);
		t.addVertexWithUV(16, 0, 0, xe, ys);
		t.addVertexWithUV(0, 0, 0, xs, ys);
		t.draw();
	}

	private static void renderEquippedItem(float xs, float xe, float ys,
			float ye) {
		Tessellator var5 = Tessellator.instance;
		net.minecraft.client.renderer.ItemRenderer.renderItemIn2D(var5, xe, ys,
				xs, ye, 0.0625F);
	}

	private static void renderEntityItem(byte var3, float xs, float xe,
			float ys, float ye) {
		Random random = new Random();
		Tessellator var8 = Tessellator.instance;
		float var13 = 1.0F;
		float var14 = 0.5F;
		float var15 = 0.25F;
		float var17;

		if (Minecraft.getMinecraft().gameSettings.fancyGraphics) {
			float var16 = 0.0625F;
			var17 = 0.021875F;
			GL11.glTranslatef(-var14, -var15,
					-((var16 + var17) * (float) var3 / 2.0F));

			for (int var20 = 0; var20 < var3; ++var20) {
				if (var20 > 0) {
					float x = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
					float y = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
					GL11.glTranslatef(x, y, var16 + var17);
				} else {
					GL11.glTranslatef(0f, 0f, var16 + var17);
				}
				net.minecraft.client.renderer.ItemRenderer.renderItemIn2D(var8,
						xe, ys, xs, ye, var16);
			}
		} else {
			for (int var25 = 0; var25 < var3; ++var25) {
				if (var25 > 0) {
					var17 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					float var27 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					float var26 = (random.nextFloat() * 2.0F - 1.0F) * 0.3F;
					GL11.glTranslatef(var17, var27, var26);
				}

				GL11.glRotatef(180.0F - RenderManager.instance.playerViewY,
						0.0F, 1.0F, 0.0F);
				var8.startDrawingQuads();
				var8.setNormal(0.0F, 1.0F, 0.0F);
				var8.addVertexWithUV((double) (0.0F - var14),
						(double) (0.0F - var15), 0.0D, (double) xs, (double) ye);
				var8.addVertexWithUV((double) (var13 - var14),
						(double) (0.0F - var15), 0.0D, (double) xe, (double) ye);
				var8.addVertexWithUV((double) (var13 - var14),
						(double) (1.0F - var15), 0.0D, (double) xe, (double) ys);
				var8.addVertexWithUV((double) (0.0F - var14),
						(double) (1.0F - var15), 0.0D, (double) xs, (double) ys);
				var8.draw();
			}
		}
	}

	private static byte getMiniItemCountForItemStack(ItemStack stack) {
		byte var24;
		int var19 = stack.stackSize;
		if (var19 < 2) {
			var24 = 1;
		} else if (var19 < 16) {
			var24 = 2;
		} else if (var19 < 32) {
			var24 = 3;
		} else {
			var24 = 4;
		}
		return var24;
	}
}
