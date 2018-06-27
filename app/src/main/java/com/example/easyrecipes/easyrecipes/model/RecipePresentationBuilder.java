package com.example.easyrecipes.easyrecipes.model;

public class RecipePresentationBuilder {

    private String title;
    private String time;
    private String portion;
    private String url;
    private String imgUrl;

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTime(String duration) {
        this.time = duration;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public RecipePresentation build() {
        return new RecipePresentation(title, time, portion, url, imgUrl);
    }

}
