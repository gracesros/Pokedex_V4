package org.grace.pokedex;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.grace.pokedex.adapters.PokemonAdapter3;
import org.grace.pokedex.data.AppDatabase;
import org.grace.pokedex.data.Pokemon;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class FavoritesActivity extends AppCompatActivity implements PokemonAdapter3.ItemClickListener {

    PokemonAdapter3 adapter;
    RecyclerView recyclerView;
    AppDatabase database;
    ImageView favorite;
    RecyclerView rvDetailsTypes;

    List<Pokemon> favoritePokemons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_pokemon);
        database = AppDatabase.getDatabase(this);
        favoritePokemons = database.pokemonDao().getAll();
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        adapter = new PokemonAdapter3(this, favoritePokemons);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
        favorite = findViewById(R.id.details_favorite);
        rvDetailsTypes = findViewById(R.id.rv_details_types);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_top, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_favorites:
                Intent intent = new Intent(this, FavoritesActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                // app icon in action bar clicked; go home
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        favoritePokemons.clear();
        favoritePokemons.addAll(database.pokemonDao().getAll());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        Pokemon selectedPokemon = adapter.getPokemon(position);
        Intent intent = new Intent(this, PokemonDetailsActivity.class);
        intent.putExtra("URL", selectedPokemon.getUrl());
        startActivity(intent);
    }
}
