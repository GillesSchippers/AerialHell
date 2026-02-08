package fr.factionbedrock.aerialhell.Integration.REI;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.display.FurnaceRecipeDisplay;
import net.minecraft.recipe.display.RecipeDisplay;

import java.util.ArrayList;
import java.util.List;

public class RecipeDisplayWrapper<T extends AbstractCookingRecipe> implements Display
{
    private final RecipeEntry<T> recipeEntry;
    private final CategoryIdentifier<RecipeDisplayWrapper<T>> categoryIdentifier;
    private final List<EntryIngredient> inputs;
    private final List<EntryIngredient> outputs;

    public RecipeDisplayWrapper(RecipeEntry<T> recipeEntry, CategoryIdentifier<RecipeDisplayWrapper<T>> categoryIdentifier)
    {
        this.recipeEntry = recipeEntry;
        this.categoryIdentifier = categoryIdentifier;

        T recipe = recipeEntry.value();
        java.util.List<RecipeDisplay> displays = recipe.getDisplays();

        this.inputs = new ArrayList<>();
        this.outputs = new ArrayList<>();

        if (!displays.isEmpty() && displays.getFirst() instanceof FurnaceRecipeDisplay furnaceRecipeDisplay)
        {
            this.inputs.add(EntryIngredients.ofIngredient(furnaceRecipeDisplay.ingredient()));
            this.outputs.add(EntryIngredients.ofIngredient(furnaceRecipeDisplay.result()));
        }
    }

    public RecipeEntry<T> getRecipeEntry()
    {
        return recipeEntry;
    }

    @Override
    public List<EntryIngredient> getInputEntries()
    {
        return inputs;
    }

    @Override
    public List<EntryIngredient> getOutputEntries()
    {
        return outputs;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier()
    {
        return categoryIdentifier;
    }
}
