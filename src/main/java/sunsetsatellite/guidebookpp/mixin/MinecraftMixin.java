package sunsetsatellite.guidebookpp.mixin;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.guidebookpp.GuidebookCustomRecipePlugin;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.RecipeRegistry;

@Mixin(
        value = Minecraft.class,
        remap = false
)
public class MinecraftMixin {

    @Inject(
            method = "startGame",
            at = @At(value = "INVOKE",target = "Lnet/minecraft/client/Minecraft;displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V", ordinal = 4, shift = At.Shift.BEFORE)
    )
    public void loadGbppPlugins(CallbackInfo ci){
        GuidebookPlusPlus.LOGGER.info("Loading plugins..");
        FabricLoader.getInstance().getEntrypointContainers("guidebookpp", GuidebookCustomRecipePlugin.class).forEach(plugin -> {
            plugin.getEntrypoint().initializePlugin(GuidebookPlusPlus.LOGGER);
        });
        GuidebookPlusPlus.LOGGER.info(String.format("Registered %d recipes in %d groups", RecipeRegistry.getRecipeAmount(),RecipeRegistry.getGroupAmount()));
    }
}
