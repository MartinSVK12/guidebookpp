package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.src.*;
import net.minecraft.src.command.ChatColor;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;

@Mixin(
        value = GuiContainer.class,
        remap = false
)
public abstract class GuiContainerMixin extends GuiScreen {

    @Shadow protected abstract Slot getSlotAtPosition(int i, int j);

    @Inject(
            method = "keyTyped",
            at = @At("TAIL")
    )
    public void keyTyped(char c, int i, CallbackInfo ci){
        Slot slot = GuidebookPlusPlus.lastSlotHovered;
        if(slot != null && c == 'r'){
            GuidebookPlusPlus.focus = slot.getStack();
            GuidebookPlusPlus.isUsage = false;
            GuidebookPlusPlus.mc.thePlayer.displayGUIGuidebook();
        } else if(slot != null && c == 'u'){
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
