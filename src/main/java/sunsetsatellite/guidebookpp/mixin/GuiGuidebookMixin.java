package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.src.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IContainerRecipeBase;
import sunsetsatellite.guidebookpp.IKeybinds;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;

import java.util.ArrayList;
import java.util.Objects;

@Debug(
        export = true
)
@Mixin(
        value = GuiGuidebook.class,
        remap = false
)
public abstract class GuiGuidebookMixin extends GuiContainer {

    @Shadow protected ContainerGuidebookRecipeBase[] recipes;

    @Shadow public abstract void scroll(int direction);

    @Shadow private static int totalRecipes;

    private static ArrayList<Object> storedRecipes;

    @Shadow protected static int maxPage;

    @Shadow protected static int page;

    @Shadow protected int xSize;

    @Shadow protected int ySize;

    public GuiGuidebookMixin(Container container) {
        super(container);
    }
    @Inject(
            method = "initGui",
            at = @At("HEAD")
    )
    public void initGui(CallbackInfo ci){
        focusRecipe();
    }

    @Inject(
            method = "drawGuiContainerForegroundLayer",
            at = @At("TAIL")
    )
    protected void drawGuiContainerForegroundLayer(CallbackInfo ci) {
        if(totalRecipes == 0 && !GuidebookPlusPlus.isUsage){
            this.drawStringNoShadow(this.fontRenderer, "No recipes.", -58, -3, 4210752);
        } else if(totalRecipes == 0){
            this.drawStringNoShadow(this.fontRenderer, "No usages.", -58, -3, 4210752);
        }
    }

    @Inject(
            method = "keyTyped",
            at = @At("HEAD"),
            cancellable = true
    )
    public void keyTyped(char c, int i, CallbackInfo ci){
        if(c == 27){
            this.mc.thePlayer.closeScreen();
            GuidebookPlusPlus.focus = null;
            ci.cancel();
        }
        if(this.mc.gameSettings.keyInventory.isEventKey()){
            this.mc.thePlayer.closeScreen();
            this.mc.displayGuiScreen(this.mc.thePlayer.getGamemode().getInventoryGui(this.mc.thePlayer));
            GuidebookPlusPlus.focus = null;
            ci.cancel();
        }
        Slot slot = GuidebookPlusPlus.lastSlotHovered;
        if(slot != null && ((IKeybinds)this.mc.gameSettings).getKeyViewRecipe().isEventKey()){
            GuidebookPlusPlus.focus = slot.getStack();
            GuidebookPlusPlus.isUsage = false;
            GuidebookPlusPlus.mc.thePlayer.displayGUIGuidebook();
        } else if(slot != null && ((IKeybinds)this.mc.gameSettings).getKeyViewUsage().isEventKey()){
            GuidebookPlusPlus.focus = slot.getStack();
            GuidebookPlusPlus.isUsage = true;
            GuidebookPlusPlus.mc.thePlayer.displayGUIGuidebook();
        } else if (slot == null && this.mc.gameSettings.keyGuidebook.isEventKey()) {
            this.mc.thePlayer.closeScreen();
            GuidebookPlusPlus.focus = null;
        }
        ci.cancel();
    }

    public void focusRecipe(){
        totalRecipes = 0;

        if(GuidebookPlusPlus.focus != null) {
            totalRecipes = GuidebookPlusPlus.getAllRecipesAmountFiltered(GuidebookPlusPlus.focus);
        } else {
            totalRecipes = GuidebookPlusPlus.getAllRecipesAmount();
        }

        maxPage = totalRecipes / 6;
        if(Math.floorMod(totalRecipes,6) == 0){
            maxPage--;
        }
        storedRecipes = new ArrayList<>();
        storedRecipes.ensureCapacity(totalRecipes);

        for(IRecipeHandlerBase handler : GuidebookPlusPlus.recipeHandlers.values()){
            if(GuidebookPlusPlus.focus != null){
                storedRecipes.addAll(handler.getRecipesFiltered(GuidebookPlusPlus.focus, GuidebookPlusPlus.isUsage));
            } else {
                storedRecipes.addAll(handler.getRecipes());
            }

        }

        if(maxPage < 0){
            maxPage = 0;
        }
    }

    public void drawGuiContainerBackgroundLayer(float f) {
        this.scroll(Mouse.getDWheel());
        int i = GuidebookPlusPlus.mc.renderEngine.getTexture("/gui/guidebook.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GuidebookPlusPlus.mc.renderEngine.bindTexture(i);
        int j = (this.width - xSize) / 2;
        int k = (this.height - ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, xSize / 2, ySize);
        this.drawTexturedModalRect(j + xSize / 2, k, 0, 0, xSize / 2, ySize);
        for(int r = 0; r < 6; r++){
            if(this.recipes[r] != null){
                ((IContainerRecipeBase)this.recipes[r]).drawContainer(((GuiGuidebook) (Object) this),xSize,ySize,r);
            }
        }

    }


    public void updateRecipesByPage(int page) {

        int startIndex = page * 6;
        this.recipes = new ContainerGuidebookRecipeBase[6];

        for(int i = 0; i < 6 && startIndex + i < totalRecipes; ++i) {
            //GuidebookPlusPlus.LOGGER.info(String.valueOf(storedRecipes.get(startIndex + i)));
            //GuidebookPlusPlus.LOGGER.info("handler: "+ GuidebookPlusPlus.getHandler(storedRecipes.get(startIndex + i).getClass()));
            this.recipes[i] = Objects.requireNonNull(GuidebookPlusPlus.getHandler(storedRecipes.get(startIndex + i).getClass()),"Invalid or unknown recipe handler!").getContainer(storedRecipes.get(startIndex + i));//GuidebookPlusPlus.recipeHandlers.get(storedRecipes.get(startIndex + i).getClass()).getContainer(storedRecipes.get(startIndex + i));
        }


        ((ContainerGuidebook)this.inventorySlots).setRecipes(this.mc.thePlayer, this.recipes, this.mc.statFileWriter);
    }
}
