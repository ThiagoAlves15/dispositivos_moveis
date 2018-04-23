package com.example.aluno.easyrecipe.Model;


import java.util.ArrayList;
import java.util.List;

public class RecipeBuilder {

    private String title;
    private String duration;
    private String yield;
    private ArrayList<String> ingredients;
    private ArrayList<String> instructions;

    public RecipeBuilder() {
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();
    }

    public void title(String title) {
        this.title = title;
    }

    public void duration(String duration) {
        this.duration = duration;
    }

    public void yield(String yield) {
        this.yield = yield;
    }

    public void ingredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public void instruction(String instruction) {
        this.instructions.add(instruction);
    }

    public Recipe build() {
        return new Recipe(title, duration, yield, ingredients, instructions);
    }

}

