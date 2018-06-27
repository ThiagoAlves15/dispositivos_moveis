package com.example.easyrecipes.easyrecipes;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyrecipes.easyrecipes.model.DownloadImageTask;
import com.example.easyrecipes.easyrecipes.model.MyConnection;
import com.example.easyrecipes.easyrecipes.model.Recipe;

import java.util.List;

public class RecipeActivity extends AppCompatActivity {

    private Recipe recipe;
    private ConstraintLayout contentRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Bundle bundle = getIntent().getExtras();
        String recipeUrl = getIntent().getStringExtra("recipeUrl");

        contentRecipe = (ConstraintLayout) findViewById(R.id.content_recipe);



        loadRecipePage(recipeUrl);



    }

    private void loadRecipePage(String recipeUrl) {

        MyConnection myConnection = new MyConnection();
        recipe = myConnection.getRecipe(recipeUrl);
        // get and set recipe setTitle
        TextView txtTitle = (TextView) findViewById(R.id.txt_recipe_title);
        txtTitle.setText(recipe.getTitle());
        // get and set image view
        ImageView img = (ImageView) findViewById(R.id.img_recipe_img);
        String imgUrl = recipe.getImgUrl();
        int width = Integer.parseInt(imgUrl.split("&")[1].split("=")[1]);
        int height = Integer.parseInt(imgUrl.split("&")[2].split("=")[1]);
        new DownloadImageTask(img).execute(imgUrl);
        // new and set text views time2 portion2
        TextView txtTime2 = new TextView(getBaseContext());
        TextView txtPortion2 = new TextView(getBaseContext());
        txtTime2.setText(recipe.getTime());
        txtPortion2.setText(recipe.getPortion());
        // new and set text views ingredients2 instructions2
        TextView txtIngredients2 = new TextView(getBaseContext());
        TextView txtInstructions2 = new TextView(getBaseContext());
        txtIngredients2.setSingleLine(false);
        txtInstructions2.setSingleLine(false);
        List<String> ingredients = recipe.getIngredients();
        List<String> instructions = recipe.getInstructions();
        for (int i = 0; i < ingredients.size(); i++) {
            String originalTxt = txtIngredients2.getText().toString();
            String newIngredient = ingredients.get(i);
            String finalTxt = originalTxt + "- " + newIngredient;
            if (i != ingredients.size()-1) {
                finalTxt += "\n";
            }
            txtIngredients2.setText(finalTxt);
        }
        for (int i = 0; i < instructions.size(); i++) {
            String index = i+1 + " ";
            String originalTxt = txtInstructions2.getText().toString();
            String newInstruction = instructions.get(i);
            String finalTxt = originalTxt + index + newInstruction;
            if (i != instructions.size()-1) {
                finalTxt += "\n";
            }
            txtInstructions2.setText(finalTxt);
        }
        // set id
        txtTime2.setId(View.generateViewId());
        txtPortion2.setId(View.generateViewId());
        txtIngredients2.setId(View.generateViewId());
        txtInstructions2.setId(View.generateViewId());

        contentRecipe.addView(txtTime2);
        contentRecipe.addView(txtPortion2);
        contentRecipe.addView(txtIngredients2);
        contentRecipe.addView(txtInstructions2);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(contentRecipe);

        constraintSet.connect(txtTime2.getId(), ConstraintSet.TOP, R.id.txt_recipe_time, ConstraintSet.TOP, 0);
        constraintSet.connect(txtTime2.getId(), ConstraintSet.LEFT, R.id.txt_recipe_time, ConstraintSet.RIGHT, 8);
        constraintSet.connect(txtPortion2.getId(), ConstraintSet.TOP, R.id.txt_recipe_portion, ConstraintSet.TOP, 0);
        constraintSet.connect(txtPortion2.getId(), ConstraintSet.LEFT, R.id.txt_recipe_portion, ConstraintSet.RIGHT, 8);
        constraintSet.connect(txtIngredients2.getId(), ConstraintSet.TOP, R.id.txt_recipe_ingredients, ConstraintSet.BOTTOM, 8);
        constraintSet.connect(txtIngredients2.getId(), ConstraintSet.LEFT, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.LEFT, 16);
        constraintSet.connect(R.id.txt_recipe_instructions, ConstraintSet.TOP, txtIngredients2.getId(), ConstraintSet.BOTTOM, 8);
        constraintSet.connect(txtInstructions2.getId(), ConstraintSet.TOP, R.id.txt_recipe_instructions, ConstraintSet.BOTTOM, 8);
        constraintSet.connect(txtInstructions2.getId(), ConstraintSet.LEFT, ConstraintLayout.LayoutParams.PARENT_ID, ConstraintSet.LEFT, 16);

        constraintSet.applyTo(contentRecipe);

    }

}
