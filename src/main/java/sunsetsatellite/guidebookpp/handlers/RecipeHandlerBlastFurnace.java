package sunsetsatellite.guidebookpp.handlers;

import net.minecraft.src.*;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;
import sunsetsatellite.guidebookpp.recipes.RecipeBlastFurnace;
import sunsetsatellite.guidebookpp.recipes.RecipeFurnace;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeHandlerBlastFurnace
        implements IRecipeHandlerBase
{
    public ContainerGuidebookRecipeBase getContainer(Object o) {
        RecipeBlastFurnace recipe = (RecipeBlastFurnace) o;
        return new ContainerGuidebookRecipeFurnace(new ItemStack(Block.furnaceBlastActive),recipe.input,recipe.output);
    }

    public int getRecipeAmount() {
        return getRecipes().size();
    }

    public ArrayList<?> getRecipes() {
        HashMap<Integer,ItemStack> rawRecipes = new HashMap<>(RecipesFurnace.smelting().getSmeltingList());
        ArrayList<RecipeBlastFurnace> recipes = new ArrayList<>();
        rawRecipes.forEach((I,O)->{
            recipes.add(new RecipeBlastFurnace(new ItemStack(I,1,0),O));
        });
        return recipes;
    }

    public ArrayList<?> getRecipesFiltered(ItemStack filter, boolean usage) {
        HashMap<Integer,ItemStack> rawRecipes = new HashMap<>(RecipesBlastFurnace.smelting().getSmeltingList());
        ArrayList<RecipeBlastFurnace> recipes = new ArrayList<>();
        rawRecipes.forEach((I,O)->{
            if(usage){
                if(O.isItemEqual(filter)){
                    recipes.add(new RecipeBlastFurnace(new ItemStack(I,1,0),O));
                }
            } else {
                if(new ItemStack(I,1,0).isItemEqual(filter)){
                    recipes.add(new RecipeBlastFurnace(new ItemStack(I,1,0),O));
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
        ArrayList<RecipeBlastFurnace> recipes = new ArrayList<>();
        rawRecipes.forEach((I,O)->{
            recipes.add(new RecipeBlastFurnace(new ItemStack(I,1,0),O));
        });
        recipes.removeIf((R)->!getNameOfRecipeOutput(R).contains(name.toLowerCase()));
        return recipes;
    }

    @Override
    public String getNameOfRecipeOutput(Object recipe){
        StringTranslate trans = StringTranslate.getInstance();
        return trans.translateKey(((RecipeBlastFurnace)recipe).output.getItemName()+".name").toLowerCase();
    }
}
