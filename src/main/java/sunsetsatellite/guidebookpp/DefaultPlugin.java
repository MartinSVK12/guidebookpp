package sunsetsatellite.guidebookpp;

import net.minecraft.core.crafting.recipe.IRecipe;
import sunsetsatellite.guidebookpp.handlers.RecipeHandlerBlastFurnace;
import sunsetsatellite.guidebookpp.handlers.RecipeHandlerCrafting;
import sunsetsatellite.guidebookpp.handlers.RecipeHandlerFurnace;
import sunsetsatellite.guidebookpp.recipes.RecipeBlastFurnace;
import sunsetsatellite.guidebookpp.recipes.RecipeFurnace;
import org.slf4j.Logger;

public class DefaultPlugin implements GuidebookCustomRecipePlugin {
    @Override
    public void initializePlugin(CustomGuidebookRecipeRegistry registry, Logger logger) {
        logger.info("Loading default recipe handlers..");
        registry.addHandler(new RecipeHandlerCrafting(), IRecipe.class);
        registry.addHandler(new RecipeHandlerFurnace(), RecipeFurnace.class);
        registry.addHandler(new RecipeHandlerBlastFurnace(), RecipeBlastFurnace.class);
    }
}
