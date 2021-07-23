package com.example.mamascookbook.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mamascookbook.R;
import com.example.mamascookbook.ui.theme.ThemeSnackbar;

import static android.util.Log.d;

public class HomeFragment extends Fragment {
    private static final String TAG = "Tag-HomeFragment";

    private RecipeListViewModel mRecipeListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRecipeListViewModel = new ViewModelProvider(requireActivity(),
                new RecipeListViewModelFactory(getContext())).get(RecipeListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final TextView textView = root.findViewById(R.id.text_welcome);
        mRecipeListViewModel.getHomeText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        Button gotoAddFragmentButton = root.findViewById(R.id.goto_add);
        if (gotoAddFragmentButton != null) {
            gotoAddFragmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d(TAG, "onClick goto_add");
                    NavController navController = Navigation.findNavController(requireActivity(),
                            R.id.nav_host_fragment);
                    navController.navigate(R.id.action_goto_add);
                }
            });
        }

        Button gotoGalleryFragmentButton = root.findViewById(R.id.goto_gallery);
        if (gotoGalleryFragmentButton != null) {
            gotoGalleryFragmentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d(TAG, "onClick goto_gallery");
                    NavController navController = Navigation.findNavController(requireActivity(),
                            R.id.nav_host_fragment);
                    navController.navigate(R.id.action_goto_gallery);
                }
            });
        }

        Button deleteAllButton = root.findViewById(R.id.button_delete_all);
        if (deleteAllButton != null) {
            deleteAllButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d(TAG, "onClick button_delete_all");
                    final String message = mRecipeListViewModel.onDeleteAllRecipes(getContext());
                    // notify the user of the action
                    ThemeSnackbar.make(view, getContext(), getResources(), message)
                            .setAction("Action", null).show();
                }
            });
        }

        return root;
    }
}