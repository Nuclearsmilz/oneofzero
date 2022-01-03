package com.chrono.oneofzero.datagen;

import com.chrono.oneofzero.OneOfZero;
import com.chrono.oneofzero.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ZeroBlockStates extends BlockStateProvider {
	
	public ZeroBlockStates(DataGenerator gen, ExistingFileHelper helper){ super(gen, OneOfZero.MODID, helper); }
	
	@Override
	protected void registerStatesAndModels () {
		simpleBlock(Registration.ZERO_ORE_OW.get());
		simpleBlock(Registration.ZERO_ORE_NETHER.get());
		simpleBlock(Registration.ZERO_ORE_END.get());
		simpleBlock(Registration.ZERO_ORE_DS.get());
	}
}
