package com.example.mamascookbook.ui.theme;

import android.content.Context;
import android.content.res.Resources;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.example.mamascookbook.R;
import com.google.android.material.snackbar.Snackbar;

public final class ThemeSnackbar {
    // a wrapper around the SQLite db handler to isolate the handler's usage
    private static final String TAG = "Tag-ThemeSnackbar";

    public static Snackbar make(View view, Context context, Resources resources, String message) {
        return make(view, context, resources, message, Snackbar.LENGTH_LONG);
    }

    public static Snackbar make(View view, Context context, Resources resources,
                                String message, int duration) {
        Snackbar snackbar = Snackbar.make(view, message, duration);
        View snackbarView = snackbar.getView();
        // set the shape and colors of the snackbar
        snackbarView.setBackground(ContextCompat.getDrawable(context, R.drawable.snackbar_background));
        snackbar.setBackgroundTint(resources.getColor(R.color.snackbarBackground));
        snackbar.setTextColor(resources.getColor(R.color.snackbarText));
        snackbar.setActionTextColor(resources.getColor(R.color.snackbarText));
        return snackbar;
    }
}
