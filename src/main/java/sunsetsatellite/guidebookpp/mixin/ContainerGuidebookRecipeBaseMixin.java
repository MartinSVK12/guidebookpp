package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.core.gui.GuiGuidebook;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import org.spongepowered.asm.mixin.Mixin;
import sunsetsatellite.guidebookpp.IContainerRecipeBase;

@Mixin(
        value = ContainerGuidebookRecipeBase.class,
        remap = false
)
public abstract class ContainerGuidebookRecipeBaseMixin implements IContainerRecipeBase {

    public abstract void drawContainer(GuiGuidebook guidebook, int xSize, int ySize);
}
