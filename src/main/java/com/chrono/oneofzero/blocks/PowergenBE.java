package com.chrono.oneofzero.blocks;

import com.chrono.oneofzero.setup.Registration;
import com.chrono.oneofzero.varia.CustomEnergyStorage;
import net.minecraft.core.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.*;
import net.minecraftforge.items.*;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;

public class PowergenBE extends BlockEntity {
	
	public static final int POWERGEN_CAPACITY = 50000; // Max capacity
	public static final int POWERGEN_GENERATE = 60;    // Generation per tick
	public static final int POWERGEN_SEND = 200;       // Power to send out per tick
	
	// Never create lazy optionals in getCapability. Always place them as fields in the tile entity:
	private final ItemStackHandler itemHandler = createHandler();
	private final LazyOptional<IItemHandler> handler = LazyOptional.of(() -> itemHandler);
	
	private final CustomEnergyStorage energyStorage = createEnergy();
	private final LazyOptional<IEnergyStorage> energy = LazyOptional.of(() -> energyStorage);
	
	private int counter;
	
	public PowergenBE (BlockPos pos, BlockState state) {
		super(Registration.POWERGEN_BE.get(), pos, state);
	}
	
	@Override
	public void setRemoved () {
		super.setRemoved();
	}
	
	public void tickServer () {
		if (counter > 0) {
			energyStorage.addEnergy(POWERGEN_GENERATE);
			counter--;
			setChanged();
		}
		if (counter <= 0) {
			ItemStack stack = itemHandler.getStackInSlot(0);
			int burnTime = ForgeHooks.getBurnTime(stack, RecipeType.SMELTING);
			if (burnTime > 0) {
				itemHandler.extractItem(0, 1, false);
				counter = burnTime;
				setChanged();
			}
		}
		
		BlockState blockState = level.getBlockState(worldPosition);
		if (blockState.getValue(BlockStateProperties.POWERED) != counter > 0) {
			level.setBlock(worldPosition, blockState.setValue(BlockStateProperties.POWERED, counter > 0),
					Block.UPDATE_ALL);
		}
		
		sendOutPower();
	}
	
	private void sendOutPower () {
		AtomicInteger capacity = new AtomicInteger(energyStorage.getEnergyStored());
		if (capacity.get() > 0) {
			for (Direction direction : Direction.values()) {
				BlockEntity be = level.getBlockEntity(worldPosition.relative(direction));
				if (be != null) {
					boolean doContinue = be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
						if (handler.canReceive()) {
							int recieved = handler.receiveEnergy(Math.min(capacity.get(), POWERGEN_SEND), false);
							capacity.addAndGet(-recieved);
							energyStorage.consumeEnergy(recieved);
							setChanged();
							return capacity.get() > 0;
						} else {
							return true;
						}
					}).orElse(true);
					if (!doContinue) {
						return;
					}
				}
			}
		}
	}
	
	@Override
	public void load (CompoundTag tag) {
		if (tag.contains("Inventory")) {
			itemHandler.deserializeNBT(tag.getCompound("Inventory"));
		}
		if (tag.contains("Energy")) {
			energyStorage.deserializeNBT(tag.get("Energy"));
		}
		if (tag.contains("Info")) {
			counter = tag.getCompound("Info").getInt("Counter");
		}
		super.load(tag);
	}
	
	@Override
	protected void saveAdditional (CompoundTag tag) {
		tag.put("Inventory", itemHandler.serializeNBT());
		tag.put("Energy", energyStorage.serializeNBT());
		
		CompoundTag infoTag = new CompoundTag();
		infoTag.putInt("Counter", counter);
		tag.put("Info", infoTag);
	}
	
	private ItemStackHandler createHandler () {
		return new ItemStackHandler(1) {
			@Override
			protected void onContentsChanged (int slot) {
				// To make sure the TE persists when the chunk is saved later we need to
				// mark it dirty every time the item handler changes
				setChanged();
			}
			
			@Override
			public boolean isItemValid (int slot, @NotNull ItemStack stack) {
				return ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) > 0;
			}
			
			@NotNull
			@Override
			public ItemStack insertItem (int slot, @NotNull ItemStack stack, boolean simulate) {
				if (ForgeHooks.getBurnTime(stack, RecipeType.SMELTING) <= 0) {
					return stack;
				}
				return super.insertItem(slot, stack, simulate);
			}
		};
	}
	
	private CustomEnergyStorage createEnergy () {
		return new CustomEnergyStorage(POWERGEN_CAPACITY, 0) {
			@Override
			protected void onEnergyChanged () {
				setChanged();
			}
		};
	}
	
	@NotNull
	@Override
	public <T> LazyOptional<T> getCapability (@NotNull Capability<T> cap, @Nullable Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			return handler.cast();
		}
		if (cap == CapabilityEnergy.ENERGY) {
			return energy.cast();
		}
		return super.getCapability(cap, side);
	}
}
