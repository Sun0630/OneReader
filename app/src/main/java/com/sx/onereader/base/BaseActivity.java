package com.sx.onereader.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sx.onereader.utils.ThemeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author sunxin
 * @Date 2017/4/4 23:21
 * @Description 基类
 */

public class BaseActivity extends AppCompatActivity{
    // 共享
    private static List<BaseActivity> mActivities = new ArrayList<>();
    //记录当前显示的Activity对象
    public static BaseActivity sActivity;
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActivities.add(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.changeTheme(this);
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sActivity  = this;
    }


    @Override
    protected void onPause() {
        super.onPause();
        sActivity = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mActivities.remove(this);
    }


    public void killAll() {
        //1.复制新的集合
        //2. CopyOnWriteArrayList 可以在遍历中移除元素
        List<BaseActivity> copy = new ArrayList<>(mActivities);

        for (BaseActivity activity : copy) {
            activity.finish();
        }
        //自杀进程
//        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
