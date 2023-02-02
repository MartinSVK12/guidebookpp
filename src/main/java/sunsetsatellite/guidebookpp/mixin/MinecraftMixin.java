package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;

@Mixin(
        value = Minecraft.class,
        remap = false
)
public abstract class MinecraftMixin {
    @Shadow
    private static Minecraft theMinecraft;

    @Inject(
            method = "startGame",
            remap = false,
            at = @At("TAIL")
    )
    public void startGame(CallbackInfo ci) {
        GuidebookPlusPlus.mc = theMinecraft;
    }

}



