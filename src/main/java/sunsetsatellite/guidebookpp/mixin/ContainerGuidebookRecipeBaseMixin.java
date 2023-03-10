package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.src.ContainerGuidebookRecipeBase;
import net.minecraft.src.GuiGuidebook;
import org.spongepowered.asm.mixin.Mixin;
import sunsetsatellite.guidebookpp.IContainerRecipeBase;

@Mixin(
        value = ContainerGuidebookRecipeBase.class,
        remap = false
)
public abstract class ContainerGuidebookRecipeBaseMixin implements IContainerRecipeBase {

    public abstract void drawContainer(GuiGuidebook guidebook, int xSize, int ySize);
}
