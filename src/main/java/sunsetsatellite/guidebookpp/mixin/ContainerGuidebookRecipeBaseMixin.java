package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.client.gui.GuiGuidebook;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import org.spongepowered.asm.mixin.Mixin;
import sunsetsatellite.guidebookpp.IContainerRecipeBase;
import sunsetsatellite.guidebookpp.recipes.RecipeBase;

@Mixin(
        value = ContainerGuidebookRecipeBase.class,
        remap = false
)
public abstract class ContainerGuidebookRecipeBaseMixin implements IContainerRecipeBase {

    public abstract void drawContainer(GuiGuidebook guidebook, int xSize, int ySize, int index, RecipeBase recipe);

}
