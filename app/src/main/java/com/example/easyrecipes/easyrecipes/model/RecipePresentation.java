package com.example.easyrecipes.easyrecipes.model;

public class RecipePresentation {

    private String title;
    private String time;
    private String portion;
    private String url;
    private String imgUrl;

    public RecipePresentation(String title, String time, String portion, String url, String imgUrl) {
        this.title = title;
        this.time = time;
        this.portion = portion;
        this.url = url;
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

    public String getUrl() {
        return url;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    @Override
    public String toString() {
        String rtrStr =
                "Recipe Presentation\n" +
                        "Title: " + title + "\n" +
                        "Time: " + time + "\n" +
                        "Portion: " + portion + "\n" +
                        "Url: " + url + "\n" +
                        "ImgUrl: " + imgUrl;


        return rtrStr;
    }

}
