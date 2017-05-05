package com.sx.onereader;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author sunxin
 * @Date 2017/5/2 9:26
 * @Description
 */

public class App extends Application {
    private static Context mContext;
    private static List<Activity> mActivities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
