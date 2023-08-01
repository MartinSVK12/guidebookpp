package sunsetsatellite.guidebookpp.handlers;

import net.minecraft.core.block.Block;
import net.minecraft.core.crafting.CraftingManager;
import net.minecraft.core.crafting.recipe.IRecipe;
import net.minecraft.core.crafting.recipe.RecipeShaped;
import net.minecraft.core.crafting.recipe.RecipeShapeless;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeCrafting;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;
import sunsetsatellite.guidebookpp.RecipeGroup;
import sunsetsatellite.guidebookpp.RecipeRegistry;
import sunsetsatellite.guidebookpp.recipes.RecipeCrafting;
import java.util.ArrayList;

public class RecipeHandlerCrafting
        implements IRecipeHandlerBase
{
    public ContainerGuidebookRecipeBase getContainer(Object o) {
        RecipeCrafting recipe = (RecipeCrafting) o;
        return new ContainerGuidebookRecipeCrafting(recipe.recipe);
    }

    @Override
    public void addRecipes() {
        GuidebookPlusPlus.LOGGER.info("Adding recipes for: " + this.getClass().getSimpleName());
        ArrayList<RecipeCrafting> recipes = new ArrayList<>();
        CraftingManager craftingManager = CraftingManager.getInstance();
        ArrayList<IRecipe> rawRecipes = (ArrayList<IRecipe>) ((ArrayList<IRecipe>) craftingManager.getRecipeList()).clone();
        rawRecipes.removeIf((R)->!(R instanceof RecipeShaped) && !(R instanceof RecipeShapeless));
        for (Object rawRecipe : rawRecipes) {
            recipes.add(new RecipeCrafting((IRecipe) rawRecipe));
        }
        RecipeGroup group = new RecipeGroup("minecraft", Block.workbench,this,recipes);
        RecipeRegistry.groups.add(group);
    }
}
