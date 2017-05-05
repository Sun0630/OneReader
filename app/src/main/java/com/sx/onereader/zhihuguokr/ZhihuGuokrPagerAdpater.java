package com.sx.onereader.zhihuguokr;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sx.onereader.zhihuguokr.guokr.GuokrFragment;
import com.sx.onereader.zhihuguokr.zhihudaily.ZhihuDailyFragment;

/**
 * Created by Administrator on 2017/3/13.
 */
public class ZhihuGuokrPagerAdpater extends FragmentPagerAdapter{
    private ZhihuDailyFragment zhihuDailyFragment;
    private GuokrFragment guokrFragment;
    private Context mContext;

    private String[] titles;
    public ZhihuGuokrPagerAdpater(FragmentManager fm, Context context, ZhihuDailyFragment zhihuDailyFragment, GuokrFragment guokrFragment){
        super(fm);
        this.mContext =context;
        this.zhihuDailyFragment =zhihuDailyFragment;
        this.guokrFragment =guokrFragment;
        titles = new String[]{
                "知乎日报","果壳精选"
        };
    }


    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return zhihuDailyFragment;
        } else if (position == 1){
            return guokrFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
