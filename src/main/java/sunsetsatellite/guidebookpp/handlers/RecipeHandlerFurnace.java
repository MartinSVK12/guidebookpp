package sunsetsatellite.guidebookpp.handlers;

import net.minecraft.core.block.Block;
import net.minecraft.core.crafting.recipe.RecipesFurnace;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeFurnace;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;
import sunsetsatellite.guidebookpp.recipes.RecipeBlastFurnace;
import sunsetsatellite.guidebookpp.recipes.RecipeFurnace;

import java.util.ArrayList;
import java.util.HashMap;

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
}
