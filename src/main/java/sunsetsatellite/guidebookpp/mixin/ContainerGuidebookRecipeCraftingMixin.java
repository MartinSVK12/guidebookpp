package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.src.ContainerGuidebookRecipeBase;
import net.minecraft.src.ContainerGuidebookRecipeCrafting;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiGuidebook;
import net.minecraft.src.command.ChatColor;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IContainerRecipeBase;
import sunsetsatellite.guidebookpp.recipes.RecipeBase;

@Mixin(
        value = ContainerGuidebookRecipeCrafting.class,
        remap = false
)
public abstract class ContainerGuidebookRecipeCraftingMixin extends ContainerGuidebookRecipeBase
    implements IContainerRecipeBase{

    public void drawContainer(GuiGuidebook guidebook, int xSize, int ySize, int index, RecipeBase recipe){
        int i = GuidebookPlusPlus.mc.renderEngine.getTexture("/gui/guidebook.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuidebookPlusPlus.mc.renderEngine.bindTexture(i);
        int j = (guidebook.width - xSize) / 2;
        int k = (guidebook.height - ySize) / 2;
        int xPos = j + 29 + 158 * (index / 3);
        int yPos = k + 30 + 62 * (index % 3);
        int yOffset = 0; //determines what texture will be shown
        if (inventorySlots.size() > 5) {
            yOffset = 54;
        }
        guidebook.drawTexturedModalRect(xPos, yPos, 158, yOffset, 98, 54);
    };

}
