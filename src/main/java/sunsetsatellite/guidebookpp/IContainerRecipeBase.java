package sunsetsatellite.guidebookpp;

import net.minecraft.src.GuiGuidebook;
import sunsetsatellite.guidebookpp.mixin.GuiGuidebookMixin;
import sunsetsatellite.guidebookpp.recipes.RecipeBase;

public interface IContainerRecipeBase {
    void drawContainer(GuiGuidebook guidebook, int xSize, int ySize, int index, RecipeBase recipe);
}
