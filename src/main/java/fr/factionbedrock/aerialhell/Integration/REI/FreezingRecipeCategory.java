package fr.factionbedrock.aerialhell.Integration.REI;

import fr.factionbedrock.aerialhell.AerialHell;
import fr.factionbedrock.aerialhell.Recipe.FreezingRecipe;
import fr.factionbedrock.aerialhell.Registry.AerialHellBlocks;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;

public class FreezingRecipeCategory extends AbstractCookingCategory<FreezingRecipe>
{
    public static final CategoryIdentifier<RecipeDisplayWrapper<FreezingRecipe>> FREEZING = 
        CategoryIdentifier.of(AerialHell.id("freezing"));

    public FreezingRecipeCategory()
    {
        super(FREEZING, AerialHellBlocks.FREEZER, "block.aerialhell.freezer", false);
    }
}
