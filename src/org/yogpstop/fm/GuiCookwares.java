package org.yogpstop.fm;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class GuiCookwares extends GuiContainer {
    boolean isNeedFuel;
    String itemname;

    public GuiCookwares(EntityPlayer player, World world, int x, int y, int z, int slot) {
        super(new ContainerCookwares(player, world, x, y, z, slot));
        isNeedFuel = DB.isNeedFuel(((IInventory) world.getBlockTileEntity(x, y, z)).getStackInSlot(slot).getItemDamage());
        itemname = StatCollector.translateToLocal(((IInventory) world.getBlockTileEntity(x, y, z)).getStackInSlot(slot).getItem().getUnlocalizedName());
        ySize = 246;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        mc.fontRenderer.drawString(StatCollector.translateToLocal("container.inventory"), 8, this.ySize - 96 + 2, 0x404040);
        mc.fontRenderer.drawString(itemname, (width - mc.fontRenderer.getStringWidth(itemname)) / 2, 2, 0x404040);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void initGui() {
        super.initGui();
        buttonList.add(new GuiButton(0, guiLeft + (width / 2) - 100, guiTop + 16, "Cook"));
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        if (isNeedFuel) {
            mc.renderEngine.func_98187_b("/org/yogpstop/fm/cookwaresf.png");
        } else {
            mc.renderEngine.func_98187_b("/org/yogpstop/fm/cookwares.png");
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int xStart = width - xSize >> 1;
        int yStart = height - ySize >> 1;
        drawTexturedModalRect(xStart, yStart, 0, 0, xSize, ySize);
    }

}
