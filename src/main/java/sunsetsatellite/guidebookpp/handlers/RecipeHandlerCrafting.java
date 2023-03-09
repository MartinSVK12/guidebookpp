package sunsetsatellite.guidebookpp.handlers;

import net.minecraft.src.*;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;
import sunsetsatellite.guidebookpp.recipes.RecipeBlastFurnace;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        ArrayList<?> list = (ArrayList<?>) ((ArrayList<?>) CraftingManager.getInstance().getRecipeList()).clone();
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

    @Override
    public ArrayList<?> getRecipesFiltered(String name) {
        if(name.equals("")){
            return getRecipes();
        }
        CraftingManager craftingManager = CraftingManager.getInstance();
        ArrayList<IRecipe> foundRecipes = new ArrayList<>();
        ArrayList<IRecipe> recipes = (ArrayList<IRecipe>) ((ArrayList<IRecipe>) craftingManager.getRecipeList()).clone();
        recipes.removeIf((R)->!(R instanceof RecipeShaped) && !(R instanceof RecipeShapeless));
        recipes.removeIf((R)->!(getNameOfRecipeOutput(R).contains(name.toLowerCase())));
        return recipes;
    }

    public String getNameOfRecipeOutput(Object recipe){
        StringTranslate trans = StringTranslate.getInstance();
        //GuidebookPlusPlus.LOGGER.info(recipe.toString()+" "+trans.translateKey(((IRecipe)recipe).getRecipeOutput().getItemName()+".name"));
        return trans.translateKey(((IRecipe)recipe).getRecipeOutput().getItemName()+".name").toLowerCase();
    }

    @Override
    public String getHandlerName() {
        return "crafting";
    }
}
