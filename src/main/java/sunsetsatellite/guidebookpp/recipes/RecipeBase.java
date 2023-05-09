package sunsetsatellite.guidebookpp.recipes;

import sunsetsatellite.guidebookpp.RecipeGroup;

public abstract class RecipeBase {

    public RecipeGroup group;

    @Override
    public abstract boolean equals(Object obj);

    public abstract boolean contains(Object obj);

    public abstract boolean containsInput(Object obj);

    public abstract boolean containsOutput(Object obj);

    public abstract boolean contains(String name);

    public abstract boolean containsInput(String name);

    public abstract boolean containsOutput(String name);
}
