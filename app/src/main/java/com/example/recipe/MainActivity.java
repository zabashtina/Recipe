package com.example.recipe;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.snackbar.Snackbar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {
    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipes = new ArrayList<>();
    private RecipeApi recipeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeAdapter = new RecipeAdapter(recipes, this);
        recyclerView.setAdapter(recipeAdapter);

        // Инициализация Retrofit API
        recipeApi = RetrofitClient.getRetrofitInstance().create(RecipeApi.class);

        // Загрузка данных
        loadRecipes();

        // Обновление данных каждую минуту
        new Handler().postDelayed(this::loadRecipes, 60000);
    }

    private void loadRecipes() {
        recipeApi.getRecipes().enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Recipes", "Загружено рецептов: " + response.body().getRecipes().size());
                    recipes.clear();
                    recipes.addAll(response.body().getRecipes());
                    recipeAdapter.notifyDataSetChanged();
                } else {
                    Log.e("ResponseError", "Код ответа: " + response.code() + ", сообщение: " + response.message());
                    showError("Ошибка загрузки данных");
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.e("NetworkError", "Ошибка сети: " + t.getMessage(), t);
                showError("Ошибка сети");
            }
        });
    }

    private void showError(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("recipe", recipe);
        startActivity(intent);
    }
}