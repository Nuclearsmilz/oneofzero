package com.chrono.oneofzero.datagen;

import com.chrono.oneofzero.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ZeroRecipes extends RecipeProvider {
	
	public ZeroRecipes (DataGenerator generatorIn) {
		super(generatorIn);
	}
	
	@Override
	protected void buildCraftingRecipes (@NotNull Consumer<FinishedRecipe> consumer) {
		// Tag (Ore) to Ingot
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registration.ZERO_ORE_ITEM),
						Registration.ZERO_INGOT.get(), 1.0f, 100)
				.unlockedBy("has_ore", has(Registration.ZERO_ORE_ITEM))
				.save(consumer, "zero_ingot_from_ore");
		// Chunk to Ingot
		SimpleCookingRecipeBuilder.smelting(Ingredient.of(Registration.RAW_ZERO_CHUNK.get()),
						Registration.ZERO_INGOT.get(), 0.0f, 100)
				.unlockedBy("has_chunk", has(Registration.RAW_ZERO_CHUNK.get()))
				.save(consumer, "zero_ingot_from_chunk");
		
		
	}
}
