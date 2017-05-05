package com.sx.onereader.weather.presenter;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.orhanobut.logger.Logger;
import com.sx.onereader.utils.Api;
import com.sx.onereader.utils.ApiService;
import com.sx.onereader.utils.DateFomatter;
import com.sx.onereader.utils.HttpUtils;
import com.sx.onereader.weather.CityFomat;
import com.sx.onereader.weather.bean.WeatherBean;
import com.sx.onereader.weather.contract.WeatherContract;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/3/16.
 */
public class WeatherPresenter implements WeatherContract.Presenter {

    private  WeatherContract.View view;
    private Context context;

    public AMapLocationClient mLocationClient = null;
    public AMapLocationClientOption mLocationOption = null;
    public WeatherPresenter(Context context , WeatherContract.View view){
        this.context = context;
        this.view =view;
        this.view.setPresenter(this);
    }

    @Override
    public void loadLocation() {
       mLocationClient = new AMapLocationClient(context);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mLocationOption.setOnceLocationLatest(true);
        mLocationOption.setWifiActiveScan(true);
        mLocationOption.setMockEnable(true);
        mLocationOption.setHttpTimeOut(10000);
        mLocationOption.setLocationCacheEnable(true);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation!=null){
                    if(aMapLocation.getErrorCode()==0){
                        String city = CityFomat.extractLocation(aMapLocation.getCity(),aMapLocation.getDistrict());
                        requestData(city);
                        Logger.d(aMapLocation.getCity() +" "+aMapLocation.getDistrict());
                        //requestData(aMapLocation.getCity());
                    }else{
                        requestData("郑州");
                        view.showError();
                    }
                }
            }
        });
        mLocationClient.startLocation();

    }

    @Override
    public void requestData(String city) {
        view.showLoading();
        Logger.d("6666666666666");
        DateFomatter dateFomatter = new DateFomatter();
        HttpUtils.getInstance()
                .create(ApiService.class,Api.Weather)
                .getWeatherData(city,dateFomatter.WeatherDateFormat())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<WeatherBean>() {
                    @Override
                    public void accept(WeatherBean weatherBean) throws Exception {
                        view.showWeather(weatherBean);
                        view.stopLoading();
                        Logger.d(weatherBean.getShowapi_res_code()+"");
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        view.showError();
                        view.stopLoading();
                    }
                });
    }

    @Override
    public void start() {

    }
}
