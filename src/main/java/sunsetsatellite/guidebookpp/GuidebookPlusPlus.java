package sunsetsatellite.guidebookpp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.core.crafting.recipe.*;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.slot.Slot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;


public class GuidebookPlusPlus implements ModInitializer {
    public static final String MOD_ID = "guidebookpp";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static ItemStack focus = null;
    public static String nameFocus = "";
    public static boolean isUsage = false;
    public static Slot lastSlotHovered = null;

    public static Minecraft mc = Minecraft.getMinecraft(Minecraft.class);

    public static ArrayList<ItemStack> getRecipeItems(IRecipe recipe){
        ArrayList<ItemStack> inputs = new ArrayList<>();
        if(recipe instanceof RecipeShapeless){
            inputs = new ArrayList<ItemStack>(((RecipeShapeless) recipe).recipeItems);
        }
        if(recipe instanceof RecipeShaped){
            inputs = new ArrayList<>();
            Collections.addAll(inputs, ((RecipeShaped) recipe).recipeItems);
        }
        inputs.removeIf(Objects::isNull);
        for (ItemStack input : inputs) {
            input.stackSize = 1;
        }

        return inputs;
    }

    public static InventoryAutocrafting getCraftingInv(ArrayList<ItemStack> stacks){
        InventoryAutocrafting crafting = new InventoryAutocrafting(3,3);
        int i = 0;
        for(ItemStack stack : stacks){
            if(stack.itemID != 0 && stack.stackSize != 0){
                crafting.setInventorySlotContents(i,stack);
                i++;
            }
        }
        return crafting;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Loading plugins..");
        FabricLoader.getInstance().getEntrypointContainers("guidebookpp", GuidebookCustomRecipePlugin.class).forEach(plugin -> {
            plugin.getEntrypoint().initializePlugin(LOGGER);
        });
        LOGGER.info(String.format("Registered %d recipes in %d groups",RecipeRegistry.getRecipeAmount(),RecipeRegistry.getGroupAmount()));
        LOGGER.info("Guidebook++ initialized.");
    }

    public GuidebookPlusPlus(){
    }
}
