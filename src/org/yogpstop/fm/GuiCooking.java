package org.yogpstop.fm;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiCooking extends GuiContainer {
    public GuiCooking(EntityPlayer player, World world, int x, int y, int z) {
        super(new ContainerCooking(player, world, x, y, z));
        ySize = 222;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        mc.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, 126, 0x404040);
        mc.fontRenderer.drawString(StatCollector.translateToLocal("container.cooking"),
                (width - mc.fontRenderer.getStringWidth(StatCollector.translateToLocal("container.cooking"))) / 2, 4, 0x404040);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();
        for (int i = 0; i < 5; i++)
            buttonList.add(new GuiButton(i, guiLeft + 30, guiTop + 26 + (20 * i), 54, 18, "open"));
        for (int i = 0; i < 5; i++)
            buttonList.add(new GuiButton(i + 5, guiLeft + 92, guiTop + 26 + (20 * i), 54, 18, "open"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        mc.renderEngine.func_98187_b("/org/yogpstop/fm/cookingtable.png");
        int xStart = width - xSize >> 1;
        int yStart = height - ySize >> 1;
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

    @Override
    public void actionPerformed(GuiButton gb) {
        ((ContainerCooking) inventorySlots).onButtonPushed(gb.id);
    }
}