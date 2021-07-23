package com.example.mamascookbook.ui;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mamascookbook.AppDatabase;
import com.example.mamascookbook.RecipeInfo;

import java.util.List;

import static android.util.Log.d;

public class RecipeListViewModel extends ViewModel {
    private static final String TAG = "Tag-RecipeListViewModel";

    // common
    private AppDatabase mDatabase;
    // fragment data
    private MutableLiveData<String> mHomeText;

    private void initHomeFragment() {
        mHomeText = new MutableLiveData<>();
        mHomeText.setValue("Your all inclusive guide to delicious home made meals!");
    }

    private void VerifyDatabase(Context context) {
        if (mDatabase == null) {
            mDatabase = new AppDatabase(context);
        }
    }

// public
    public RecipeListViewModel(Context context) {
        d(TAG, "RecipeListViewModel()");
        mDatabase = new AppDatabase(context);
        initHomeFragment();
    }

    public List<RecipeInfo> getRecipeList(Context context) {
        VerifyDatabase(context);
        return mDatabase.allRecipes();
    }

    public String getRecipeStringList(Context context) {
        VerifyDatabase(context);
        List<RecipeInfo> recipes = mDatabase.allRecipes();
        StringBuilder builder = new StringBuilder();
        for (RecipeInfo recipe: recipes) {
            // build map indexed by names (what about id's)
            builder.append(recipe.toLongString()).append("\n");
        }
        return builder.toString();
    }

    public LiveData<String> getHomeText() {
        return mHomeText;
    }

    public String onAddRecipe(Context context, String name, String ingredient, String instructions) {
        VerifyDatabase(context);
        long err = mDatabase.addRecipe(name, ingredient, instructions);
        if (RecipeInfo.isValidRecipeId(err)) {
            return "Successfully added " + name + "!";
        } else {
            return "Oops! Could not add " + name + ".";
        }
    }

    public String onDeleteAllRecipes(Context context) {
        VerifyDatabase(context);
        mDatabase.deleteAllRecipes();
        return "All recipes have been deleted.";
    }

    public void onDeleteRecipe(Context context, String name) {
        VerifyDatabase(context);
        mDatabase.deleteRecipe(name);
    }
}
