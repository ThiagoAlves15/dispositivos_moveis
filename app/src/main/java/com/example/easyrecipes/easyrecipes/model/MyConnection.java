package com.example.easyrecipes.easyrecipes.model;

import android.os.StrictMode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MyConnection {

    public List<RecipePresentation> getCategoryRecipes(Category category) {
        String url = "http://www.tudogostoso.com.br/categorias/" + category.getCategoryUrlString();
        List<RecipePresentation> rpList = new ArrayList<>();

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Document document = Jsoup.connect(url).userAgent("Mozilla").get();
            Element body = document.select("body").first();
            Elements moreHighlightsRPDivs = body.select("div.more-highlights").select("div.box.box-hover");
            List<Element> aTagsMoreHighlights = new ArrayList<>();
            for (Element e : moreHighlightsRPDivs) {
                aTagsMoreHighlights.add(e.select("a").first());
            }

            rpList = getRPFromATags(aTagsMoreHighlights);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return rpList;
    }

    public Map<String, List<RecipePresentation>> getHomeRP() {
        String url = "http://www.tudogostoso.com.br";
        Map<String, List<RecipePresentation>> rpMap = new HashMap<>();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            List<RecipePresentation> moreHighlightsRPList = new LinkedList<>();
            List<RecipePresentation> mostViewedRPList = new LinkedList<>();
            Document document = Jsoup.connect(url).userAgent("Mozilla").get();

            Element body = document.select("body").first();
            Elements divsBoxHoverMoreHighlights = body.select("div.more-highlights").select("div.main-content").select("div.left-content").select("div.box.box-hover");
            List<Element> aTagsMoreHighlights = new ArrayList<>();
            for (Element e: divsBoxHoverMoreHighlights) {
                aTagsMoreHighlights.add(e.select("a").first());
            }

            Elements divsBoxHoverMostViewed = body.select("div.most-viewed").select("div.main-content").select("div.left-content").select("div.box.box-hover");
            List<Element> aTagsMostViewed = new ArrayList<>();
            for (Element e : divsBoxHoverMostViewed) {
                aTagsMostViewed.add(e.select("a").first());
            }

            moreHighlightsRPList = getRPFromATags(aTagsMoreHighlights);
            mostViewedRPList = getRPFromATags(aTagsMostViewed);

            rpMap.put("moreHighlights", moreHighlightsRPList);
            rpMap.put("mostViewed", mostViewedRPList);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rpMap;
    }

    private List<RecipePresentation> getRPFromATags(List<Element> aTags) {
        List<RecipePresentation> rpList = new ArrayList<>();
        RecipePresentationBuilder rpBuilder = new RecipePresentationBuilder();
        for (Element e : aTags) {
            String title = e.select("h3").first().text();
            String time = e.select("span.time.recipe-info").first().text();
            String portion = e.select("span.portion.recipe-info").first().text();
            String url = e.attr("href");
            String absUrl = "http://www.tudogostoso.com.br" + url;
            String imgSrc = e.select("img.recipe-img").first().attr("src");
            rpBuilder.setTitle(title);
            rpBuilder.setTime(time);
            rpBuilder.setPortion(portion);
            rpBuilder.setUrl(absUrl);
            rpBuilder.setImgUrl(imgSrc);
            RecipePresentation rp = rpBuilder.build();
            rpList.add(rp);
        }
        return rpList;
    }

    private List<RecipePresentation> getRPFromElementsDivs(Elements recipesDivs) {
        List<RecipePresentation> rpList = new ArrayList<>();
        RecipePresentationBuilder rpBuilder = new RecipePresentationBuilder();
        for (Element e : recipesDivs) {
            rpBuilder.setTitle(e.select("h3").text());
            rpBuilder.setTime(e.select("span.time.recipe-info").text());
            rpBuilder.setPortion(e.select("span.portion.recipe-info").text());
            rpBuilder.setUrl(e.attr("abs:href"));
            rpBuilder.setImgUrl(e.select("img").attr("abs:href"));
            RecipePresentation recipePresentation = rpBuilder.build();
            rpList.add(recipePresentation);
        }
        return rpList;
    }

    public Recipe getRecipe(String recipeUrl) {
        Recipe recipe = null;
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Document document = Jsoup.connect(recipeUrl).get();
            Element body = document.select("body").first();
            Element title = body.select("div.recipe-title").select("h1").first();
            Element time = body.select("span.num.preptime").first();
            Element portion = body.select("data.p-yield.num.yield").first();
            Elements ingList = body.select("div.ingredients-box.box").select("ul").select("li");
            Elements stepList = body.select("div.directions-box.box").select("ol").select("li");
            Element imgUrl = body.select("div.clearfix.pics").select("li.picframe").select("img").first();

            RecipeBuilder recipeBuilder = new RecipeBuilder();
            recipeBuilder.setTitle(title.text());
            recipeBuilder.setTime(time.text());
            recipeBuilder.setPortion(portion.text());
            recipeBuilder.setImgUrl(imgUrl.attr("src"));

            for (Element e: ingList) {
                recipeBuilder.addIngredient(e.text());
            }
            for (Element e: stepList) {
                recipeBuilder.addInstructions(e.text());
            }

            recipe = recipeBuilder.build();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return recipe;
    }

    public SearchPageContext searchRecipe(String keywords) {
        String url = "http://www.tudogostoso.com.br/busca?q=" + keywords;
        SearchPageContext spc = new SearchPageContext();

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            Document document = Jsoup.connect(url).userAgent("Mozilla").get();

            Element body = document.select("body").first();
            Element searchFor = body.select("div.page-title").select("h1").first();
            Element foundedQtt = body.select("div.page-title").select("span.num").first();

            Elements elementsA = body.select("div.recipe-list").select("div.box.box-big").select("a");
            List<Element> aTags = new ArrayList<>(elementsA);

            Element spanNextDisabled = body.select("div.pagination").select("span.next.disabled").first();
            Element spanPrevDisabled = body.select("div.pagination").select("span.previous.disabled").first();

            spc.setFoundedRP(getRPFromATags(aTags));
            spc.setSearchFor(searchFor.text());
            spc.setFoundedQtt(Integer.parseInt(foundedQtt.text().split(" ")[0]));
            spc.setCurrentPage(1);
            if (spanNextDisabled == null) {
               spc.setHasNext(true);
            } else {
                spc.setHasNext(false);
            }
            if (spanPrevDisabled == null) {
                spc.setHasPrev(false);
            } else {
                spc.setHasPrev(true);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return spc;
    }

    public SearchPageContext searchRecipe(String keywords, int page) {
        String url = "www.tudogostoso.com.br/busca?page=" + page + "&q=" + keywords;
        SearchPageContext searchPageContext = new SearchPageContext();
        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Document document = Jsoup.connect(url).userAgent("Mozilla").get();
            Element body = document.select("body").first();
            Element searchFor = body.select("div.page-title").select("h1").first();
            Element foundedQtt = body.select("div.page-title").select("span.num").first();

            Elements elementsA = body.select("div.recipe-list").select("div.box.box-big").select("a");
            List<Element> aTags = new ArrayList<>(elementsA);

            searchPageContext.setFoundedRP(getRPFromATags(aTags));
            searchPageContext.setSearchFor(searchFor.text());
            searchPageContext.setFoundedQtt(Integer.parseInt(foundedQtt.text().split(" ")[0]));
            searchPageContext.setCurrentPage(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchPageContext;

    }


}
