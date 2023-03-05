package sunsetsatellite.guidebookpp;

import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;

import java.util.ArrayList;

public interface IRecipeHandlerBase {
    ContainerGuidebookRecipeBase getContainer(Object o);

    int getRecipeAmount();

    ArrayList<?> getRecipes();

    ArrayList<?> getRecipesFiltered(ItemStack filter, boolean usage);
}
