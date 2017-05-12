package com.sx.onereader.detail;

import com.sx.onereader.base.BasePresenter;
import com.sx.onereader.base.BaseView;

/**
 * Created by sunxin on 2017/3/15.
 */
interface  DetailContract {
    interface View extends BaseView<Presenter>{
        void showLoading();
        void stopLoading();
        void showResult(String url);
        void setTitle(String title);

    }
    interface Presenter extends BasePresenter{

    }

}
