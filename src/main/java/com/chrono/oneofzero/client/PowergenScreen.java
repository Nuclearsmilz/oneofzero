package com.chrono.oneofzero.client;

import com.chrono.oneofzero.OneOfZero;
import com.chrono.oneofzero.blocks.PowergenContainer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class PowergenScreen extends AbstractContainerScreen<PowergenContainer> {
	
	private final ResourceLocation GUI = new ResourceLocation(OneOfZero.MODID, "textures/gui/powergen_gui.png");
	
	public PowergenScreen (PowergenContainer container, Inventory inv, Component name) {
		super(container, inv, name);
	}
	
	@Override
	public void render (PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
		this.renderBackground(matrixStack);
		super.render(matrixStack, mouseX, mouseY, partialTicks);
		this.renderTooltip(matrixStack, mouseX, mouseY);
	}
	
	@Override
	protected void renderLabels (PoseStack matrixStack, int mouseX, int mouseY) {
		drawString(matrixStack, Minecraft.getInstance().font, "Energy: " + menu.getEnergy(), 10, 10, 0xffffffff);
	}
	
	@Override
	protected void renderBg (PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
		RenderSystem.setShaderTexture(0, GUI);
		int relativeX = (this.width - this.imageWidth) / 2;
		int relativeY = (this.height - this.imageHeight) / 2;
		this.blit(matrixStack, relativeX, relativeY, 0, 0, this.imageWidth, this.imageHeight);
	}
}
