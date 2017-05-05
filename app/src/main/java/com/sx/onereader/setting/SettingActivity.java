package com.sx.onereader.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.orhanobut.logger.Logger;
import com.sx.onereader.R;
import com.sx.onereader.base.BaseActivity;
import com.sx.onereader.utils.GlideCacheUtils;
import com.sx.onereader.utils.ThemeUtils;

/**
 * time:2017/3/29
 * Created by w77996
 * Github:https://github.com/w77996
 * CSDN:http://blog.csdn.net/w77996?viewmode=contents
 */
public class SettingActivity extends BaseActivity {
    private Toolbar mToolbar;
    private TextView mTextViewCache;
    private RelativeLayout mLinearLayoutClearCache;
    private RelativeLayout mChangeSkin;
    boolean isDay = true;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mToolbar = (Toolbar)findViewById(R.id.setting_toolbar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        //mToolbar.setTitle("设置");
        actionBar.setTitle("设置");
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);
        Logger.d(GlideCacheUtils.getInstance().getCacheSize(getApplicationContext()));
        mTextViewCache = (TextView)findViewById(R.id.setting_cache);
        mTextViewCache.setText(GlideCacheUtils.getInstance().getCacheSize(getApplicationContext()));
        mLinearLayoutClearCache = (RelativeLayout)findViewById(R.id.setting_clear_layout);
        //更换白天夜间模式
        final ImageView skinDrawer = (ImageView) findViewById(R.id.drawer);
        final TextView tv_skin = (TextView) findViewById(R.id.tv_skin);

        mChangeSkin = (RelativeLayout) findViewById(R.id.rl_change_skin);
        skinDrawer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDay){
                    skinDrawer.setImageResource(R.drawable.open);
                    tv_skin.setText("日间模式");
                    isDay = false;
                }else{
                    skinDrawer.setImageResource(R.drawable.close);
                    tv_skin.setText("夜间模式");
                    isDay = true;
                }
                ThemeUtils.isLight = !ThemeUtils.isLight;
                SettingActivity.this.recreate();//重新创建当前Activity实例
            }
        });
        mLinearLayoutClearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlideCacheUtils.getInstance().clearImageAllCache(getApplicationContext());
                mTextViewCache.setText("0.0Byte");
                Toast.makeText(getApplicationContext(),"清除成功",Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
