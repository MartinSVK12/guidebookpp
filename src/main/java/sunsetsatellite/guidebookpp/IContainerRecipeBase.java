package sunsetsatellite.guidebookpp;

import net.minecraft.client.gui.GuiGuidebook;
import sunsetsatellite.guidebookpp.recipes.RecipeBase;

public interface IContainerRecipeBase {
    void drawContainer(GuiGuidebook guidebook, int xSize, int ySize, int index, RecipeBase recipe);
}
