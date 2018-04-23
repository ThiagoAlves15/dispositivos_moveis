package com.example.aluno.easyrecipe;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.aluno.easyrecipe.Model.MyConnection;
import com.example.aluno.easyrecipe.Model.Recipe;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

        Recipe recipe = null;
        MyConnection myConnection = new MyConnection();

        TextView textView = findViewById(R.id.recipe_content);


        if (id == R.id.cat_bolo_tortas) {
            recipe = myConnection.getRecipe("http://www.tudogostoso.com.br/receita/54118-bolo-de-aipim-facil.html");
        } else if (id == R.id.cat_carnes) {
            recipe = myConnection.getRecipe("http://www.tudogostoso.com.br/receita/174375-escondidinho-de-carne-seca.html");
        } else if (id == R.id.cat_aves) {
            recipe = myConnection.getRecipe("http://www.tudogostoso.com.br/receita/191499-coxas-de-asas-com-batatas-e-cenouras.html");
        } else if (id == R.id.cat_peixes_frutos_mar) {
            recipe = myConnection.getRecipe("http://www.tudogostoso.com.br/receita/185656-moqueca-de-corvina-muito-saborosa.html");
        } else if (id == R.id.cat_saladas_molhos) {
            recipe = myConnection.getRecipe("http://www.tudogostoso.com.br/receita/188444-couve-flor-gratinada.html");
        } else if (id == R.id.cat_sopas) {
            recipe = myConnection.getRecipe("http://www.tudogostoso.com.br/receita/143121-sopa-de-batata-com-bacon.html");
        } else if (id == R.id.cat_massas) {
            recipe = myConnection.getRecipe("http://www.tudogostoso.com.br/receita/182315-macarrao-integral-com-peito-de-peru.html");
        } else if (id == R.id.cat_bebidas) {
            recipe = myConnection.getRecipe("http://www.tudogostoso.com.br/receita/70654-milk-shake-do-bem.html");
        } else if (id == R.id.cat_doces_sobremesas) {
            recipe = myConnection.getRecipe("http://www.tudogostoso.com.br/receita/59348-sobremesa-tipo-chandelle-deliciosa.html");
        } else if (id == R.id.cat_lanches) {
            recipe = myConnection.getRecipe("http://www.tudogostoso.com.br/receita/119814-waffle-de-chocolate.html");
        } else if (id == R.id.cat_alimentacao_saudavel) {
            recipe = myConnection.getRecipe("http://www.tudogostoso.com.br/receita/147724-torta-integral-de-atum.html");
        }

        textView.setText(recipe.getText());


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
