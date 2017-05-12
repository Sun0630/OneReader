package com.sx.onereader.todayofhistory.contract;

import com.sx.onereader.base.BasePresenter;
import com.sx.onereader.base.BaseView;
import com.sx.onereader.todayofhistory.bean.TodayOfHistoryBean;

import java.util.List;

/**
 * Created by sunxin on 2017/3/14.
 */
public interface TodayOfHistoryContract {

    interface  View extends BaseView<Presenter>{
        void showLoading();
        void stopLoading();
        void showError();
        void showResult(List<TodayOfHistoryBean.ResultBean> list);
    }
    interface Presenter extends BasePresenter{
        void requestData();
        void refresh();
        void loadDetail(int position);

    }
}
