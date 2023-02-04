package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.src.StringTranslate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Properties;


@Mixin(
        value = StringTranslate.class,
        remap = false
)
/*
  Wacky hack to register keybind translation keys since it isn't done properly, apparently.
 */
public abstract class StringTranslateMixin {
    @Shadow private Properties translateTable;

    @Shadow
    public static StringTranslate getInstance() {
        return null;
    }

    @Inject(
            method = "<init>",
            at = @At("RETURN")
    )
    private void init(CallbackInfo ci){
        translateTable.put("key.viewRecipe","View Recipe");
        translateTable.put("key.viewUsage","View Usage");
    }
}
