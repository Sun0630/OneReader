package com.sx.onereader.todayofhistory.contract;

import com.sx.onereader.base.BasePresenter;
import com.sx.onereader.base.BaseView;
import com.sx.onereader.todayofhistory.bean.TodayOfHistoryDetailBean;

/**
 * @Author sunxin
 * @Date 2017/5/1 9:36
 * @Description
 */

public interface TodayOfHistoryDetailContract  {
    interface  View extends BaseView<Presenter>{
        void showResult(TodayOfHistoryDetailBean todayOfHistoryDetailBean);
        void showError();
        void shareData(String txt);
    }
    interface Presenter extends BasePresenter{
        void requestData(String id);

    }
}
