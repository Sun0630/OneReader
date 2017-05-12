package com.sx.onereader.zhihuguokr.zhihudaily;

import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;
import com.sx.onereader.detail.BeanType;
import com.sx.onereader.utils.Api;
import com.sx.onereader.utils.ApiService;
import com.sx.onereader.utils.DateFomatter;
import com.sx.onereader.utils.HttpUtils;
import com.sx.onereader.webview.WebViewDetailActivity;
import com.sx.onereader.zhihuguokr.zhihudaily.bean.ZhihuDailyBean;
import com.sx.onereader.zhihuguokr.zhihudaily.contract.ZhihuDailyContract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/3/11.
 */
public class ZhihuDailyPresenter implements ZhihuDailyContract.Presenter {

    private ZhihuDailyContract.View view;
    private Context mContext;
    private List<ZhihuDailyBean.StoriesBean> list = new ArrayList<ZhihuDailyBean.StoriesBean>();

    public ZhihuDailyPresenter(Context context, ZhihuDailyContract.View view) {
        this.mContext = context;
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void requestData(long date, final boolean clearing) {
        DateFomatter dateFomatter = new DateFomatter();
        if (clearing) {
            view.showLoading();
        }

        HttpUtils.getInstance()
                .create(ApiService.class, Api.ZHIHU_HISTORY)
                .getZhihuDaily(dateFomatter.ZhihuDailyDateFormat(date))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ZhihuDailyBean>() {
                    @Override
                    public void accept(ZhihuDailyBean zhihuDailyBean) throws Exception {
                        if (clearing) {
                            list.clear();
                        }
                        for (ZhihuDailyBean.StoriesBean storiesBean : zhihuDailyBean.getStories()) {
                            list.add(storiesBean);
                        }
                        view.showResult(list);
                        view.stopLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        com.orhanobut.logger.Logger.d(throwable);
                        view.showError();
                        view.stopLoading();
                    }
                });
    }

    @Override
    public void refresh() {
        requestData(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        requestData(date, false);
    }

    @Override
    public void loadDetail(int position) {
        Intent intent = new Intent(mContext, WebViewDetailActivity.class);
        Logger.d(list.get(position).getId());
        intent.putExtra("id", list.get(position).getId() + "");
        intent.putExtra("type", BeanType.TYPE_ZHIHU);
        Logger.d(position);
        mContext.startActivity(intent);
    }

    @Override
    public void start() {
        requestData(Calendar.getInstance().getTimeInMillis(), true);
    }
}
