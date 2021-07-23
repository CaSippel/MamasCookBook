package com.example.mamascookbook.ui;

import android.content.Context;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RecipeListViewModelFactory implements ViewModelProvider.Factory {
    private Context mContext;

    public RecipeListViewModelFactory(Context context) {
        mContext = context;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new RecipeListViewModel(mContext);
    }
}
