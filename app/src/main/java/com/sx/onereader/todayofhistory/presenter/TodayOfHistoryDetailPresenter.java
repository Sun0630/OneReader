package com.sx.onereader.todayofhistory.presenter;

import android.content.Context;

import com.sx.onereader.todayofhistory.bean.TodayOfHistoryDetailBean;
import com.sx.onereader.todayofhistory.contract.TodayOfHistoryDetailContract;
import com.sx.onereader.utils.Api;
import com.sx.onereader.utils.ApiService;
import com.sx.onereader.utils.HttpUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * time:2017/3/28
 * Created by w77996
 * Github:https://github.com/w77996
 * CSDN:http://blog.csdn.net/w77996?viewmode=contents
 */
public class TodayOfHistoryDetailPresenter implements TodayOfHistoryDetailContract.Presenter {

    private  Context context;
    TodayOfHistoryDetailContract.View view;
    public TodayOfHistoryDetailPresenter(Context context, TodayOfHistoryDetailContract.View  view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }



    @Override
    public void requestData(String  id) {
        HttpUtils.getInstance()
                .create(ApiService.class, Api.TODAY_HISTORY)
                .getTodayOfHistoryDetailData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TodayOfHistoryDetailBean>() {
                    @Override
                    public void accept(TodayOfHistoryDetailBean todayOfHistoryDetailBean) throws Exception {
                        view.showResult(todayOfHistoryDetailBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();
                    }
                });
    }



    @Override
    public void start() {

    }
}
