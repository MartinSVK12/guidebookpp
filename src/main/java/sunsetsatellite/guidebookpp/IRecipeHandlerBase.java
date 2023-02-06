package sunsetsatellite.guidebookpp;

import net.minecraft.src.ContainerGuidebookRecipeBase;
import net.minecraft.src.ItemStack;

import java.util.ArrayList;

public interface IRecipeHandlerBase {
    ContainerGuidebookRecipeBase getContainer(Object o);

    int getRecipeAmount();

    ArrayList<?> getRecipes();

    ArrayList<?> getRecipesFiltered(ItemStack filter, boolean usage);
}
