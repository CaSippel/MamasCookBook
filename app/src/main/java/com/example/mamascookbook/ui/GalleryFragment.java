package com.example.mamascookbook.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mamascookbook.ListviewActivity;
import com.example.mamascookbook.R;
import com.example.mamascookbook.RecipeInfo;

import java.util.List;

public class GalleryFragment extends Fragment {
    private static final String TAG = "Tag-GalleryFragment";

    List<RecipeInfo> mRecipes;
    private RecipeListViewModel mRecipeListViewModel;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance() {
        return new GalleryFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        mRecipeListViewModel = new ViewModelProvider(requireActivity(),
                new RecipeListViewModelFactory(getContext())).get(RecipeListViewModel.class);
        mRecipes = mRecipeListViewModel.getRecipeList(getContext());

        ListView mRecipeListView = (ListView) root.findViewById(R.id.listview_recipes);
        ArrayAdapter<RecipeInfo> arrayAdapter = new ArrayAdapter<RecipeInfo>(getContext(),
                R.layout.activity_listview, R.id.listview_item_display, mRecipes);
        mRecipeListView.setAdapter(arrayAdapter);

        mRecipeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object item = adapterView.getItemAtPosition(i);

                Intent intent = new Intent(getContext(), ListviewActivity.class);
                // based on item add info to intent
                RecipeInfo recipeInfo = mRecipes.get(i);
                intent.putExtra(Constants.TAG_RECIPE_NAME, recipeInfo.getName());
                intent.putExtra(Constants.TAG_RECIPE_INGREDIENTS, recipeInfo.getIngredient());
                intent.putExtra(Constants.TAG_RECIPE_INSTRUCTIONS, recipeInfo.getInstructions());
                startActivity(intent);
            }
        });

        return root;
    }
}