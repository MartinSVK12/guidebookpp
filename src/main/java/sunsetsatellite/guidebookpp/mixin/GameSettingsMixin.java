package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.core.option.GameSettings;
import net.minecraft.core.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import sunsetsatellite.guidebookpp.IKeybinds;

@Mixin(
        value = GameSettings.class,
        remap = false
)
public class GameSettingsMixin
    implements IKeybinds
{
    public KeyBinding keyViewRecipe = new KeyBinding("key.viewRecipe",19);
    public KeyBinding keyViewUsage = new KeyBinding("key.viewUsage",22);

    public KeyBinding getKeyViewRecipe() {
        return keyViewRecipe;
    }
    public KeyBinding getKeyViewUsage() {
        return keyViewUsage;
    }
}
