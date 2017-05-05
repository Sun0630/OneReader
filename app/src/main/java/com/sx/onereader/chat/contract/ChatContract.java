package com.sx.onereader.chat.contract;

import com.sx.onereader.base.BasePresenter;
import com.sx.onereader.base.BaseView;
import com.sx.onereader.chat.bean.ChatMsgBean;

/**
 * Created by Administrator on 2017/3/16.
 */
public interface ChatContract {
    interface View extends BaseView<Presenter>{
        void showError();
        void showReslut(ChatMsgBean list);
    }
    interface Presenter extends BasePresenter{
        void requestData(String data);

        void sendMsg(String data);
    }
}
