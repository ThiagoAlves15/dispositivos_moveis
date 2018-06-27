package com.example.easyrecipes.easyrecipes.model;

import java.util.Collections;
import java.util.List;

public class Recipe {

    private String title;
    private String time;
    private String portion;
    private List<String> ingredients;
    private List<String> instructions;
    private String imgUrl;

    public Recipe(String title, String time, String portion, List<String> ingredients, List<String> instructions, String imgUrl) {
        this.title = title;
        this.time = time;
        this.portion = portion;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getTime() {
        return time;
    }

    public String getPortion() {
        return portion;
    }

    public List<String> getIngredients() {
        return Collections.unmodifiableList(ingredients);
    }

    public List<String> getInstructions() {
        return Collections.unmodifiableList(instructions);
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(title).append("\n");
        stringBuilder.append(time).append("\n");
        stringBuilder.append(portion).append("\n");
        for (String s: ingredients) {
            stringBuilder.append(s).append("\n");
        }
        for (String s: instructions) {
            stringBuilder.append(s).append("\n");
        }
        stringBuilder.append("imgUrl ").append(imgUrl);
        return stringBuilder.toString();
    }

}
