package sunsetsatellite.guidebookpp;

import net.minecraft.core.block.Block;
import net.minecraft.core.lang.I18n;
import sunsetsatellite.guidebookpp.recipes.RecipeBase;

import java.util.ArrayList;
import java.util.Objects;

public class RecipeRegistry {
    private RecipeRegistry(){}

    public static ArrayList<RecipeGroup> groups = new ArrayList<>();

    public static ArrayList<RecipeGroup> getGroupsByModId(String modId){
        ArrayList<RecipeGroup> modGroups = new ArrayList<>();
        for (RecipeGroup group : groups) {
            if(group.modId.equalsIgnoreCase(modId)){
                modGroups.add(group);
            }
        }
        return modGroups;
    }

    public static ArrayList<RecipeGroup> getGroupsByMachine(Block machine){
        ArrayList<RecipeGroup> machineGroups = new ArrayList<>();
        for (RecipeGroup group : groups) {
            if(Objects.equals(group.machine, machine)){
                machineGroups.add(group);
            }
        }
        return machineGroups;
    }

    public static ArrayList<RecipeGroup> getGroupsByMachineName(String machineName){
        ArrayList<RecipeGroup> machineGroups = new ArrayList<>();
        for (RecipeGroup group : groups) {
            I18n translator = I18n.getInstance();
            if(translator.translateKey(group.machine.getKey()+".name").equalsIgnoreCase(machineName)){
                machineGroups.add(group);
            }
        }
        return machineGroups;
    }

    public static ArrayList<RecipeBase> getRecipes(){
        ArrayList<RecipeBase> recipes = new ArrayList<>();
        for (RecipeGroup group : groups) {
            recipes.addAll(group.recipes);
        }
        return recipes;
    }

    public static ArrayList<RecipeBase> findRecipesThatContain(Object obj){
        ArrayList<RecipeBase> recipes = new ArrayList<>();
        for (RecipeGroup group : groups) {
            for (RecipeBase recipe : group.recipes) {
                if(recipe.contains(obj)){
                    recipes.add(recipe);
                }
            }
        }
        return recipes;
    }

    public static ArrayList<RecipeBase> findRecipesByInput(Object obj){
        ArrayList<RecipeBase> recipes = new ArrayList<>();
        for (RecipeGroup group : groups) {
            for (RecipeBase recipe : group.recipes) {
                if(recipe.containsInput(obj)){
                    recipes.add(recipe);
                }
            }
        }
        return recipes;
    }

    public static ArrayList<RecipeBase> findRecipesByOutput(Object obj){
        ArrayList<RecipeBase> recipes = new ArrayList<>();
        for (RecipeGroup group : groups) {
            for (RecipeBase recipe : group.recipes) {
                if(recipe.containsOutput(obj)){
                    recipes.add(recipe);
                }
            }
        }
        return recipes;
    }

    public static ArrayList<RecipeBase> findRecipesThatContain(String name){
        ArrayList<RecipeBase> recipes = new ArrayList<>();
        for (RecipeGroup group : groups) {
            for (RecipeBase recipe : group.recipes) {
                if(recipe.contains(name)){
                    recipes.add(recipe);
                }
            }
        }
        return recipes;
    }

    public static ArrayList<RecipeBase> findRecipesByInput(String name){
        ArrayList<RecipeBase> recipes = new ArrayList<>();
        for (RecipeGroup group : groups) {
            for (RecipeBase recipe : group.recipes) {
                if(recipe.containsInput(name)){
                    recipes.add(recipe);
                }
            }
        }
        return recipes;
    }

    public static ArrayList<RecipeBase> findRecipesByOutput(String name){
        ArrayList<RecipeBase> recipes = new ArrayList<>();
        for (RecipeGroup group : groups) {
            for (RecipeBase recipe : group.recipes) {
                if(recipe.containsOutput(name)){
                    recipes.add(recipe);
                }
            }
        }
        return recipes;
    }

    public static int getGroupAmount(){
        return groups.size();
    }

    public static int getRecipeAmount(){
        int i = 0;
        for (RecipeGroup group : groups) {
            i += group.recipes.size();
        }
        return i;
    }
}
