package com.example.aluno.easyrecipe.Model;

import android.os.StrictMode;
import android.view.Menu;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyConnection {

    public Recipe getRecipe(String url) {
        Recipe recipe = null;
        System.out.println("trying to get the recipe");
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Document document = Jsoup.connect(url).get();
            Element title = document.select("div.recipe-title").first().select("h1").first();
            Element time = document.select("time").first();
            Element yield = document.select("data.p-yield.num.yield").first();
            Elements ingList = document.select("span.p-ingredient");
            Elements stepList = document.select("div.instructions").select("span");

            RecipeBuilder recipeBuilder = new RecipeBuilder();
            recipeBuilder.title(title.text());
            recipeBuilder.duration(time.text());
            recipeBuilder.yield(yield.text());

            for (Element e: ingList) {
                recipeBuilder.ingredient(e.text());
            }
            for (Element e: stepList) {
                recipeBuilder.instruction(e.text());
            }

            recipe = recipeBuilder.build();


        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return recipe;
    }

}
