package sunsetsatellite.guidebookpp.recipes;

import net.minecraft.core.crafting.recipe.IRecipe;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.lang.I18n;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.InventoryAutocrafting;

import java.util.ArrayList;

public class RecipeCrafting extends RecipeBase {

    public IRecipe recipe;

    public RecipeCrafting(IRecipe recipe){
        this.recipe = recipe;
    }


    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof RecipeCrafting)){
            return false;
        }
        //hack to get minecraft to match recipes for me
        ArrayList<ItemStack> otherItems = GuidebookPlusPlus.getRecipeItems(((RecipeCrafting) obj).recipe);
        InventoryAutocrafting inv = GuidebookPlusPlus.getCraftingInv(otherItems);
        return recipe.matches(inv);
    }

    @Override
    public boolean contains(Object obj) {
        if(!(obj instanceof ItemStack)){
            return false;
        }
        ArrayList<ItemStack> recipeItems = GuidebookPlusPlus.getRecipeItems(recipe);
        for (ItemStack recipeItem : recipeItems) {
            if(recipeItem.isItemEqual((ItemStack) obj)){
                return true;
            }
        }
        return recipe.getRecipeOutput().isItemEqual((ItemStack) obj);
    }

    @Override
    public boolean containsInput(Object obj) {
        if(!(obj instanceof ItemStack)){
            return false;
        }
        ArrayList<ItemStack> recipeItems = GuidebookPlusPlus.getRecipeItems(recipe);
        for (ItemStack recipeItem : recipeItems) {
            if(recipeItem.isItemEqual((ItemStack) obj)){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsOutput(Object obj) {
        if(!(obj instanceof ItemStack)){
            return false;
        }
        return recipe.getRecipeOutput().isItemEqual((ItemStack) obj);
    }

    @Override
    public boolean contains(String name) {
        I18n translator = I18n.getInstance();
        ArrayList<ItemStack> recipeItems = GuidebookPlusPlus.getRecipeItems(recipe);
        for (ItemStack recipeItem : recipeItems) {
            if(translator.translateKey(recipeItem.getItemName()+".name").toLowerCase().contains(name.toLowerCase())){
                return true;
            }
        }
        return translator.translateKey(recipe.getRecipeOutput().getItemName()+".name").toLowerCase().contains(name.toLowerCase());
    }

    @Override
    public boolean containsInput(String name) {
        I18n translator = I18n.getInstance();
        ArrayList<ItemStack> recipeItems = GuidebookPlusPlus.getRecipeItems(recipe);
        for (ItemStack recipeItem : recipeItems) {
            if(translator.translateKey(recipeItem.getItemName()+".name").toLowerCase().contains(name.toLowerCase())){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsOutput(String name) {
        I18n translator = I18n.getInstance();
        return translator.translateKey(recipe.getRecipeOutput().getItemName()+".name").toLowerCase().contains(name.toLowerCase());
    }
}
