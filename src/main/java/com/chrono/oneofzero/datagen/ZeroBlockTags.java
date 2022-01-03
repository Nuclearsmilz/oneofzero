package com.chrono.oneofzero.datagen;

import com.chrono.oneofzero.OneOfZero;
import com.chrono.oneofzero.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class ZeroBlockTags extends BlockTagsProvider {
	public ZeroBlockTags (DataGenerator generator, ExistingFileHelper helper) {
		super(generator, OneOfZero.MODID, helper);
	}
	
	@Override
	protected void addTags () {
		tag(BlockTags.MINEABLE_WITH_PICKAXE)
				.add(Registration.ZERO_ORE_OW.get())
				.add(Registration.ZERO_ORE_NETHER.get())
				.add(Registration.ZERO_ORE_END.get())
				.add(Registration.ZERO_ORE_DS.get());
		tag(BlockTags.NEEDS_IRON_TOOL)
				.add(Registration.ZERO_ORE_OW.get())
				.add(Registration.ZERO_ORE_NETHER.get())
				.add(Registration.ZERO_ORE_END.get())
				.add(Registration.ZERO_ORE_DS.get());
		tag(Tags.Blocks.ORES)
				.add(Registration.ZERO_ORE_OW.get())
				.add(Registration.ZERO_ORE_NETHER.get())
				.add(Registration.ZERO_ORE_END.get())
				.add(Registration.ZERO_ORE_DS.get());
		tag(Registration.ZERO_ORE)
				.add(Registration.ZERO_ORE_OW.get())
				.add(Registration.ZERO_ORE_NETHER.get())
				.add(Registration.ZERO_ORE_END.get())
				.add(Registration.ZERO_ORE_DS.get());
	}
	
	@Override
	public @NotNull String getName () {
		return "One of Zero Tags";
	}
}

