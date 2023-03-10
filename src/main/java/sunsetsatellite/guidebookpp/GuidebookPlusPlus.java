package sunsetsatellite.guidebookpp;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sunsetsatellite.guidebookpp.handlers.RecipeHandlerCrafting;

import java.util.*;


public class GuidebookPlusPlus implements ModInitializer {
    public static final String MOD_ID = "guidebookpp";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static ItemStack focus = null;
    public static String nameFocus = "";
    public static boolean isUsage = false;
    public static Slot lastSlotHovered = null;

    public static Minecraft mc = null;

    public static CustomGuidebookRecipeRegistry recipeRegistry = new CustomGuidebookRecipeRegistry();

    public static ArrayList<IRecipe> findRecipesByOutput(ItemStack output){
        CraftingManager craftingManager = CraftingManager.getInstance();
        ArrayList<IRecipe> foundRecipes = new ArrayList<>();
        List<IRecipe> recipes = craftingManager.getRecipeList();
        recipes.removeIf((R)->!(R instanceof RecipeShaped) && !(R instanceof RecipeShapeless));
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
        recipes.removeIf((R)->!(R instanceof RecipeShaped) && !(R instanceof RecipeShapeless));
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

    public static int getAllRecipesAmount(){
        int amount = 0;
        for (IRecipeHandlerBase handler : recipeRegistry.recipeHandlers.values()) {
            amount += handler.getRecipeAmount();
        }
        return amount;
    }

    public static int getAllRecipesAmountFiltered(ItemStack filter){
        int amount = 0;
        for (IRecipeHandlerBase handler : recipeRegistry.recipeHandlers.values()) {
            amount += handler.getRecipesFiltered(filter,isUsage).size();
        }
        return amount;
    }

    public static int getAllRecipesAmountFiltered(String filter){
        if(filter.equals("")){
            return getAllRecipesAmount();
        }
        int amount = 0;
        ArrayList<?> recipes = new ArrayList<>();
        for (IRecipeHandlerBase handler : recipeRegistry.recipeHandlers.values()) {
            recipes = handler.getRecipesFiltered(filter);
        }
        amount = recipes.size();
        return amount;
    }

    public static IRecipeHandlerBase getHandler(Class<?> cls){
        //LOGGER.info("class: "+ cls);
        for (IRecipeHandlerBase handler : recipeRegistry.recipeHandlers.values()) {
           // LOGGER.info("recipe class bound to handler: "+ recipeHandlersInv.get(handler));
            if(((Class<?>) recipeRegistry.recipeHandlersInv.get(handler)).isAssignableFrom(cls)){
                return handler;
            }
        }
        return null;
    }

    @Override
    public void onInitialize() {
        LOGGER.info("Loading plugins..");
        FabricLoader.getInstance().getEntrypointContainers("guidebookpp", GuidebookCustomRecipePlugin.class).forEach(plugin -> {
            plugin.getEntrypoint().initializePlugin(recipeRegistry,LOGGER);
        });
        LOGGER.info("Guidebook++ initialized.");
    }
}
