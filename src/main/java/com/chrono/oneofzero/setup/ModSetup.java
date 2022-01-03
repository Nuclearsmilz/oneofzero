package com.chrono.oneofzero.setup;

import net.minecraft.world.item.*;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import org.jetbrains.annotations.NotNull;

public class ModSetup {
	
	public static final String TAB_NAME = "oneofzero";
	
	public static final CreativeModeTab ITEM_GROUP = new CreativeModeTab(TAB_NAME) {
		@Override
		public @NotNull ItemStack makeIcon () {
			return new ItemStack(Items.DIAMOND);
		}
	};
	
	public static void init (FMLCommonSetupEvent event) {
		
	}
}
