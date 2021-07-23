package com.example.mamascookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.mamascookbook.ui.Constants;
import com.example.mamascookbook.ui.ListViewItemFragment;

public class ListviewActivity extends AppCompatActivity {
    private static final String TAG = "Tag-ListviewActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_list_view_item);
        getSupportActionBar().setTitle(
                getIntent().getStringExtra(Constants.TAG_RECIPE_NAME));

        // display info on a newfragment
        ListViewItemFragment newFragment = ListViewItemFragment.newInstance(
                getIntent().getStringExtra(Constants.TAG_RECIPE_NAME),
                getIntent().getStringExtra(Constants.TAG_RECIPE_INGREDIENTS),
                getIntent().getStringExtra(Constants.TAG_RECIPE_INSTRUCTIONS)
        );
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_list_view_item, newFragment);
        transaction.commit();
    }
}