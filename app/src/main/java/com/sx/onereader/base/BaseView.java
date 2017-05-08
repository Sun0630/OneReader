package com.sx.onereader.base;

import android.view.View;


public interface BaseView <T>{
    void setPresenter(T presenter);
    void initView(View view);
}
