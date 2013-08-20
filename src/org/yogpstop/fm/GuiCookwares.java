package org.yogpstop.fm;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiCookwares extends GuiContainer {
	boolean isNeedFuel;
	String itemname;
	private static final ResourceLocation rl_f = new ResourceLocation("yogpstop_fm","textures/gui/cookwaresf.png");
	private static final ResourceLocation rl = new ResourceLocation("yogpstop_fm","textures/gui/cookwares.png");

	public GuiCookwares(EntityPlayer player, World world, int x, int y, int z, int slot) {
		super(new ContainerCookwares(player, world, x, y, z, slot));
		this.isNeedFuel = DB.isNeedFuel(((IInventory) world.getBlockTileEntity(x, y, z)).getStackInSlot(slot).getItemDamage());
		this.itemname = StatCollector.translateToLocal(((IInventory) world.getBlockTileEntity(x, y, z)).getStackInSlot(slot).getItem().getUnlocalizedName());
		this.ySize = 246;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.mc.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
		this.mc.fontRenderer.drawString(this.itemname, (this.width - this.mc.fontRenderer.getStringWidth(this.itemname)) / 2, 2, 0x404040);
	}

	@Override
	public void initGui() {
		super.initGui();
		this.buttonList.add(new GuiButton(0, this.guiLeft + (this.width / 2) - 100, this.guiTop + 16, "Cook"));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		if (this.isNeedFuel) {
			this.mc.func_110434_K().func_110577_a(rl_f);
		} else {
			this.mc.func_110434_K().func_110577_a(rl);
		}
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int xStart = this.width - this.xSize >> 1;
		int yStart = this.height - this.ySize >> 1;
		drawTexturedModalRect(xStart, yStart, 0, 0, this.xSize, this.ySize);
	}

}
