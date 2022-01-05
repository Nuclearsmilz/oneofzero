package com.chrono.oneofzero.setup;

import com.chrono.oneofzero.OneOfZero;
import com.chrono.oneofzero.blocks.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.*;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;
import org.antlr.runtime.TokenRewriteStream;

import java.awt.*;
import java.awt.geom.NoninvertibleTransformException;

import static com.chrono.oneofzero.OneOfZero.MODID;

public class Registration {
	
	private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
	private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, MODID);
	private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, MODID);
	
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
	
	public static final RegistryObject<PowergenBlock> POWERGEN = BLOCKS.register("powergen", PowergenBlock::new);
	public static final RegistryObject<Item> POWERGEN_ITEM = fromBlock(POWERGEN);
	public static final RegistryObject<BlockEntityType<PowergenBE>> POWERGEN_BE = BLOCK_ENTITIES.register("powergen", () -> BlockEntityType.Builder.of(PowergenBE::new, POWERGEN.get()).build(null));
	public static final RegistryObject<MenuType<PowergenContainer>> POWERGEN_CONTAINER = CONTAINERS.register("powergen", () -> IForgeMenuType.create((windowID, inv, data) -> new PowergenContainer(windowID, data.readBlockPos(), inv, inv.player)));
	
	public static final RegistryObject<Item> RAW_ZERO_CHUNK = ITEMS.register("raw_zero_chunk", () -> new Item(ITEM_PROPS));
	public static final RegistryObject<Item> ZERO_INGOT = ITEMS.register("zero_ingot", () -> new Item(ITEM_PROPS));
	
	public static final Tags.IOptionalNamedTag<Block> ZERO_ORE = BlockTags.createOptional(new ResourceLocation(MODID, "zero_ore"));
	public static final Tags.IOptionalNamedTag<Item> ZERO_ORE_ITEM = ItemTags.createOptional(new ResourceLocation(MODID, "zero_ore"));
	
	
	public static <B extends Block> RegistryObject<Item> fromBlock (RegistryObject<B> block) {
		return ITEMS.register(block.getId().getPath(), () -> new BlockItem(block.get(), ITEM_PROPS));
	}
}
