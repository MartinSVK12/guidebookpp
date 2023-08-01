package sunsetsatellite.guidebookpp;

import sunsetsatellite.guidebookpp.handlers.RecipeHandlerBlastFurnace;
import sunsetsatellite.guidebookpp.handlers.RecipeHandlerCrafting;
import sunsetsatellite.guidebookpp.handlers.RecipeHandlerFurnace;
import org.slf4j.Logger;

public class DefaultPlugin implements GuidebookCustomRecipePlugin {
    @Override
    public void initializePlugin(Logger logger) {
        logger.info("Loading default recipe handlers..");
        new RecipeHandlerFurnace().addRecipes();
        new RecipeHandlerBlastFurnace().addRecipes();
        new RecipeHandlerCrafting().addRecipes();

    }
}
