package com.example.easyrecipes.easyrecipes.model;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyrecipes.easyrecipes.R;
import com.example.easyrecipes.easyrecipes.RecipeActivity;

import java.util.LinkedList;
import java.util.List;

public class RecipePresentationInserter {

    private Context packageContext;
    private ConstraintLayout contentView;
    private int lastId;

    public RecipePresentationInserter(Context packageContext, ConstraintLayout contentView, int lastId) {
        this.packageContext = packageContext;
        this.contentView = contentView;
        this.lastId = lastId;
    }

    public List<Integer> insertRPToContentView(RecipePresentation rp) {
        TextView title = new TextView(packageContext);
        TextView time = new TextView(packageContext);
        TextView portion = new TextView(packageContext);
        ImageView img = new ImageView(packageContext);
        Button button = new Button(packageContext);
        title.setSingleLine(false);
        // set font size
        title.setTextSize(18);
        time.setTextSize(14);
        portion.setTextSize(14);
        button.setTextSize(14);
        // set text
        title.setText(rp.getTitle());
        time.setText(rp.getTime());
        portion.setText(rp.getPortion());
        button.setText(R.string.btn_see_recipe_text);
        // set image
        new DownloadImageTask(img).execute(rp.getImgUrl());
        //set id
        title.setId(View.generateViewId());
        time.setId(View.generateViewId());
        portion.setId(View.generateViewId());
        img.setId(View.generateViewId());
        button.setId(View.generateViewId());
        // set button onclick
        final String url = rp.getUrl();
        button.setOnClickListener(v -> {
            Intent seeRecipe = new Intent(packageContext, RecipeActivity.class);
            seeRecipe.putExtra("recipeUrl", url);
            packageContext.startActivity(seeRecipe);
        });


        contentView.addView(title);
        contentView.addView(time);
        contentView.addView(portion);
        contentView.addView(img);
        contentView.addView(button);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(contentView);

        constraintSet.connect(title.getId(), ConstraintSet.TOP, lastId, ConstraintSet.BOTTOM, 10);
        constraintSet.connect(img.getId(), ConstraintSet.TOP, title.getId(), ConstraintSet.BOTTOM, 8);
        constraintSet.connect(time.getId(), ConstraintSet.TOP, title.getId(), ConstraintSet.BOTTOM, 10);
        constraintSet.connect(time.getId(), ConstraintSet.LEFT, img.getId(), ConstraintSet.RIGHT, 24);
        constraintSet.connect(portion.getId(), ConstraintSet.TOP, time.getId(), ConstraintSet.BOTTOM, 10);
        constraintSet.connect(portion.getId(), ConstraintSet.LEFT, img.getId(), ConstraintSet.RIGHT, 24);
        constraintSet.connect(button.getId(), ConstraintSet.TOP, portion.getId(), ConstraintSet.BOTTOM, 10);
        constraintSet.connect(button.getId(), ConstraintSet.LEFT, img.getId(), ConstraintSet.RIGHT, 24);

        constraintSet.connect(title.getId(), ConstraintSet.LEFT, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.LEFT, 24);
        constraintSet.connect(img.getId(), ConstraintSet.LEFT, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.LEFT, 32);

        constraintSet.applyTo(contentView);

        lastId = img.getId();

        List<Integer> newIds = new LinkedList<>();
        newIds.add(title.getId());
        newIds.add(time.getId());
        newIds.add(portion.getId());
        newIds.add(img.getId());
        newIds.add(button.getId());

        return newIds;
    }

}
