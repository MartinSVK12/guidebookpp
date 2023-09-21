package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.client.gui.GuiContainer;
import net.minecraft.client.gui.GuiCrafting;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.core.player.inventory.Container;
import net.minecraft.core.player.inventory.ContainerWorkbench;
import net.minecraft.core.player.inventory.slot.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IKeybinds;

@Mixin(
        value = GuiContainer.class,
        remap = false
)
public abstract class GuiContainerMixin extends GuiScreen {

    @Shadow protected abstract Slot getSlotAtPosition(int i, int j);

    @Shadow public Container inventorySlots;

    @Shadow public abstract void onGuiClosed();

    @Inject(
            method = "keyTyped",
            at = @At("TAIL")
    )
    public void keyTyped(char c, int i, int mouseX, int mouseY, CallbackInfo ci){

        Slot slot = GuidebookPlusPlus.lastSlotHovered;
        if(slot != null && ((IKeybinds)this.mc.gameSettings).getKeyViewRecipe().isEventKey()){
            this.mc.thePlayer.closeScreen();
            onGuiClosed();
            GuidebookPlusPlus.focus = slot.getStack();
            GuidebookPlusPlus.isUsage = false;
            GuidebookPlusPlus.mc.thePlayer.displayGUIGuidebook();
        } else if(slot != null && ((IKeybinds)this.mc.gameSettings).getKeyViewUsage().isEventKey()){
            this.mc.thePlayer.closeScreen();
            onGuiClosed();
            GuidebookPlusPlus.focus = slot.getStack();
            GuidebookPlusPlus.isUsage = true;
            GuidebookPlusPlus.mc.thePlayer.displayGUIGuidebook();
        }

    }

    @Inject(
            method = "mouseMovedOrUp",
            at = @At("HEAD")
    )
    public void mouseMovedOrUp(int i, int j, int k, CallbackInfo ci) {
        GuidebookPlusPlus.lastSlotHovered = getSlotAtPosition(i,j);
    }

}
