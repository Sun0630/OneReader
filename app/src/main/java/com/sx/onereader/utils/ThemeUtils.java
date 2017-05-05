package com.sx.onereader.utils;

import android.app.Activity;

import com.sx.onereader.R;


/**
 * @Author sunxin
 * @Date 2017/5/5 16:42
 * @Description  改变主题
 */

public class ThemeUtils {

    public static boolean isLight = true;

    public static void changeTheme(Activity activity) {
        if (!isLight) {
            activity.setTheme(R.style.Base_Theme_AppTheme_Dark);
        }
    }
}
