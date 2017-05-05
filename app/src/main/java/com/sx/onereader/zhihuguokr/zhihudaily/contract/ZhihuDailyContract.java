package com.sx.onereader.zhihuguokr.zhihudaily.contract;

import com.sx.onereader.base.BasePresenter;
import com.sx.onereader.base.BaseView;
import com.sx.onereader.zhihuguokr.zhihudaily.bean.ZhihuDailyBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/11.
 */
public interface ZhihuDailyContract {
    interface View extends BaseView<Presenter>{
        void showLoading();
        void stopLoading();
        void showError();
        void showResult(List<ZhihuDailyBean.StoriesBean> list);
        void showPickerDialog();

    }
    interface  Presenter extends BasePresenter {
        void requestData(long date,boolean clearing);
        void refresh();
        void loadMore(long date);
        void loadDetail(int position);
    }
}
