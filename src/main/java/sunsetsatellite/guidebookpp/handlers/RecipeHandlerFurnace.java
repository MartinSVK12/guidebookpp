package sunsetsatellite.guidebookpp.handlers;

import net.minecraft.src.*;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;
import sunsetsatellite.guidebookpp.recipes.RecipeBlastFurnace;
import sunsetsatellite.guidebookpp.recipes.RecipeFurnace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class RecipeHandlerFurnace
        implements IRecipeHandlerBase
{

    public ContainerGuidebookRecipeBase getContainer(Object o) {
        RecipeFurnace recipe = (RecipeFurnace) o;
        return new ContainerGuidebookRecipeFurnace(new ItemStack(Block.furnaceStoneActive),recipe.input,recipe.output);
    }


    public int getRecipeAmount() {
        return getRecipes().size();
    }

    public ArrayList<?> getRecipes() {
        HashMap<Integer,ItemStack> rawRecipes = new HashMap<>(RecipesFurnace.smelting().getSmeltingList());
        ArrayList<RecipeFurnace> recipes = new ArrayList<>();
        rawRecipes.forEach((I,O)->{
            recipes.add(new RecipeFurnace(new ItemStack(I,1,0),O));
        });
        return recipes;
    }

    public ArrayList<?> getRecipesFiltered(ItemStack filter, boolean usage) {
        HashMap<Integer,ItemStack> rawRecipes = new HashMap<>(RecipesFurnace.smelting().getSmeltingList());
        ArrayList<RecipeFurnace> recipes = new ArrayList<>();
        rawRecipes.forEach((I,O)->{
            if(usage){
                if(O.isItemEqual(filter)){
                    recipes.add(new RecipeFurnace(new ItemStack(I,1,0),O));
                }
            } else {
                if(new ItemStack(I,1,0).isItemEqual(filter)){
                    recipes.add(new RecipeFurnace(new ItemStack(I,1,0),O));
                }
            }
        });
        return recipes;
    }

    @Override
    public ArrayList<?> getRecipesFiltered(String name) {
        if(name.equals("")){
            return getRecipes();
        }
        HashMap<Integer,ItemStack> rawRecipes = new HashMap<>(RecipesFurnace.smelting().getSmeltingList());
        ArrayList<RecipeFurnace> recipes = new ArrayList<>();
        rawRecipes.forEach((I,O)->{
            recipes.add(new RecipeFurnace(new ItemStack(I,1,0),O));
        });
        recipes.removeIf((R)->!getNameOfRecipeOutput(R).contains(name.toLowerCase()));
        return recipes;
    }

    public String getNameOfRecipeOutput(Object recipe){
        StringTranslate trans = StringTranslate.getInstance();
        return trans.translateKey(((RecipeFurnace)recipe).output.getItemName()+".name").toLowerCase();
    }

    @Override
    public String getHandlerName() {
        return "furnace";
    }
}
