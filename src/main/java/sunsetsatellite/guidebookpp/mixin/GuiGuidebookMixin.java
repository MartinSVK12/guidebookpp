package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.src.*;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IKeybinds;

import java.util.ArrayList;
import java.util.List;
@Mixin(
        value = GuiGuidebook.class,
        remap = false
)
public abstract class GuiGuidebookMixin extends GuiContainer {

    @Shadow protected ContainerGuidebookRecipeBase[] recipes;

    @Shadow public abstract void scroll(int direction);

    @Shadow private static int totalRecipes;

    @Shadow private static Object[] storedRecipes;

    @Shadow protected static int maxPage;

    @Shadow protected static int page;

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
        } else if(totalRecipes == 0 && GuidebookPlusPlus.isUsage){
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
        ArrayList<IRecipe> recipes;
        if(GuidebookPlusPlus.focus != null){
            page = 0;
            if(GuidebookPlusPlus.isUsage){
                recipes = GuidebookPlusPlus.findRecipesByInput(GuidebookPlusPlus.focus);
            } else {
                recipes = GuidebookPlusPlus.findRecipesByOutput(GuidebookPlusPlus.focus);
            }
        } else {
            recipes = (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList();
        }

        for (IRecipe recipe : recipes){
            if(recipe instanceof RecipeShapeless || recipe instanceof RecipeShaped){
                totalRecipes++;
            }
        }

        totalRecipes += RecipesFurnace.smelting().getSmeltingList().size();
        totalRecipes += RecipesBlastFurnace.smelting().getSmeltingList().size();

        maxPage = totalRecipes / 6;
        if(Math.floorMod(totalRecipes,6) == 0){
            maxPage--;
        }
        storedRecipes = new Object[totalRecipes];

        int i = 0;
        int j = 0;
        for (IRecipe recipe : recipes){
            if(recipe instanceof RecipeShapeless || recipe instanceof RecipeShaped){
                storedRecipes[j] = recipe;
                j++;
            }
        }

        ArrayList<Integer> keys = new ArrayList<Integer>(RecipesFurnace.smelting().getSmeltingList().keySet());
        ArrayList<Integer> keysClone = (ArrayList<Integer>) keys.clone();
        i = 0;
        if(GuidebookPlusPlus.focus != null){
            for(Integer key : keysClone){
                if( !((ItemStack)RecipesFurnace.smelting().getSmeltingList().get(keysClone.get(i))).isItemEqual(GuidebookPlusPlus.focus) ){
                    keys.remove(key);
                    totalRecipes--;
                    maxPage = totalRecipes / 6;
                    if(Math.floorMod(totalRecipes,6) == 0){
                        maxPage--;
                    }
                }
                i++;
            }
        }



        for(i = 0; i < keys.size(); i++) {
            storedRecipes[j] = new ItemStack[]{new ItemStack(Block.furnaceStoneActive), new ItemStack((Integer)keys.get(i), 1, 0), (ItemStack)RecipesFurnace.smelting().getSmeltingList().get(keys.get(i))};
            j++;
        }


        keys = new ArrayList(RecipesBlastFurnace.smelting().getSmeltingList().keySet());
        keysClone = (ArrayList<Integer>) keys.clone();
        i = 0;
        if(GuidebookPlusPlus.focus != null) {
            for (Integer key : keysClone) {
                if (!((ItemStack) RecipesBlastFurnace.smelting().getSmeltingList().get(keysClone.get(i))).isItemEqual(GuidebookPlusPlus.focus)) {
                    keys.remove(key);
                    totalRecipes--;
                    maxPage = totalRecipes / 6;
                    if (Math.floorMod(totalRecipes, 6) == 0) {
                        maxPage--;
                    }
                }
                i++;
            }
        }


        for(i = 0; i < keys.size(); i++) {
            storedRecipes[j] = new ItemStack[]{new ItemStack(Block.furnaceBlastActive), new ItemStack((Integer)keys.get(i), 1, 0), (ItemStack)RecipesBlastFurnace.smelting().getSmeltingList().get(keys.get(i))};
            j++;
        }
        if(maxPage < 0){
            maxPage = 0;
        }
    }

    public void drawGuiContainerBackgroundLayer(float f) {
        this.scroll(Mouse.getDWheel());
        int i = this.mc.renderEngine.getTexture("/gui/guidebook.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(i);
        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize / 2, this.ySize);
        this.drawTexturedModalRect(j + this.xSize / 2, k, 0, 0, this.xSize / 2, this.ySize);

        for(int q = 0; q < 6; ++q) {
            int xPos = j + 29 + 158 * (q / 3);
            int yPos = k + 30 + 62 * (q % 3);
            int yOffset = 0;
            if (this.recipes[q] instanceof ContainerGuidebookRecipeCrafting) {
                ContainerGuidebookRecipeCrafting r = (ContainerGuidebookRecipeCrafting)this.recipes[q];
                if (r.inventorySlots.size() > 5) {
                    yOffset = 54;
                } else {
                    yOffset = 0;
                }
            } else {
                if (!(this.recipes[q] instanceof ContainerGuidebookRecipeFurnace)) {
                    continue;
                }

                ContainerGuidebookRecipeFurnace r = (ContainerGuidebookRecipeFurnace)this.recipes[q];
                if (r.furnaceType == 0) {
                    yOffset = 108;
                } else if (r.furnaceType == 1) {
                    yOffset = 162;
                }
            }

            this.drawTexturedModalRect(xPos, yPos, 158, yOffset, 98, 54);
        }

    }
}
