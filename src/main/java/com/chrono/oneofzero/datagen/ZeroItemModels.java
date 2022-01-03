package com.chrono.oneofzero.datagen;

import com.chrono.oneofzero.OneOfZero;
import com.chrono.oneofzero.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ZeroItemModels extends ItemModelProvider {
	
	public ZeroItemModels (DataGenerator generator, ExistingFileHelper helper) {
		super(generator, OneOfZero.MODID, helper);
	}
	
	@Override
	protected void registerModels () {
		withExistingParent(Registration.ZERO_ORE_OW_ITEM.get().getRegistryName().getPath(),
				modLoc("block/zero_ore_ow"));
		withExistingParent(Registration.ZERO_ORE_NETHER_ITEM.get().getRegistryName().getPath(),
				modLoc("block/zero_ore_nether"));
		withExistingParent(Registration.ZERO_ORE_END_ITEM.get().getRegistryName().getPath(),
				modLoc("block/zero_ore_end"));
		withExistingParent(Registration.ZERO_ORE_DS_ITEM.get().getRegistryName().getPath(),
				modLoc("block/zero_ore_ds"));
		
		singleTexture(Registration.RAW_ZERO_CHUNK.get().getRegistryName().getPath(),
				mcLoc("item/generated"),
				"layer0",
				mcLoc("item/raw_zero_chunk"));
		singleTexture(Registration.ZERO_INGOT.get().getRegistryName().getPath(),
				mcLoc("item/generated"),
				"layer0",
				mcLoc("item/zero_ingot"));
	}
}
