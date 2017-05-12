package com.sx.onereader.weather.contract;

import com.sx.onereader.base.BasePresenter;
import com.sx.onereader.base.BaseView;
import com.sx.onereader.weather.bean.WeatherBean;

/**
 * @Author sunxin
 * @Date 2017/4/8 22:03
 * @Description
 */

public interface WeatherContract  {
    interface View extends BaseView<Presenter>{
        void showLoading();
        void showError();
        void stopLoading();
        void showWeather(WeatherBean weather);
    }
    interface Presenter extends BasePresenter{
        void loadLocation();
        void requestData(String city);
    }
}
