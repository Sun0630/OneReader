package com.sx.onereader.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.orhanobut.logger.Logger;
import com.sx.onereader.R;
import com.sx.onereader.zhihuguokr.zhihudaily.bean.ZhihuDetailBean;
import com.sx.onereader.utils.Api;
import com.sx.onereader.utils.ApiService;
import com.sx.onereader.utils.HttpUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sunxin on 2017/3/15.
 */
public class DetailActivity extends AppCompatActivity {
    WebView webView;
    private Toolbar toolbar;
    private int mType;
    private String mUrl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_fragment);

         webView = (WebView)findViewById(R.id.webview);
        toolbar = (Toolbar)findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      /*  DetailFragment detailFragment = DetailFragment.newInstance();
        if(!detailFragment.isAdded()){
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_frament,detailFragment,"DetailFragment").commit();
        }*/
        //setHasOptionsMenu(true);
        WebSettings webSettings  =webView.getSettings();
        webView.setScrollbarFadingEnabled(true);
        webSettings.setJavaScriptEnabled(true);
       webSettings.setDomStorageEnabled(true);
        webSettings.setBuiltInZoomControls(false);
        Intent intent = getIntent();
        mType = intent.getIntExtra("type",1);
        webView.setWebViewClient(new webViewClient());
        String id;
        String url;
        switch (mType){
            case BeanType.TYPE_ZHIHU:
                id= intent.getStringExtra("id");
                url= Api.ZHIHU_NEWS+id;

             HttpUtils.getInstance()
                        .create(ApiService.class,Api.ZHIHU_NEWS)
                        .getZhihuDetailNews(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<ZhihuDetailBean>() {
                            @Override
                            public void accept(ZhihuDetailBean zhihuDetailBean) throws Exception {
                                    String url = zhihuDetailBean.getShare_url();
                                Logger.d(url);
                                webView.loadUrl(url);
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Logger.e(throwable+"");
                            }
                        });
                break;
            case BeanType.TYPE_GUOKR:
                 id= intent.getStringExtra("id");
                 url = Api.GUOKR_ARTICLE_LINK+"pick/"+id;
                Logger.d(url);
                webView.loadUrl(url);
                break;
            case BeanType.TYPE_HISTORY:

                break;
            case BeanType.TYPE_NEWS:
                id= intent.getStringExtra("link");
                webView.loadUrl(id);
                break;
        }
      //  String url = "http://daily.zhihu.com/story/9289923";
     //   Logger.d(url);
      //  webView.loadUrl(url);

      //  Logger.d(Api.ZHIHU_NEWS+url);

    }


    private class webViewClient extends WebViewClient {
        //设置不跳转系统自带的浏览器
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
