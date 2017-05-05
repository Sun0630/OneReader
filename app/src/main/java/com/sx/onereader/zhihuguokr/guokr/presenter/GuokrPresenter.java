package com.sx.onereader.zhihuguokr.guokr.presenter;

import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;
import com.sx.onereader.detail.BeanType;
import com.sx.onereader.webview.WebViewDetailActivity;
import com.sx.onereader.zhihuguokr.guokr.bean.GuokrHandpickNews;
import com.sx.onereader.zhihuguokr.guokr.contract.GuokrContract;
import com.sx.onereader.utils.Api;
import com.sx.onereader.utils.ApiService;
import com.sx.onereader.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/13.
 */
public class GuokrPresenter implements GuokrContract.Presenter {
    private Context mContext;
    private GuokrContract.View view;
    private List<GuokrHandpickNews.result> list = new ArrayList<>();
    public GuokrPresenter(Context context,GuokrContract.View view){
        this.mContext =context;
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    public void requestData() {
        list.clear();
        view.showLoading();
        HttpUtils.getInstance()
                .create(ApiService.class,Api.GUOKR_ARTICLES)
                .getGuokrHandpick()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<GuokrHandpickNews>() {
                    @Override
                    public void accept(GuokrHandpickNews guokrHandpickNews) throws Exception {

                        for(GuokrHandpickNews.result result :guokrHandpickNews.getResult()){
                            list.add(result);
                        }
                        Logger.d(list.size()+"");
                        view.showResult(list);
                        view.stopLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.stopLoading();
                        view.showError();
                    }
                });
    }

    @Override
    public void refresh() {
        requestData();
    }

    @Override
    public void loadDetail(int position) {
        mContext.startActivity(new Intent(mContext, WebViewDetailActivity.class)
                .putExtra("type", BeanType.TYPE_GUOKR)
                .putExtra("id", list.get(position).getId()+"")
                .putExtra("title", list.get(position).getTitle()));
    }



    @Override
    public void start() {
        requestData();
    }
}
