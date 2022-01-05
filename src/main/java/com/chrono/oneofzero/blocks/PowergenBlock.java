package com.chrono.oneofzero.blocks;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.*;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.*;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.*;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.network.NetworkHooks;
import org.apache.logging.log4j.core.config.PropertiesPlugin;
import org.jetbrains.annotations.*;

import java.util.List;

public class PowergenBlock extends Block implements EntityBlock {
	
	public static final String MESSAGE_POWERGEN = "message.powergen";
	public static final String SCREEN_ZERO_POWERGEN = "screen.zero.powergen";
	
	private static final VoxelShape RENDER_SHAPE = Shapes.box(0.1, 0.1, 0.1, 0.9, 0.9, 0.9);
	
	public PowergenBlock () {
		super(Properties.of(Material.METAL)
				.sound(SoundType.METAL)
				.strength(2.0f)
				.lightLevel(state -> state.getValue(BlockStateProperties.POWERED) ? 14 : 0)
				.requiresCorrectToolForDrops()
		);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public VoxelShape getOcclusionShape (BlockState blockState, BlockGetter reader, BlockPos pos) {
		return RENDER_SHAPE;
	}
	
	@Override
	public void appendHoverText (ItemStack itemStack, @Nullable BlockGetter reader, List<Component> list, TooltipFlag flags) {
		list.add(new TranslatableComponent(MESSAGE_POWERGEN, Integer.toString(PowergenBE.POWERGEN_GENERATE))
				.withStyle(ChatFormatting.BLUE));
	}
	
	@Nullable
	@Override
	public BlockEntity newBlockEntity (BlockPos blockPos, BlockState blockState) {
		return new PowergenBE(blockPos, blockState);
	}
	
	@Nullable
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker (Level level, BlockState state, BlockEntityType<T> type) {
		if (level.isClientSide()) {
			return null;
		}
		return (lvl, pos, state1, t) -> {
			if (t instanceof PowergenBE tile) {
				tile.tickServer();
			}
		};
	}
	
	@Override
	protected void createBlockStateDefinition (StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(BlockStateProperties.POWERED);
	}
	
	@Nullable
	@Override
	public BlockState getStateForPlacement (BlockPlaceContext context) {
		return super.getStateForPlacement(context).setValue(BlockStateProperties.POWERED, false);
		
	}
	
	
	@SuppressWarnings("deprecation")
	@Override
	public InteractionResult use (BlockState state, Level level, BlockPos blockPos, Player player, InteractionHand hand, BlockHitResult trace) {
		if (!level.isClientSide) {
			BlockEntity be = level.getBlockEntity(blockPos);
			if (be instanceof PowergenBE) {
				MenuProvider containerProvider = new MenuProvider() {
					@Override
					public Component getDisplayName () {
						return new TranslatableComponent(SCREEN_ZERO_POWERGEN);
					}
					
					@Override
					public AbstractContainerMenu createMenu (int windowID, Inventory playerInventory, Player playerEntity) {
						return new PowergenContainer(windowID, blockPos, playerInventory, playerEntity);
					}
				};
				NetworkHooks.openGui((ServerPlayer) player, containerProvider, be.getBlockPos());
			} else {
				throw new IllegalStateException("Our named container provider is missing! {PowergenBlock}");
			}
		}
		
		return InteractionResult.SUCCESS;
	}
}
