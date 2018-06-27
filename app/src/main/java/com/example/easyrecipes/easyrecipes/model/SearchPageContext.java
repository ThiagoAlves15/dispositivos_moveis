package com.example.easyrecipes.easyrecipes.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class SearchPageContext {

    private String searchFor;
    private int foundedQtt;
    private List<RecipePresentation> foundedRecipes;
    private int currentPage;
    private boolean hasPrev;
    private boolean hasNext;

    public SearchPageContext() {
        this.foundedRecipes = new LinkedList<>();
    }

    public void setSearchFor(String searchFor) {
        this.searchFor = searchFor;
    }

    public void setFoundedQtt(int foundedQtt) {
        this.foundedQtt = foundedQtt;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setFoundedRP(List<RecipePresentation> foundedRecipes) {
        this.foundedRecipes = foundedRecipes;
    }

    public void setHasPrev(boolean hasPrev) {
        this.hasPrev = hasPrev;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getFoundedQtt() {
        return foundedQtt;
    }

    public List<RecipePresentation> getFoundedRecipes() {
        return Collections.unmodifiableList(foundedRecipes);
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public String getSearchFor() {
        return searchFor;
    }

    public boolean hasPrev() {
        return hasPrev;
    }

    public boolean hasNext() {
        return hasNext;
    }
}
