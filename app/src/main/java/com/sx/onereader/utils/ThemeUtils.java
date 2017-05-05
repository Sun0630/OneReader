package com.sx.onereader.utils;

import android.app.Activity;

import com.sx.onereader.R;


/**
 * Created by tangqi on 10/5/15.
 */
public class ThemeUtils {

    public static boolean isLight = true;

    public static void changeTheme(Activity activity) {
        if (!isLight) {
            activity.setTheme(R.style.Base_Theme_AppTheme_Dark);
        }
    }
}
