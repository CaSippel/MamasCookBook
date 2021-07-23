package com.example.mamascookbook;

public class RecipeInfo {
    int id;
    String ingredient; // todo: make plural
    String instructions;
    String name;

    RecipeInfo() {
        this.id = -1;
    }
    RecipeInfo(int id, String name, String ingredient, String instructions) {
        this.id = id;
        this.ingredient = ingredient;
        this.instructions = instructions;
        this.name = name;
    }

    public String toLongString() {
        return "(" + this.id + ") " + this.name + ": " + this.ingredient +
                "\n" + this.instructions;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }
    public String getIngredient() {
        return this.ingredient;
    }
    public String getInstructions() {
        return this.instructions;
    }
    public String getName() {
        return this.name;
    }

    public boolean isValidRecipe() {
        return this.id != -1;
    }
    public static boolean isValidRecipeId(long id) { return id != -1; }
}