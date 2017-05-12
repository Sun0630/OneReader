package com.sx.onereader.zhihuguokr.guokr.contract;

import com.sx.onereader.base.BasePresenter;
import com.sx.onereader.base.BaseView;
import com.sx.onereader.zhihuguokr.guokr.bean.GuokrHandpickNews;

import java.util.List;

/**
 * Created by sunxin on 2017/3/13.
 */
public interface GuokrContract  {
    interface View extends BaseView<Presenter>{
        void showLoading();
        void stopLoading();
        void showError();
        void showResult(List<GuokrHandpickNews.result> list);
    }
    interface Presenter extends BasePresenter{
        void requestData();
        void refresh();
        void loadDetail(int position);

    }
}
