package sunsetsatellite.guidebookpp.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(
        value = Minecraft.class,
        remap = false
)
public interface MinecraftAccessor {
    @Invoker
    GuiInventory callGetGuiInventory();
}
