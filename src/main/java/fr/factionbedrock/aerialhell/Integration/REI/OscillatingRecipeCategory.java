package fr.factionbedrock.aerialhell.Integration.REI;

import fr.factionbedrock.aerialhell.AerialHell;
import fr.factionbedrock.aerialhell.Recipe.OscillatingRecipe;
import fr.factionbedrock.aerialhell.Registry.AerialHellBlocks;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;

public class OscillatingRecipeCategory extends AbstractCookingCategory<OscillatingRecipe>
{
    public static final CategoryIdentifier<RecipeDisplayWrapper<OscillatingRecipe>> OSCILLATING = 
        CategoryIdentifier.of(AerialHell.id("oscillating"));

    public OscillatingRecipeCategory()
    {
        super(OSCILLATING, AerialHellBlocks.OSCILLATOR, "block.aerialhell.oscillator", true);
    }
}
