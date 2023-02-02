package sunsetsatellite.guidebookpp;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


public class GuidebookPlusPlus implements ModInitializer {
    public static final String MOD_ID = "guidebookpp";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static ItemStack focus = null;
    public static boolean isUsage = false;
    public static Slot lastSlotHovered = null;

    public static Minecraft mc = null;

    public static ArrayList<IRecipe> findRecipesByOutput(ItemStack output){
        CraftingManager craftingManager = CraftingManager.getInstance();
        ArrayList<IRecipe> foundRecipes = new ArrayList<>();
        List<IRecipe> recipes = craftingManager.getRecipeList();
        for(IRecipe recipe : recipes){
            if(recipe.getRecipeOutput().isItemEqual(output)){
                foundRecipes.add(recipe);
            }
        }
        return foundRecipes;
    }

    public static ArrayList<IRecipe> findRecipesByInput(ItemStack input){
        CraftingManager craftingManager = CraftingManager.getInstance();
        ArrayList<IRecipe> foundRecipes = new ArrayList<>();
        List<IRecipe> recipes = craftingManager.getRecipeList();
        for(IRecipe recipe : recipes){
            ArrayList<ItemStack> inputs = getRecipeItems(recipe);
            for(ItemStack recipeInput : inputs){
                if(recipeInput.isItemEqual(input)){
                    foundRecipes.add(recipe);
                    break;
                }
            }
        }
        return foundRecipes;
    }

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

    @Override
    public void onInitialize() {
        LOGGER.info("Guidebook++ initialized.");
    }
}
