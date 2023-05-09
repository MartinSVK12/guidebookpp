package sunsetsatellite.guidebookpp.recipes;

import net.minecraft.src.ItemStack;

import java.util.ArrayList;

public class RecipeItem extends RecipeBase {

    public ArrayList<ItemStack> inputs;
    public ArrayList<ItemStack> outputs;

    public RecipeItem(ArrayList<ItemStack> inputs, ArrayList<ItemStack> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public boolean contains(Object obj) {
        return false;
    }

    @Override
    public boolean containsInput(Object obj) {
        return false;
    }

    @Override
    public boolean containsOutput(Object obj) {
        return false;
    }

    @Override
    public boolean contains(String name) {
        return false;
    }

    @Override
    public boolean containsInput(String name) {
        return false;
    }

    @Override
    public boolean containsOutput(String name) {
        return false;
    }
}
