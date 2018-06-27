package com.example.easyrecipes.easyrecipes.model;

import java.util.ArrayList;
import java.util.List;

public class RecipeBuilder {

    private String title;
    private String time;
    private String portion;
    private List<String> ingredients;
    private List<String> instructions;
    private String imgUrl;

    public RecipeBuilder() {
        ingredients = new ArrayList<>();
        instructions = new ArrayList<>();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public void addIngredient(String ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addInstructions(String instruction) {
        this.instructions.add(instruction);
    }

    public Recipe build() {
        return new Recipe(title, time, portion, ingredients, instructions, imgUrl);
    }


}
