package fr.factionbedrock.aerialhell.Integration.REI;

import fr.factionbedrock.aerialhell.Client.Gui.Screen.Inventory.FreezerScreen;
import fr.factionbedrock.aerialhell.Client.Gui.Screen.Inventory.OscillatorScreen;
import fr.factionbedrock.aerialhell.Registry.AerialHellItems;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.block.Block;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.recipe.AbstractCookingRecipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.display.FurnaceRecipeDisplay;
import net.minecraft.recipe.display.RecipeDisplay;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractCookingCategory<T extends AbstractCookingRecipe> implements DisplayCategory<RecipeDisplayWrapper<T>>
{
    protected final boolean isOscillating;
    protected final CategoryIdentifier<RecipeDisplayWrapper<T>> categoryIdentifier;
    protected final Renderer icon;
    protected final Text title;

    public AbstractCookingCategory(CategoryIdentifier<RecipeDisplayWrapper<T>> categoryIdentifier, Block icon, String translationKey, boolean isOscillating)
    {
        this.categoryIdentifier = categoryIdentifier;
        this.icon = EntryStacks.of(icon);
        this.title = Text.translatable(translationKey);
        this.isOscillating = isOscillating;
    }

    @Override
    public CategoryIdentifier<? extends RecipeDisplayWrapper<T>> getCategoryIdentifier()
    {
        return categoryIdentifier;
    }

    @Override
    public Renderer getIcon()
    {
        return icon;
    }

    @Override
    public Text getTitle()
    {
        return title;
    }

    @Override
    public List<Widget> setupDisplay(RecipeDisplayWrapper<T> display, Rectangle bounds)
    {
        List<Widget> widgets = new ArrayList<>();
        Point startPoint = new Point(bounds.getCenterX() - 41, bounds.getCenterY() - 27);

        widgets.add(Widgets.createRecipeBase(bounds));

        RecipeEntry<T> recipeEntry = display.getRecipeEntry();
        T recipe = recipeEntry.value();
        java.util.List<RecipeDisplay> displays = recipe.getDisplays();

        if (!displays.isEmpty() && displays.getFirst() instanceof FurnaceRecipeDisplay furnaceRecipeDisplay)
        {
            // Input slot
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 1, startPoint.y + 1))
                    .entries(EntryIngredients.ofIngredient(furnaceRecipeDisplay.ingredient()))
                    .markInput());

            // Fuel slot
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 1, startPoint.y + 37))
                    .entries(List.of(EntryStacks.of(this.isOscillating ? AerialHellItems.FLUORITE : AerialHellItems.MAGMATIC_GEL)))
                    .disableBackground()
                    .markInput());

            // Output slot
            widgets.add(Widgets.createSlot(new Point(startPoint.x + 61, startPoint.y + 19))
                    .entries(EntryIngredients.ofIngredient(furnaceRecipeDisplay.result()))
                    .disableBackground()
                    .markOutput());

            // Arrow
            widgets.add(Widgets.createArrow(new Point(startPoint.x + 26, startPoint.y + 17)));

            // Icon (wave or flake)
            if (this.isOscillating)
            {
                widgets.add(Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> {
                    graphics.drawTexture(RenderPipelines.GUI_TEXTURED, OscillatorScreen.OSCILLATOR_GUI_TEXTURES, startPoint.x + 2, startPoint.y + 20, 57.0F, 36.0F, 13, 13, 256, 256);
                }));
            }
            else
            {
                widgets.add(Widgets.createDrawableWidget((graphics, mouseX, mouseY, delta) -> {
                    graphics.drawTexture(RenderPipelines.GUI_TEXTURED, FreezerScreen.FREEZER_GUI_TEXTURES, startPoint.x + 2, startPoint.y + 20, 57.0F, 36.0F, 13, 13, 256, 256);
                }));
            }
        }

        return widgets;
    }

    @Override
    public int getDisplayHeight()
    {
        return 54;
    }

    @Override
    public int getDisplayWidth(RecipeDisplayWrapper<T> display)
    {
        return 82;
    }
}
