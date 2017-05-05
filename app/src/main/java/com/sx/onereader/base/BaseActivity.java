package com.sx.onereader.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.sx.onereader.utils.ThemeUtils;

/**
 * @Author sunxin
 * @Date 2017/5/4 23:21
 * @Description
 */

public class BaseActivity extends AppCompatActivity{

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        ThemeUtils.changeTheme(this);
        super.onCreate(savedInstanceState);

    }
}
