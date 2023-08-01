package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.client.gui.GuiGuidebook;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeFurnace;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IContainerRecipeBase;
import sunsetsatellite.guidebookpp.recipes.RecipeBase;

@Mixin(
        value = ContainerGuidebookRecipeFurnace.class,
        remap = false
)
public abstract class ContainerGuidebookRecipeFurnaceMixin extends ContainerGuidebookRecipeBase
    implements IContainerRecipeBase {

    @Shadow public int furnaceType;

    public void drawContainer(GuiGuidebook guidebook, int xSize, int ySize, int index, RecipeBase recipe){
        int i = GuidebookPlusPlus.mc.renderEngine.getTexture("/gui/guidebook.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuidebookPlusPlus.mc.renderEngine.bindTexture(i);
        int j = (guidebook.width - xSize) / 2;
        int k = (guidebook.height - ySize) / 2;
        int xPos = j + 29 + 158 * (index / 3);
        int yPos = k + 30 + 62 * (index % 3);
        int yOffset = 0; //determines what texture will be shown
        if (furnaceType == 0) {
            yOffset = 108;
        } else if (furnaceType == 1) {
            yOffset = 162;
        }
        guidebook.drawTexturedModalRect(xPos, yPos, 158, yOffset, 98, 54);
    };

}
