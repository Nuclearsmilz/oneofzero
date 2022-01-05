package com.chrono.oneofzero.blocks;

import com.chrono.oneofzero.setup.Registration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.*;
import net.minecraftforge.items.*;
import net.minecraftforge.items.wrapper.InvWrapper;

public class PowergenContainer extends AbstractContainerMenu {
	
	private BlockEntity blockEntity;
	private Player playerEntity;
	private IItemHandler playerInventory;
	
	public PowergenContainer (int windowID, BlockPos pos, Inventory inv, Player player) {
		super(Registration.POWERGEN_CONTAINER.get(), windowID);
		blockEntity = player.getCommandSenderWorld().getBlockEntity(pos);
		this.playerEntity = player;
		this.playerInventory = new InvWrapper((Container) playerInventory); //TODO: uncast container
		
		if (blockEntity != null) {
			blockEntity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
				addSlot(new SlotItemHandler(h, 0, 64, 24));
			});
		}
		layoutPlayerInventorySlots(10, 70);
		trackPower();
	}
	
	// Setup syncing of power from server to client so that the GUI can show the amount of power in the block
	private void trackPower () {
		// Unfortunately on a dedicated server ints are actually truncated to short so we need
		// to split our integer below (split our 32 bit integer into two 16 bit integers)
		addDataSlot(new DataSlot() {
			@Override
			public int get () {
				return getEnergy() & 0xffff;
			}
			
			@Override
			public void set (int pValue) {
				blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
					int energyStored = h.getEnergyStored() & 0xffff0000;
					//((CustomEnergyStorage)h).setEnergy(energyStored + (value & 0xffff));
				});
			}
		});
		addDataSlot(new DataSlot() {
			@Override
			public int get () {
				return (getEnergy() >> 16) & 0xffff;
			}
			
			@Override
			public void set (int pValue) {
				blockEntity.getCapability(CapabilityEnergy.ENERGY).ifPresent(h -> {
					int energyStored = h.getEnergyStored() & 0x0000ffff;
					//((CustomEnergyStorage)h).setEnergy(energyStored | (value << 16));
				});
			}
		});
	}
	
	@Override
	public ItemStack quickMoveStack (Player playerIn, int index) {
		ItemStack itemStack = ItemStack.EMPTY;
		Slot slot = this.slots.get(index);
		if(slot != null && slot.hasItem()){
			ItemStack stack = slot.getItem();
			itemStack = stack.copy();
			if(index == 0){
				if(!this.moveItemStackTo(stack, 1, 37, true)){
					return ItemStack.EMPTY;
				}
				slot.onQuickCraft(stack, itemStack);
			} else {
				if(ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0){
					if(!this.moveItemStackTo(stack, 0, 1, false)){
						return ItemStack.EMPTY;
					}
				} else if (index < 28) {
					if (!this.moveItemStackTo(stack, 28, 37, false)) {
						return ItemStack.EMPTY;
					}
				} else if (index < 37 && !this.moveItemStackTo(stack, 1, 28, false)) {
					return ItemStack.EMPTY;
				}
			}
			
			if (stack.isEmpty()) {
				slot.set(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			
			if (stack.getCount() == itemStack.getCount()) {
				return ItemStack.EMPTY;
			}
			
			slot.onTake(playerIn, stack);
		}
		return itemStack;
	}
	
	public int getEnergy () {
		return blockEntity.getCapability(CapabilityEnergy.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
	}
	
	private int addSlotRange (IItemHandler handler, int index, int x, int y, int amount, int dx) {
		for (int i = 0; i < amount; i++) {
			addSlot(new SlotItemHandler(handler, index, x, y));
			x += dx;
			index++;
		}
		return index;
	}
	
	private int addSlotBox (IItemHandler handler, int index, int x, int y, int horizontalAmount, int dx, int verticalAmount, int dy) {
		for (int j = 0; j < verticalAmount; j++) {
			index = addSlotRange(handler, index, x, y, horizontalAmount, dx);
			y += dy;
		}
		return index;
	}
	
	private void layoutPlayerInventorySlots (int leftCol, int topRow) {
		// Player inv
		addSlotBox(playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);
		
		// Hotbar
		topRow += 58;
		addSlotRange(playerInventory, 0, leftCol, topRow, 9, 18);
	}
	
	@Override
	public boolean stillValid (Player playerIn) {
		return stillValid(ContainerLevelAccess.create(blockEntity.getLevel(), blockEntity.getBlockPos()), playerEntity, Registration.POWERGEN.get());
	}
}
