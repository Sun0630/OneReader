package com.sx.onereader.searchbook.contract;

import com.sx.onereader.base.BasePresenter;
import com.sx.onereader.base.BaseView;
import com.sx.onereader.searchbook.bean.ISBNBookBean;

import java.util.List;

/**
 * @Author sunxin
 * @Date 2017/5/8 22:02
 * @Description 查找图书的合同类
 */

public class BookContract {
    public interface View extends BaseView<Presenter> {
        void showLoading();

        void showError();

        void stopLoading();

        void showBook(List<ISBNBookBean> list);

        void showEmpty();

        void loadDetail(int position);
    }

    public interface Presenter extends BasePresenter {
        void requestData(String isbn, boolean clearing);
        void updateBook();//从数据库中取出喜欢的图书
    }
}

