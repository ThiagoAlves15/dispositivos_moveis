package com.example.easyrecipes.easyrecipes;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easyrecipes.easyrecipes.model.Category;
import com.example.easyrecipes.easyrecipes.model.DownloadImageTask;
import com.example.easyrecipes.easyrecipes.model.MyConnection;
import com.example.easyrecipes.easyrecipes.model.RecipePresentation;

import java.util.List;
import java.util.Map;

public class CategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ConstraintLayout contentCategory;
    private int lastId;
    private Category actualCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSearch();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_category);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_category);
        navigationView.setNavigationItemSelectedListener(this);

        loadCategoryPage();



    }

    private void startSearch() {
        Intent startSearch = new Intent(CategoryActivity.this, SearchActivity.class);
        startActivity(startSearch);
    }

    private void loadCategoryPage() {
        Category category = (Category) getIntent().getSerializableExtra("category");
        actualCategory = category;
        MyConnection connection = new MyConnection();
        List<RecipePresentation> moreHighlightsRPList = connection.getCategoryRecipes(category);

        contentCategory = (ConstraintLayout) findViewById(R.id.content_category);
        lastId = R.id.txt_cat_more_highlights;

        TextView txtCategoryName = (TextView) findViewById(R.id.txt_category_name);
        txtCategoryName.setText(category.getCategoryName());

        for (RecipePresentation rp : moreHighlightsRPList) {
            insertRPToContentView(rp);
        }

    }

    private void insertRPToContentView(RecipePresentation rp) {
        TextView title = new TextView(getBaseContext());
        TextView time = new TextView(getBaseContext());
        TextView portion = new TextView(getBaseContext());
        ImageView img = new ImageView(getBaseContext());
        Button button = new Button(getBaseContext());
        title.setSingleLine(false);
        // set text
        title.setText(rp.getTitle());
        time.setText(rp.getTitle());
        portion.setText(rp.getPortion());
        button.setText(R.string.btn_see_recipe_text);
        // set text size
        title.setTextSize(18);
        time.setTextSize(14);
        portion.setTextSize(14);
        button.setTextSize(14);
        // set img
        new DownloadImageTask(img).execute(rp.getImgUrl());
        // set id
        title.setId(View.generateViewId());
        time.setId(View.generateViewId());
        portion.setId(View.generateViewId());
        img.setId(View.generateViewId());
        button.setId(View.generateViewId());
        // set button onclick
        final String url = rp.getUrl();
        button.setOnClickListener(v -> {
            Intent seeRecipe = new Intent(CategoryActivity.this, RecipeActivity.class);
            seeRecipe.putExtra("recipeUrl", url);
            startActivity(seeRecipe);
        });

        contentCategory.addView(title);
        contentCategory.addView(time);
        contentCategory.addView(portion);
        contentCategory.addView(img);
        contentCategory.addView(button);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(contentCategory);

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

        constraintSet.applyTo(contentCategory);

        lastId = img.getId();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Category selectedCategory = null;

        if (id == R.id.nav_bolo_tortas) {
            selectedCategory = Category.BOLOS_E_TORTAS;
        } else if (id == R.id.nav_carnes) {
            selectedCategory = Category.CARNES;
        } else if (id == R.id.nav_aves) {
            selectedCategory = Category.AVES;
        } else if (id == R.id.nav_peixes_frutos_mar) {
            selectedCategory = Category.PEIXES_E_FRUTOS_DO_MAR;
        } else if (id == R.id.nav_saladas_molhos) {
            selectedCategory = Category.SALADAS_E_MOLHOS;
        } else if (id == R.id.nav_sopas) {
            selectedCategory = Category.SOPAS;
        } else if (id == R.id.nav_massas) {
            selectedCategory = Category.MASSAS;
        } else if (id == R.id.nav_bebidas) {
            selectedCategory = Category.BEBIDAS;
        } else if (id == R.id.nav_doces_sobremesas) {
            selectedCategory = Category.DOCES_E_SOBREMESAS;
        } else if (id == R.id.nav_lanches) {
            selectedCategory = Category.LANCHES;
        } else if (id == R.id.nav_alimentacao_saudavel) {
            selectedCategory = Category.ALIMENTACAO_SAUDAVEL;
        } else if (id == R.id.nav_localizacao) {
            Intent startMaps = new Intent(CategoryActivity.this, MapsActivity.class);
            startActivity(startMaps);
        }


        if (selectedCategory != actualCategory) {
            Intent startCategoryActivity = new Intent(CategoryActivity.this, CategoryActivity.class);
            startCategoryActivity.putExtra("category", selectedCategory);
            startActivity(startCategoryActivity);
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_home);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
