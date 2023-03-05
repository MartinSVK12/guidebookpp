package sunsetsatellite.guidebookpp;

import java.util.HashMap;

public class CustomGuidebookRecipeRegistry {

    public CustomGuidebookRecipeRegistry(){

    }

    public HashMap<Object, IRecipeHandlerBase> recipeHandlers = new HashMap<>();
    public HashMap<IRecipeHandlerBase, Object> recipeHandlersInv = new HashMap<>();

    public void addHandler(IRecipeHandlerBase handler, Class<?> recipeClass){
        GuidebookPlusPlus.LOGGER.info("Adding recipe handler: "+handler.getClass().getSimpleName());
        recipeHandlers.put(recipeClass,handler);
        recipeHandlersInv.put(handler,recipeClass);
    }
}
