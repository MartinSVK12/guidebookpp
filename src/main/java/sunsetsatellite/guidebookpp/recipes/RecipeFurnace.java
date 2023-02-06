package sunsetsatellite.guidebookpp.recipes;

import net.minecraft.src.ItemStack;
import net.minecraft.src.RecipesFurnace;

public class RecipeFurnace {
    public ItemStack input;
    public ItemStack output;

    public RecipeFurnace(ItemStack input, ItemStack output){
        this.input = input;
        this.output = output;
    }
}
