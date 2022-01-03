package com.chrono.oneofzero.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class ZeroRecipes extends RecipeProvider {
	
	public ZeroRecipes(DataGenerator generatorIn) { super(generatorIn); }
	
	@Override
	protected void buildCraftingRecipes (@NotNull Consumer<FinishedRecipe> consumer) {
		super.buildCraftingRecipes(consumer);
	}
}
