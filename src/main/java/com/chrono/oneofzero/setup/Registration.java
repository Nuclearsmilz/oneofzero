package com.chrono.oneofzero.setup;

import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;

import static com.chrono.oneofzero.OneOfZero.MODID;

public class Registration {
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	
	public static void init () {
		IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
		BLOCKS.register(bus);
		ITEMS.register(bus);
	}
	
	
	//Some common properties for our blocks and items
	public static final BlockBehaviour.Properties ORE_PROPS = BlockBehaviour.Properties.of(Material.STONE).strength(2f);
	public static final Item.Properties ITEM_PROPS = new Item.Properties().tab(ModSetup.ITEM_GROUP);
	
	
	public static final RegistryObject<Block> ZERO_ORE_OW = BLOCKS.register("zero_ore_ow", () -> new Block(ORE_PROPS));
	public static final RegistryObject<Item> ZERO_ORE_OW_ITEM = fromBlock(ZERO_ORE_OW);
	public static final RegistryObject<Block> ZERO_ORE_NETHER = BLOCKS.register("zero_ore_nether", () -> new Block(ORE_PROPS));
	public static final RegistryObject<Item> ZERO_ORE_NETHER_ITEM = fromBlock(ZERO_ORE_NETHER);
	public static final RegistryObject<Block> ZERO_ORE_END = BLOCKS.register("zero_ore_end", () -> new Block(ORE_PROPS));
	public static final RegistryObject<Item> ZERO_ORE_END_ITEM = fromBlock(ZERO_ORE_END);
	public static final RegistryObject<Block> ZERO_ORE_DS = BLOCKS.register("zero_ore_ds", () -> new Block(ORE_PROPS));
	public static final RegistryObject<Item> ZERO_ORE_DS_ITEM = fromBlock(ZERO_ORE_DS);
	
	
	
	public static <B extends Block> RegistryObject<Item> fromBlock (RegistryObject<B> block) {
		return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPS));
	}
}
