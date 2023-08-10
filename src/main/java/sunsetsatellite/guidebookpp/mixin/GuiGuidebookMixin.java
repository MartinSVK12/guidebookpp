package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.client.gui.*;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.ContainerGuidebook;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import net.minecraft.core.player.inventory.slot.Slot;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.guidebookpp.*;
import sunsetsatellite.guidebookpp.recipes.RecipeBase;

import java.util.ArrayList;

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

    private static ArrayList<RecipeBase> storedRecipes;

    @Shadow protected static int maxPage;

    @Shadow protected static int page;

    @Shadow protected int xSize;

    @Shadow protected int ySize;

    public GuiTextField nameField;

    public GuiGuidebookMixin(Container container) {
        super(container);
    }
    @Inject(
            method = "initGui",
            at = @At("HEAD")
    )
    public void initGui(CallbackInfo ci){
        nameField = new GuiTextField((GuiGuidebook)(Object)this, fontRenderer,Math.round(width / 2 - (xSize/2)),Math.round(height/2 - (ySize/2)) - 28,xSize,20,"","");
        focusRecipe();
    }

    @Override
    public void mouseClicked(int i1, int i2, int i3) {
        nameField.mouseClicked(i1, i2, i3);
        super.mouseClicked(i1, i2, i3);
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
    public void keyTyped(char c, int i, int mouseX, int mouseY, CallbackInfo ci){
        if(nameField.isFocused) {
            Keyboard.enableRepeatEvents(true);
            if (c == 27) {
                Keyboard.enableRepeatEvents(false);
                nameField.setFocused(false);
                this.mc.thePlayer.closeScreen();
                GuidebookPlusPlus.focus = null;
                ci.cancel();
            } else nameField.textboxKeyTyped(c, i);
            GuidebookPlusPlus.nameFocus = nameField.getText();
            focusRecipe();
            updatePages();
            ci.cancel();
        }
        if(c == 27 && !nameField.isFocused){
            this.mc.thePlayer.closeScreen();
            GuidebookPlusPlus.focus = null;
            ci.cancel();
        }
        if(this.mc.gameSettings.keyInventory.isEventKey() && !nameField.isFocused){
            this.mc.thePlayer.closeScreen();
            this.mc.displayGuiScreen(((MinecraftAccessor)this.mc).callGetGuiInventory());
            GuidebookPlusPlus.focus = null;
            ci.cancel();
        }
        Slot slot = GuidebookPlusPlus.lastSlotHovered;
        if(slot != null && ((IKeybinds)this.mc.gameSettings).getKeyViewRecipe().isEventKey() && !nameField.isFocused){
            if(!slot.discovered && GuidebookPlusPlus.config.getBoolean("disableLookupOfUndiscoveredItems")){
                ci.cancel();
                return;
            }
            GuidebookPlusPlus.focus = slot.getStack();
            GuidebookPlusPlus.isUsage = false;
            GuidebookPlusPlus.mc.thePlayer.displayGUIGuidebook();
        } else if(slot != null && ((IKeybinds)this.mc.gameSettings).getKeyViewUsage().isEventKey() && !nameField.isFocused){
            if(!slot.discovered && GuidebookPlusPlus.config.getBoolean("disableLookupOfUndiscoveredItems")){
                return;
            }
            GuidebookPlusPlus.focus = slot.getStack();
            GuidebookPlusPlus.isUsage = true;
            GuidebookPlusPlus.mc.thePlayer.displayGUIGuidebook();
        } else if (slot == null && this.mc.gameSettings.keyGuidebook.isEventKey() && !nameField.isFocused) {
            this.mc.thePlayer.closeScreen();
            GuidebookPlusPlus.focus = null;
        }
        ci.cancel();
    }

    @Shadow
    protected abstract void updatePages();

    public void focusRecipe(){
        page = 0;
        totalRecipes = 0;

        if(GuidebookPlusPlus.focus != null) {
            if(GuidebookPlusPlus.isUsage){
                totalRecipes = RecipeRegistry.findRecipesByInput(GuidebookPlusPlus.focus).size();
            } else {
                totalRecipes = RecipeRegistry.findRecipesByOutput(GuidebookPlusPlus.focus).size();
            }
        } else if (!(GuidebookPlusPlus.nameFocus.equals(""))) {
            //mod search
            if(GuidebookPlusPlus.nameFocus.startsWith("@")){
                //combined mod and machine search
                if(GuidebookPlusPlus.nameFocus.contains(">")){
                    String[] str = GuidebookPlusPlus.nameFocus.split(">",-1);
                    ArrayList<RecipeGroup> modIdGroups = RecipeRegistry.getGroupsByModId(str[0].replace("@",""));
                    ArrayList<RecipeGroup> machineGroups = RecipeRegistry.getGroupsByMachineName(str[1]);
                    modIdGroups.removeIf((G)-> !machineGroups.contains(G));
                    for (RecipeGroup modIdGroup : modIdGroups) {
                        totalRecipes += modIdGroup.recipes.size();
                    }

                } else {
                    for (RecipeGroup group : RecipeRegistry.getGroupsByModId(GuidebookPlusPlus.nameFocus.replace("@", ""))) {
                        totalRecipes += group.getRecipeAmount();
                    }
                }
            //machine search
            } else if (GuidebookPlusPlus.nameFocus.startsWith(">")) {
                for (RecipeGroup group : RecipeRegistry.getGroupsByMachineName(GuidebookPlusPlus.nameFocus.replace(">", ""))) {
                    totalRecipes += group.getRecipeAmount();
                }
            } else {
                totalRecipes = RecipeRegistry.findRecipesThatContain(GuidebookPlusPlus.nameFocus).size();
            }

        } else {
            totalRecipes = RecipeRegistry.getRecipeAmount();
        }

        maxPage = totalRecipes / 6;
        if(Math.floorMod(totalRecipes,6) == 0){
            maxPage--;
        }
        storedRecipes = new ArrayList<>();
        storedRecipes.ensureCapacity(totalRecipes);

        if(GuidebookPlusPlus.focus != null){
            if(GuidebookPlusPlus.isUsage){
                storedRecipes.addAll(RecipeRegistry.findRecipesByInput(GuidebookPlusPlus.focus));
            } else {
                storedRecipes.addAll(RecipeRegistry.findRecipesByOutput(GuidebookPlusPlus.focus));
            }
        } else if (!(GuidebookPlusPlus.nameFocus.equals(""))) {
            if(GuidebookPlusPlus.nameFocus.startsWith("@")){
                if(GuidebookPlusPlus.nameFocus.contains(">")){
                    String[] str = GuidebookPlusPlus.nameFocus.split(">",-1);
                    ArrayList<RecipeGroup> modIdGroups = RecipeRegistry.getGroupsByModId(str[0].replace("@",""));
                    ArrayList<RecipeGroup> machineGroups = RecipeRegistry.getGroupsByMachineName(str[1]);
                    modIdGroups.removeIf((G)-> !machineGroups.contains(G));
                    for (RecipeGroup modIdGroup : modIdGroups) {
                        storedRecipes.addAll(modIdGroup.recipes);
                    }
                } else {
                    for (RecipeGroup group : RecipeRegistry.getGroupsByModId(GuidebookPlusPlus.nameFocus.replace("@", ""))) {
                        storedRecipes.addAll(group.recipes);
                    }
                }
            } else if (GuidebookPlusPlus.nameFocus.startsWith(">")) {
                for (RecipeGroup group : RecipeRegistry.getGroupsByMachineName(GuidebookPlusPlus.nameFocus.replace(">", ""))) {
                    storedRecipes.addAll(group.recipes);
                }
            } else {
                storedRecipes.addAll(RecipeRegistry.findRecipesThatContain(GuidebookPlusPlus.nameFocus));
            }
        } else {
            storedRecipes.addAll(RecipeRegistry.getRecipes());
        }

        if(maxPage < 0){
            maxPage = 0;
        }
    }

    public void drawGuiContainerBackgroundLayer(float f) {
        nameField.drawTextBox();
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
                ((IContainerRecipeBase)this.recipes[r]).drawContainer(((GuiGuidebook) (Object) this),xSize,ySize,r, storedRecipes.get(page*6+r));
            }
        }

    }

    public void updateRecipesByPage(int page) {

        int startIndex = page * 6;
        this.recipes = new ContainerGuidebookRecipeBase[6];

        for(int i = 0; i < 6 && startIndex + i < totalRecipes; ++i) {
            this.recipes[i] = storedRecipes.get(startIndex+i).group.handler.getContainer(storedRecipes.get(startIndex+i));
        }


        ((ContainerGuidebook)this.inventorySlots).setRecipes(this.mc.thePlayer, this.recipes, this.mc.statFileWriter);
    }
}
