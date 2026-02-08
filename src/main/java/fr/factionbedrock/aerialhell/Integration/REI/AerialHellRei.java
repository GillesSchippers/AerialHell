package fr.factionbedrock.aerialhell.Integration.REI;

import fr.factionbedrock.aerialhell.Recipe.FreezingRecipe;
import fr.factionbedrock.aerialhell.Recipe.OscillatingRecipe;
import fr.factionbedrock.aerialhell.Registry.AerialHellItems;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.recipe.RecipeEntry;

public class AerialHellRei implements REIClientPlugin
{
    @Override
    public void registerCategories(CategoryRegistry registry)
    {
        registry.add(new OscillatingRecipeCategory());
        registry.add(new FreezingRecipeCategory());

        registry.addWorkstations(OscillatingRecipeCategory.OSCILLATING, EntryStacks.of(AerialHellItems.OSCILLATOR));
        registry.addWorkstations(FreezingRecipeCategory.FREEZING, EntryStacks.of(AerialHellItems.FREEZER));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry)
    {
        // Register oscillating recipes
        for (RecipeEntry<OscillatingRecipe> recipe : REIHelper.createOscillatingRecipeEntryList())
        {
            registry.add(new RecipeDisplayWrapper<>(recipe, OscillatingRecipeCategory.OSCILLATING));
        }

        // Register freezing recipes
        for (RecipeEntry<FreezingRecipe> recipe : REIHelper.createFreezingRecipeEntryList())
        {
            registry.add(new RecipeDisplayWrapper<>(recipe, FreezingRecipeCategory.FREEZING));
        }
    }
}
