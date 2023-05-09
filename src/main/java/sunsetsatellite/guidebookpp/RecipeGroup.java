package sunsetsatellite.guidebookpp;

import net.minecraft.src.Block;
import net.minecraft.src.ContainerGuidebookRecipeBase;
import org.jetbrains.annotations.NotNull;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;
import sunsetsatellite.guidebookpp.recipes.RecipeBase;

import java.util.ArrayList;

public class RecipeGroup {
    @NotNull public final String modId;
    @NotNull public final Block machine;
    @NotNull public ArrayList<RecipeBase> recipes;
    @NotNull public final IRecipeHandlerBase handler;

    public RecipeGroup(@NotNull String modId, @NotNull Block machine, @NotNull IRecipeHandlerBase handler, @NotNull ArrayList<? extends RecipeBase> recipes) {
        this.modId = modId;
        this.machine = machine;
        this.recipes = (ArrayList<RecipeBase>) recipes;
        this.handler = handler;
        for (RecipeBase recipe : recipes) {
            recipe.group = this;
        }
    }

    public void addRecipe(RecipeBase recipeBase){
        recipeBase.group = this;
        recipes.add(recipeBase);
    }

    public RecipeBase findRecipe(RecipeBase recipe){
        for (RecipeBase recipeBase : recipes) {
            if(recipe.equals(recipeBase)){
                return recipeBase;
            }
        }
        return null;
    }

    public int getRecipeAmount(){
        return recipes.size();
    }

    @Override
    public String toString() {
        return "RecipeGroup{" +
                "modId='" + modId + '\'' +
                ", machine=" + machine +
                ", recipes=" + recipes.size() +
                '}';
    }
}
