package com.example.mamascookbook.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.mamascookbook.R;

import static android.util.Log.d;

public class ListViewItemFragment extends Fragment {
    private static final String TAG = "Tag-ListViewItemFrag";

    private String mName;
    private String mIngredients;
    private String mInstructions;
    private RecipeListViewModel mRecipeListViewModel;

    public ListViewItemFragment() {
        // Required empty public constructor
    }

    public static ListViewItemFragment newInstance(String name, String ingredients,
                                                   String instructions) {
        ListViewItemFragment fragment = new ListViewItemFragment();
        Bundle args = new Bundle();
        args.putString(Constants.TAG_RECIPE_NAME, name);
        args.putString(Constants.TAG_RECIPE_INGREDIENTS, ingredients);
        args.putString(Constants.TAG_RECIPE_INSTRUCTIONS, instructions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mName = getArguments().getString(Constants.TAG_RECIPE_NAME);
            mIngredients = getArguments().getString(Constants.TAG_RECIPE_INGREDIENTS);
            mInstructions = getArguments().getString(Constants.TAG_RECIPE_INSTRUCTIONS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRecipeListViewModel = new ViewModelProvider(requireActivity(),
                new RecipeListViewModelFactory(getContext())).get(RecipeListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_list_view_item, container, false);

        TextView textView = (TextView) root.findViewById(R.id.text_ingredients);
        textView.setText(mIngredients);
        textView = (TextView) root.findViewById(R.id.text_instructions);
        textView.setText(mInstructions);

        ImageButton deleteButton = root.findViewById(R.id.button_delete_item);
        if (deleteButton != null) {
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d(TAG, "onClick button_delete_item(" + mName + ")");
                    mRecipeListViewModel.onDeleteRecipe(getContext(), mName);

                    // refresh the recipe list

                    getActivity().onBackPressed();
                }
            });
        }

        return root;
    }
}