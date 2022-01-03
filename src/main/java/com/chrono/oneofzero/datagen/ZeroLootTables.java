package com.chrono.oneofzero.datagen;

import com.chrono.oneofzero.setup.Registration;
import net.minecraft.data.DataGenerator;

public class ZeroLootTables extends BaseLootTableProvider {
	
	public ZeroLootTables (DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void addTables () {
		lootTables.put(Registration.ZERO_ORE_OW.get(), createSilkTouchTable("zero_ore_ow", Registration.ZERO_ORE_OW.get(), Registration.RAW_ZERO_CHUNK.get(), 1, 3));
		lootTables.put(Registration.ZERO_ORE_NETHER.get(), createSilkTouchTable("zero_ore_nether", Registration.ZERO_ORE_NETHER.get(), Registration.RAW_ZERO_CHUNK.get(), 1, 3));
		lootTables.put(Registration.ZERO_ORE_END.get(), createSilkTouchTable("zero_ore_end", Registration.ZERO_ORE_END.get(), Registration.RAW_ZERO_CHUNK.get(), 1, 3));
		lootTables.put(Registration.ZERO_ORE_DS.get(), createSilkTouchTable("zero_ore_ds", Registration.ZERO_ORE_DS.get(), Registration.RAW_ZERO_CHUNK.get(), 1, 3));
	}
}
