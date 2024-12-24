package com.example.recipe;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class RecipeDetailActivity extends AppCompatActivity {
    private TextView title, description;
    private RecyclerView stepsRecyclerView;
    private StepsAdapter stepsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Включить Action Bar со стрелкой "Назад"
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Детали рецепта");
        }

        title = findViewById(R.id.recipe_title);
        description = findViewById(R.id.recipe_description);
        stepsRecyclerView = findViewById(R.id.steps_recycler_view);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Recipe recipe = (Recipe) getIntent().getSerializableExtra("recipe");

        title.setText(recipe.getTitle());
        description.setText(recipe.getDescription());

        stepsAdapter = new StepsAdapter(recipe.getSteps());
        stepsRecyclerView.setAdapter(stepsAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Возвращаемся назад при нажатии на стрелку
        onBackPressed();
        return true;
    }
}
