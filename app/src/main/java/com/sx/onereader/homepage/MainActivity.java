package com.sx.onereader.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.sx.onereader.R;
import com.sx.onereader.about.AboutActivity;
import com.sx.onereader.base.BaseActivity;
import com.sx.onereader.chat.ChatFragment;
import com.sx.onereader.news.NewsFragment;
import com.sx.onereader.searchbook.SearchBookActivity;
import com.sx.onereader.setting.SettingActivity;
import com.sx.onereader.todayofhistory.TodayOfHistoryFragment;
import com.sx.onereader.todayofhistory.presenter.TodayOfHistoryPresenter;
import com.sx.onereader.utils.ThemeUtils;
import com.sx.onereader.weather.WeatherFragment;
import com.sx.onereader.weather.presenter.WeatherPresenter;
import com.sx.onereader.zhihuguokr.ZhihuGuokrMainFragment;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    private ZhihuGuokrMainFragment zhihuGuokrMainFragment;
    private TodayOfHistoryFragment todayOfHistoryFragment;
    private TodayOfHistoryPresenter todayOfHistoryPresenter;
    private ChatFragment chatFragment;
    private NewsFragment newsFragment;
    private WeatherFragment weatherFragment;
    private WeatherPresenter weatherPresenter;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ThemeUtils.changeTheme(this);
//        MainActivity.this.recreate();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        zhihuGuokrMainFragment = ZhihuGuokrMainFragment.newInstance();
        todayOfHistoryFragment = TodayOfHistoryFragment.newInstance();
        newsFragment = NewsFragment.newInstance();
        chatFragment = ChatFragment.newIntance();
        weatherFragment = WeatherFragment.newInstance();

        todayOfHistoryPresenter = new TodayOfHistoryPresenter(MainActivity.this, todayOfHistoryFragment);
        weatherPresenter = new WeatherPresenter(MainActivity.this, weatherFragment);
        if (!zhihuGuokrMainFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, zhihuGuokrMainFragment, "MainFragment").commit();
        }
        if (!todayOfHistoryFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, todayOfHistoryFragment, "todyFragment").commit();
        }
        if (!newsFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, newsFragment, "newsFragment").commit();
        }
        if (!chatFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, chatFragment, "chatFragment").commit();
        }
        if (!weatherFragment.isAdded()) {
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, weatherFragment, "waetherFragment").commit();
        }
        showNewsFragment();
//            showTodayOfHitoryFragment();
    }

    private void initView() {
        drawerLayout = (DrawerLayout) findViewById(R.id.mian_drawlayout);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("新闻资讯");
        navigationView = (NavigationView) findViewById(R.id.main_nav_view);

        View headerView = navigationView.getHeaderView(0);
        CircleImageView circleImageView = (CircleImageView) headerView.findViewById(R.id.profile_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    showMainFragment();
                } else if (id == R.id.nav_todayofhistory) {
                    showTodayOfHitoryFragment();
                } else if (id == R.id.nav_news) {
                    showNewsFragment();
                } else if (id == R.id.nav_about) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, AboutActivity.class));
                } else if (id == R.id.nav_chat) {
                    showChatFragment();
                } else if (id == R.id.nav_weather) {
                    showWeatherFragment();
                } else if (id == R.id.nav_settings) {
                    MainActivity.this.startActivity(new Intent(MainActivity.this, SettingActivity.class));
                } else if (id == R.id.nav_search) {
                    //扫码查书
                    MainActivity.this.startActivity(new Intent(MainActivity.this, SearchBookActivity.class));
                }
                return true;
            }


        });


    }

    private void showChatFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(chatFragment);
        fragmentTransaction.hide(todayOfHistoryFragment);
        fragmentTransaction.hide(newsFragment);
        fragmentTransaction.hide(zhihuGuokrMainFragment);
        fragmentTransaction.hide(weatherFragment);
        fragmentTransaction.commit();

        toolbar.setTitle("聊天机器人");
    }

    private void showMainFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(zhihuGuokrMainFragment);
        fragmentTransaction.hide(todayOfHistoryFragment);
        fragmentTransaction.hide(newsFragment);
        fragmentTransaction.hide(chatFragment);
        fragmentTransaction.hide(weatherFragment);
        fragmentTransaction.commit();

        toolbar.setTitle("知乎果壳");

    }

    private void showTodayOfHitoryFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(todayOfHistoryFragment);
        fragmentTransaction.hide(zhihuGuokrMainFragment);
        fragmentTransaction.hide(newsFragment);
        fragmentTransaction.hide(chatFragment);
        fragmentTransaction.hide(weatherFragment);
        fragmentTransaction.commit();

        toolbar.setTitle("历史上的今天");

    }

    private void showNewsFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(newsFragment);
        fragmentTransaction.hide(zhihuGuokrMainFragment);
        fragmentTransaction.hide(todayOfHistoryFragment);
        fragmentTransaction.hide(chatFragment);
        fragmentTransaction.hide(weatherFragment);
        fragmentTransaction.commit();

        toolbar.setTitle("新闻资讯");
    }

    private void showWeatherFragment() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(weatherFragment);
        fragmentTransaction.hide(zhihuGuokrMainFragment);
        fragmentTransaction.hide(todayOfHistoryFragment);
        fragmentTransaction.hide(chatFragment);
        fragmentTransaction.hide(newsFragment);
        fragmentTransaction.commit();

        toolbar.setTitle("城市天气");
    }

}
