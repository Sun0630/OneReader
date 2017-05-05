package com.sx.onereader.chat.presenter;

import android.content.Context;

import com.sx.onereader.chat.bean.ChatBean;
import com.sx.onereader.chat.bean.ChatMsgBean;
import com.sx.onereader.chat.contract.ChatContract;
import com.sx.onereader.utils.Api;
import com.sx.onereader.utils.ApiService;
import com.sx.onereader.utils.HttpUtils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/17.
 */
public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View view;
    public ChatPresenter(Context context,ChatContract.View view){
        this.view =view;
        this.view.setPresenter(this);
    }


    @Override
    public void requestData(String data) {
        HttpUtils.getInstance()
                .create(ApiService.class,Api.ROBOT)
                .getChatData(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ChatBean>() {
                    @Override
                    public void accept(ChatBean chatBean) throws Exception {
                        String msg = chatBean.getText();
                        ChatMsgBean chatMsgBean = new ChatMsgBean();
                        chatMsgBean.setMsg(msg);
                        chatMsgBean.setType(ChatMsgBean.TYPE_RECIV);
                        view.showReslut(chatMsgBean);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();
                    }
                });
    }

    @Override
    public void sendMsg(String data) {
        requestData(data);
    }

    @Override
    public void start() {

    }
}
