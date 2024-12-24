package com.example.recipe;

import retrofit2.Call;
import retrofit2.http.GET;
import java.util.List;

public interface RecipeApi {
    @GET("recipes")
    Call<RecipeResponse> getRecipes();
}