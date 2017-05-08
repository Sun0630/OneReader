package com.sx.onereader.news.presenter;

import android.content.Context;
import android.content.Intent;

import com.orhanobut.logger.Logger;
import com.sx.onereader.detail.BeanType;
import com.sx.onereader.news.bean.NewsBean;
import com.sx.onereader.news.contract.NewsListContract;
import com.sx.onereader.utils.Api;
import com.sx.onereader.utils.ApiService;
import com.sx.onereader.utils.HttpUtils;
import com.sx.onereader.webview.WebViewDetailActivity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/15.
 */
public class NewsListPresenter implements NewsListContract.Presenter {


    private final Context context;
    private final NewsListContract.View view;
    private int mPager =1;
    private List<NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean> list = new ArrayList<>();
    public NewsListPresenter(Context context, NewsListContract.View view){
        this.context = context;
        this.view = view;
        this.view.setPresenter(this);
    }


    @Override
    public void request(String type, int page, String time, final boolean clearing) {
        if(clearing){
            view.showLoading();
        }
        HttpUtils.getInstance().create(ApiService.class,Api.SHOWAPI_NEWS)
                .getNews(type,page,time)
                .subscribeOn(Schedulers.io())//请求在子线程
                .observeOn(AndroidSchedulers.mainThread())//在主线程更新UI
                .subscribe(new Consumer<NewsBean>() {//订阅
                    @Override
                    public void accept(NewsBean newsBean) throws Exception {
                        if(clearing){
                            list.clear();
                        }
                        for(NewsBean.ShowapiResBodyBean.PagebeanBean.ContentlistBean contentlistBean:newsBean.getShowapi_res_body().getPagebean().getContentlist()){
                            list.add(contentlistBean);
                        }
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
    public void loadMore(String type , String time) {
        Logger.d("loadMore");
        mPager =mPager+1;
        request(type,mPager,time,false);
    }

    @Override
    public void refresh(String type,String time) {
        mPager=1;
        request(type,mPager,time,true);
    }

    @Override
    public void showDetail(int position) {
        context.startActivity(new Intent(context, WebViewDetailActivity.class)
                .putExtra("type", BeanType.TYPE_NEWS)
                .putExtra("link", list.get(position).getLink()+""));
    }

    @Override
    public void start() {

      //  request(NewsFragment.TYPE_DOMESTIC,1, dateFomatter.NewsDateFormat(),true);
    }
}
