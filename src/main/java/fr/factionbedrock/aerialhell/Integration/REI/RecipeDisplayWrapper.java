package fr.factionbedrock.aerialhell.Integration.REI;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.display.DisplaySerializer;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.display.FurnaceRecipeDisplay;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.recipe.display.SlotDisplay;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
            this.inputs.add(EntryIngredients.ofItemStacks(resolveSlotDisplay(furnaceRecipeDisplay.ingredient())));
            this.outputs.add(EntryIngredients.ofItemStacks(resolveSlotDisplay(furnaceRecipeDisplay.result())));
        }
    }

    /**
     * Converts a SlotDisplay to a list of ItemStacks for REI
     */
    private static List<ItemStack> resolveSlotDisplay(SlotDisplay slotDisplay)
    {
        List<ItemStack> stacks = new ArrayList<>();
        
        // Pattern match on the SlotDisplay type
        if (slotDisplay instanceof SlotDisplay.CompositeSlotDisplay compositeSlotDisplay)
        {
            // Handle composite displays by recursively resolving all contents
            for (SlotDisplay childDisplay : compositeSlotDisplay.contents())
            {
                stacks.addAll(resolveSlotDisplay(childDisplay));
            }
        }
        else if (slotDisplay instanceof SlotDisplay.ItemSlotDisplay itemSlotDisplay)
        {
            stacks.add(new ItemStack(itemSlotDisplay.item()));
        }
        else if (slotDisplay instanceof SlotDisplay.StackSlotDisplay stackSlotDisplay)
        {
            stacks.add(stackSlotDisplay.stack());
        }
        else if (slotDisplay instanceof SlotDisplay.TagSlotDisplay tagSlotDisplay)
        {
            // For tag displays, we need to resolve the tag
            // This is a simplified version - in production, you'd want to resolve the tag properly
            stacks.add(ItemStack.EMPTY);
        }
        else if (slotDisplay instanceof SlotDisplay.EmptySlotDisplay)
        {
            stacks.add(ItemStack.EMPTY);
        }
        
        return stacks;
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

    @Override
    public Optional<Identifier> getDisplayLocation()
    {
        // Return the recipe ID as the display location
        return Optional.of(recipeEntry.id().getValue());
    }

    @Override
    public DisplaySerializer<?> getSerializer()
    {
        // Return null as we don't need serialization for these dynamic displays
        return null;
    }
}
