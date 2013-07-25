package org.yogpstop.fm;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiCooking extends GuiContainer {
	private static final ResourceLocation rl = new ResourceLocation("/org/yogpstop/fm/cookingtable.png");

	public GuiCooking(EntityPlayer player, World world, int x, int y, int z) {
		super(new ContainerCooking(player, world, x, y, z));
		this.ySize = 222;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		this.mc.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, 126, 0x404040);
		this.mc.fontRenderer.drawString(StatCollector.translateToLocal("container.cooking"),
				(this.width - this.mc.fontRenderer.getStringWidth(StatCollector.translateToLocal("container.cooking"))) / 2, 4, 0x404040);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui() {
		super.initGui();
		for (int i = 0; i < 5; i++)
			this.buttonList.add(new GuiButton(i, this.guiLeft + 30, this.guiTop + 26 + (20 * i), 54, 18, "open"));
		for (int i = 0; i < 5; i++)
			this.buttonList.add(new GuiButton(i + 5, this.guiLeft + 92, this.guiTop + 26 + (20 * i), 54, 18, "open"));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.func_110434_K().func_110577_a(rl);
		int xStart = this.width - this.xSize >> 1;
		int yStart = this.height - this.ySize >> 1;
		drawTexturedModalRect(xStart, yStart, 0, 0, this.xSize, this.ySize);
	}

	@Override
	public void actionPerformed(GuiButton gb) {
		((ContainerCooking) this.inventorySlots).onButtonPushed(gb.id);
	}
}