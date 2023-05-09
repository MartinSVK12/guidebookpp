package sunsetsatellite.guidebookpp;

import net.minecraft.src.ContainerGuidebookRecipeBase;
import net.minecraft.src.ItemStack;

import java.util.ArrayList;

public interface IRecipeHandlerBase {
    ContainerGuidebookRecipeBase getContainer(Object o);

    void addRecipes();

    /*int getRecipeAmount();

    ArrayList<?> getRecipes();

    ArrayList<?> getRecipesFiltered(ItemStack filter, boolean usage);

    ArrayList<?> getRecipesFiltered(String name);

    String getNameOfRecipeOutput(Object recipe);

    String getHandlerName();*/
}
