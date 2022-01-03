package com.chrono.oneofzero.datagen;

import com.chrono.oneofzero.OneOfZero;
import com.chrono.oneofzero.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.*;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.BlockItem;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class ZeroItemTags extends ItemTagsProvider {
	public ZeroItemTags (DataGenerator generator, BlockTagsProvider blockTags, ExistingFileHelper helper) {
		super(generator, blockTags, OneOfZero.MODID, helper);
	}
	
	@Override
	protected void addTags () {
		tag(Tags.Items.ORES)
				.add(Registration.ZERO_ORE_OW_ITEM.get())
				.add(Registration.ZERO_ORE_NETHER_ITEM.get())
				.add(Registration.ZERO_ORE_END_ITEM.get())
				.add(Registration.ZERO_ORE_DS_ITEM.get());
		tag(Tags.Items.INGOTS)
				.add(Registration.ZERO_INGOT.get());
		tag(Registration.ZERO_ORE_ITEM)
				.add(Registration.ZERO_ORE_OW_ITEM.get())
				.add(Registration.ZERO_ORE_NETHER_ITEM.get())
				.add(Registration.ZERO_ORE_END_ITEM.get())
				.add(Registration.ZERO_ORE_DS_ITEM.get());
	}
	
	@Override
	public @NotNull String getName () {
		return "One of Zero Tags";
	}
}