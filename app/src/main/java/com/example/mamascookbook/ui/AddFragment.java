package com.example.mamascookbook.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.mamascookbook.R;
import com.example.mamascookbook.ui.theme.ThemeSnackbar;

import static android.util.Log.d;

public class AddFragment extends Fragment {
    private static final String TAG = "Tag-AddFragment";

    private RecipeListViewModel mRecipeListViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        d(TAG, "onCreateView");
        mRecipeListViewModel = new ViewModelProvider(requireActivity(),
                new RecipeListViewModelFactory(getContext())).get(RecipeListViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add, container, false);

        ImageButton addNewButton = root.findViewById(R.id.button_add_new);
        if (addNewButton != null) {
            addNewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    d(TAG, "onClick button_add_new");
                    final EditText amountText = root.findViewById(R.id.text_recipe_amount);
                    final EditText instructionsText = root.findViewById(R.id.text_recipe_instructions);
                    final EditText nameText = root.findViewById(R.id.text_recipe_name);
                    final Spinner measurementSpinner = root.findViewById(R.id.spinner_measurement);
                    final String message = mRecipeListViewModel.onAddRecipe(
                            getContext(),
                            nameText.getText().toString(),
                            amountText.getText().toString() + " " + measurementSpinner.getSelectedItem().toString(),
                            instructionsText.getText().toString()
                    );
                    // notify the user of the insert's status
                    ThemeSnackbar.make(view, getContext(), getResources(), message)
                            .setAction("Action", null).show();
                }
            });
        }

        return root;
    }
}
