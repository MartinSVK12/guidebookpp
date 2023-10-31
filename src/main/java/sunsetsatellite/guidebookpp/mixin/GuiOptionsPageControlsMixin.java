package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.options.GuiOptionsPageControls;
import net.minecraft.client.gui.options.GuiOptionsPageOptionBase;
import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.guidebookpp.IKeybinds;

@Mixin(value = GuiOptionsPageControls.class, remap = false)
public abstract class GuiOptionsPageControlsMixin extends GuiOptionsPageOptionBase {
    @Shadow protected abstract void addKeyBindingsCategory(String languageKey, KeyBinding... bindings);

    public GuiOptionsPageControlsMixin(GuiScreen parent, GameSettings settings) {
        super(parent, settings);
    }
    @Inject(method = "<init>(Lnet/minecraft/client/gui/GuiScreen;Lnet/minecraft/client/option/GameSettings;)V", at = @At("TAIL"))
    public void addGuidebookButtons(GuiScreen parent, GameSettings settings, CallbackInfo ci){
        IKeybinds keybinds = (IKeybinds)settings;
        this.addKeyBindingsCategory("options.guidebook++.category.keybinds", keybinds.getKeyViewRecipe(), keybinds.getKeyViewUsage());
    }
}
