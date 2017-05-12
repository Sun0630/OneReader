package com.sx.onereader.searchbook.presenter;

import android.content.Context;

import com.sx.onereader.searchbook.bean.ISBNBookBean;
import com.sx.onereader.searchbook.contract.BookContract;
import com.sx.onereader.utils.Api;
import com.sx.onereader.utils.ApiService;
import com.sx.onereader.utils.HttpUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author sunxin
 * @Date 2017/5/8 22:08
 * @Description 查找图书的主持类
 */

public class SearchBookPresenter implements BookContract.Presenter {


    private Context mContext;
    private BookContract.View mView;
    private List<ISBNBookBean> mList = new ArrayList<>();

    public SearchBookPresenter(Context context, BookContract.View view) {

        mContext = context;
        mView = view;
    }

    @Override
    public void start() {
//        requestData(isbn, true);
    }

    @Override
    public void requestData(String isbn, final boolean clearing) {
        if (clearing) {
            mView.showLoading();
        }

        HttpUtils
                .getInstance()
                .create(ApiService.class, Api.BOOK)
                .getBookInfo(isbn)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ISBNBookBean>() {
                    @Override
                    public void accept(ISBNBookBean bean) throws Exception {
                        mList.add(bean);
                        System.out.println("ISBNBOOKBEAN:" + mList.toString());
                        mView.showBook(mList);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.stopLoading();
                        mView.showError();
                    }
                });

    }

    @Override
    public void updateBook() {

    }
}
