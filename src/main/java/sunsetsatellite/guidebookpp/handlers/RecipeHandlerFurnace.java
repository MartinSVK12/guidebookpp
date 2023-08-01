package sunsetsatellite.guidebookpp.handlers;

import net.minecraft.core.block.Block;
import net.minecraft.core.crafting.recipe.RecipesFurnace;
import net.minecraft.core.item.ItemStack;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeBase;
import net.minecraft.core.player.inventory.ContainerGuidebookRecipeFurnace;
import sunsetsatellite.guidebookpp.GuidebookPlusPlus;
import sunsetsatellite.guidebookpp.IRecipeHandlerBase;
import sunsetsatellite.guidebookpp.RecipeGroup;
import sunsetsatellite.guidebookpp.RecipeRegistry;
import sunsetsatellite.guidebookpp.recipes.RecipeSimple;

import java.util.ArrayList;
import java.util.HashMap;

public class RecipeHandlerFurnace
        implements IRecipeHandlerBase
{

    public ContainerGuidebookRecipeBase getContainer(Object o) {
        RecipeSimple recipe = (RecipeSimple) o;
        return new ContainerGuidebookRecipeFurnace(new ItemStack(Block.furnaceStoneActive),recipe.input,recipe.output);
    }

    public void addRecipes() {
        Block block = Block.furnaceStoneIdle;
        GuidebookPlusPlus.LOGGER.info("Adding recipes for: " + this.getClass().getSimpleName());
        ArrayList<RecipeSimple> recipes = new ArrayList<>();
        HashMap<Integer, ItemStack> rawRecipes = new HashMap<>(RecipesFurnace.smelting().getSmeltingList());
        rawRecipes.forEach((I,O)->{
            recipes.add(new RecipeSimple(new ItemStack(I,1,0),O));
        });
        RecipeGroup group = new RecipeGroup("minecraft",block,this,recipes);
        RecipeRegistry.groups.add(group);
    }

}
