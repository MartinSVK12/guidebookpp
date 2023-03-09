package sunsetsatellite.guidebookpp;

import net.minecraft.src.ContainerGuidebookRecipeBase;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StringTranslate;
import sunsetsatellite.guidebookpp.recipes.RecipeFurnace;

import java.util.ArrayList;

public interface IRecipeHandlerBase {
    ContainerGuidebookRecipeBase getContainer(Object o);

    int getRecipeAmount();

    ArrayList<?> getRecipes();

    ArrayList<?> getRecipesFiltered(ItemStack filter, boolean usage);

    ArrayList<?> getRecipesFiltered(String name);

    String getNameOfRecipeOutput(Object recipe);

    String getHandlerName();
}
