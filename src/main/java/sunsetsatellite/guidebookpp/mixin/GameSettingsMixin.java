package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.client.option.GameSettings;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
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
