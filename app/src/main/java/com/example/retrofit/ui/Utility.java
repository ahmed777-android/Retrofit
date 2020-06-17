package com.example.retrofit.ui;

import android.content.Context;
import android.util.DisplayMetrics;

public class Utility {
    // For example column Width dp=180
    public static int calculateNoOfColumns(Context context, float columnWidthDp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        float screenWidthDp = displayMetrics.widthPixels / displayMetrics.density;
        int noOfColumns = (int) (screenWidthDp / columnWidthDp + 0.5);
        // +0.5 for correct rounding to int.
        return noOfColumns;
    }

}
