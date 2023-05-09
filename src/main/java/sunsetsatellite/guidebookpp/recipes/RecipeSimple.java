package sunsetsatellite.guidebookpp.recipes;

import net.minecraft.src.IRecipe;
import net.minecraft.src.ItemStack;
import net.minecraft.src.StringTranslate;

public class RecipeSimple extends RecipeBase {
    public ItemStack input;
    public ItemStack output;

    public RecipeSimple(ItemStack input, ItemStack output){
        this.input = input;
        this.output = output;
    }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof RecipeSimple)){
            return false;
        }
        return ((RecipeSimple) obj).input.isItemEqual(input) && ((RecipeSimple) obj).output.isItemEqual(output);
    }

    @Override
    public boolean contains(Object obj) {
        if(!(obj instanceof ItemStack)){
            return false;
        }
        return input.isItemEqual((ItemStack) obj) || output.isItemEqual((ItemStack) obj);
    }

    @Override
    public boolean containsInput(Object obj) {
        if(!(obj instanceof ItemStack)){
            return false;
        }
        return input.isItemEqual((ItemStack) obj);
    }

    @Override
    public boolean containsOutput(Object obj) {
        if(!(obj instanceof ItemStack)){
            return false;
        }
        return output.isItemEqual((ItemStack) obj);
    }

    @Override
    public boolean contains(String name) {
        StringTranslate translator = StringTranslate.getInstance();
        return translator.translateKey(input.getItemName()+".name").toLowerCase().contains(name.toLowerCase()) || translator.translateKey(output.getItemName()+".name").toLowerCase().contains(name.toLowerCase());
    }

    @Override
    public boolean containsInput(String name) {
        StringTranslate translator = StringTranslate.getInstance();
        return translator.translateKey(input.getItemName()+".name").toLowerCase().contains(name.toLowerCase());
    }

    @Override
    public boolean containsOutput(String name) {
        StringTranslate translator = StringTranslate.getInstance();
        return translator.translateKey(output.getItemName()+".name").toLowerCase().contains(name.toLowerCase());
    }
}
