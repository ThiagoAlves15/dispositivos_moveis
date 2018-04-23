package com.example.aluno.easyrecipe.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Recipe implements Parcelable {

    private String title;
    private String duration;
    private String yield;
    private ArrayList<String> ingredients;
    private ArrayList<String> instructions;

    public Recipe(String title, String duration, String yield, ArrayList<String> ingredients, ArrayList<String> instructions) {
        this.title = title;
        this.duration = duration;
        this.yield = yield;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public String getTitle() {
        return title;
    }

    public String getDuration() {
        return duration;
    }

    public String getYield() {
        return yield;
    }

    public ArrayList<String> getIngredients() {
        return (ArrayList<String>) Collections.unmodifiableList(ingredients);
    }

    public ArrayList<String> getInstructions() {
        return (ArrayList<String>) Collections.unmodifiableList(instructions);
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        duration = in.readString();
        yield = in.readString();
        if (in.readByte() == 0x01) {
            ingredients = new ArrayList<String>();
            in.readList(ingredients, String.class.getClassLoader());
        } else {
            ingredients = null;
        }
        if (in.readByte() == 0x01) {
            instructions = new ArrayList<String>();
            in.readList(instructions, String.class.getClassLoader());
        } else {
            instructions = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(duration);
        dest.writeString(yield);
        if (ingredients == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(ingredients);
        }
        if (instructions == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(instructions);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    public String getText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(title).append("\n");
        stringBuilder.append(duration).append("\n");
        stringBuilder.append(yield).append("\n");
        for (String s: ingredients) {
            stringBuilder.append(s).append("\n");
        }
        for (String s: instructions) {
            stringBuilder.append(s).append("\n");
        }
        return stringBuilder.toString();
    }

}
