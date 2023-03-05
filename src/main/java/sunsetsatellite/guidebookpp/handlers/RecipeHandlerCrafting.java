package sunsetsatellite.guidebookpp.handlers;

import net.minecraft.core.crafting.CraftingManager;
import net.minecraft.core.crafting.recipe.IRecipe;
import net.minecraft.core.crafting.recipe.RecipeShaped;
import net.minecraft.core.crafting.recipe.RecipeShapeless;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeCrafting;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;

import java.util.ArrayList;

public class RecipeHandlerCrafting
        implements IRecipeHandlerBase
{
    public ContainerGuidebookRecipeBase getContainer(Object o) {
        return new ContainerGuidebookRecipeCrafting((IRecipe)o);
    }

    public int getRecipeAmount() {
        return getRecipes().size();
    }

    public ArrayList<?> getRecipes() {
        ArrayList<?> list = (ArrayList<?>) CraftingManager.getInstance().getRecipeList();
        list.removeIf((R)->!(R instanceof RecipeShaped) && !(R instanceof RecipeShapeless));
        return list;
    }

    public ArrayList<?> getRecipesFiltered(ItemStack filter, boolean usage) {
        ArrayList<?> recipes;
        if(usage){
            recipes = GuidebookPlusPlus.findRecipesByInput(GuidebookPlusPlus.focus);
        } else {
            recipes = GuidebookPlusPlus.findRecipesByOutput(GuidebookPlusPlus.focus);
        }
        return recipes;
    }
}
