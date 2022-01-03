package com.chrono.oneofzero.datagen;

import com.chrono.oneofzero.OneOfZero;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = OneOfZero.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
	
	@SubscribeEvent
	public static void gatherData(GatherDataEvent event){
		DataGenerator generator = event.getGenerator();
		if(event.includeServer()){
			generator.addProvider(new ZeroRecipes(generator));
			//.addProvider(new ZeroLootTables(generator));
			ZeroBlockTags blockTags = new ZeroBlockTags(generator, event.getExistingFileHelper());
			generator.addProvider(blockTags);
			generator.addProvider(new ZeroItemTags(generator, blockTags, event.getExistingFileHelper()));
		}
		if(event.includeClient()){
			generator.addProvider(new ZeroBlockStates(generator, event.getExistingFileHelper()));
			generator.addProvider(new ZeroItemModels(generator, event.getExistingFileHelper()));
			generator.addProvider(new ZeroLanguageProvider(generator, "en_us"));
		}
		
	}
}
