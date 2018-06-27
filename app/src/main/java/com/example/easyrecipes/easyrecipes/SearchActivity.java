package com.example.easyrecipes.easyrecipes;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.easyrecipes.easyrecipes.model.MyConnection;
import com.example.easyrecipes.easyrecipes.model.RecipePresentation;
import com.example.easyrecipes.easyrecipes.model.RecipePresentationInserter;
import com.example.easyrecipes.easyrecipes.model.SearchPageContext;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private ConstraintLayout contentSearch;
    EditText editTextSearch;
    private TextView txtSearchFor;
    private TextView txtFoundedQtt;
    private Button btnNext;
    private Button btnPrev;
    private TextView txtNotFound;
    private int currentPage;
    private List<Integer> newIds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadSearchPage();
    }

    private void loadSearchPage() {
        this.clearView();
        newIds = new ArrayList<>();
        contentSearch = (ConstraintLayout) findViewById(R.id.content_search);
        ImageButton btnSearch = (ImageButton) findViewById(R.id.btn_search);
        txtSearchFor = (TextView) findViewById(R.id.txt_search_for);
        txtFoundedQtt = (TextView) findViewById(R.id.txt_founded_qtt);
        txtNotFound = (TextView) findViewById(R.id.txt_not_found);
        txtSearchFor.setVisibility(View.GONE);
        txtFoundedQtt.setVisibility(View.GONE); // GONE = invisible and don't ocupe space
        txtNotFound.setVisibility(View.GONE);
        btnNext = (Button) findViewById(R.id.btn_next_page);
        btnPrev = (Button) findViewById(R.id.btn_prev_page);
        btnNext.setVisibility(View.GONE);
        btnPrev.setVisibility(View.GONE);
        editTextSearch = (EditText) findViewById(R.id.edit_txt_search);

        btnSearch.setOnClickListener(v -> {
            txtSearchFor.setVisibility(View.VISIBLE);
            txtFoundedQtt.setVisibility(View.VISIBLE);
            search(editTextSearch.getText().toString(), 1);
        });

        btnNext.setOnClickListener(v -> {
            search(editTextSearch.getText().toString(), currentPage+1);
        });

        btnPrev.setOnClickListener(v -> {
            search(editTextSearch.getText().toString(), currentPage-1);
        });


    }

    public void search(String keywords, int page) {
        MyConnection connection = new MyConnection();
        SearchPageContext spc;
        if (page == 1) { spc = connection.searchRecipe(keywords); }
        else { spc = connection.searchRecipe(keywords, page); }
        txtSearchFor.setVisibility(View.VISIBLE);
        txtFoundedQtt.setVisibility(View.VISIBLE);
        String searchForKeywords = "Busca por: " + keywords;
        String foundedQttString = "Encontrou " + spc.getFoundedQtt() + " receitas";
        txtSearchFor.setText(searchForKeywords);
        txtFoundedQtt.setText(foundedQttString);
        if (spc.getFoundedQtt() == 0) {
            txtNotFound.setVisibility(View.VISIBLE);
        } else {
            txtNotFound.setVisibility(View.GONE);
            List<RecipePresentation> rpList = spc.getFoundedRecipes();
            RecipePresentationInserter rpInserter =
                    new RecipePresentationInserter(SearchActivity.this, contentSearch, R.id.txt_founded_qtt);
            for (RecipePresentation rp : rpList) {
                newIds.addAll(rpInserter.insertRPToContentView(rp));
            }
            currentPage = spc.getCurrentPage();
            if (spc.hasPrev()) { btnPrev.setVisibility(View.VISIBLE); }
                else { btnPrev.setVisibility(View.GONE); }
            if (spc.hasNext()) { btnNext.setVisibility(View.VISIBLE); }
                else { btnNext.setVisibility(View.GONE); }
        }
    }



    private void clearView() {
        // remove not found, and previous RP
        // set invisible: btnNext, prev, txtFounded, txtSearchFor
        if (newIds != null && !newIds.isEmpty()) {
            txtFoundedQtt.setVisibility(View.GONE);
            txtSearchFor.setVisibility(View.GONE);
            btnNext.setVisibility(View.GONE);
            btnPrev.setVisibility(View.GONE);
            txtNotFound.setVisibility(View.GONE);
            for (int i : newIds) {
                contentSearch.removeView(findViewById(i));
            }
            newIds = new ArrayList<>();
        }
    }

}
