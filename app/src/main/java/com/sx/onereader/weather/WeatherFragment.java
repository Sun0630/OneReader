package com.sx.onereader.weather;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.orhanobut.logger.Logger;
import com.sx.onereader.R;
import com.sx.onereader.utils.SystemUtils;
import com.sx.onereader.weather.bean.WeatherBean;
import com.sx.onereader.weather.contract.WeatherContract;
import com.zaaach.citypicker.CityPickerActivity;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/3/27.
 */
public class WeatherFragment extends Fragment implements WeatherContract.View {

    WeatherContract.Presenter presenter;
    SwipeRefreshLayout mSwipeRefreshLayout;
    //WeatherPresenter weatherPresenter;
    RelativeLayout mRelativeLayout;
    TextView mTextViewRefreshTime;
    TextView mTextViewAirIndex;
    TextView mTextViewWeather;
    ImageView mImageViewWeather;
    TextView mTextViewTemp;
    TextView mTextViewMaxTemp;
    TextView mTextViewMinTemp;
    Toolbar mToolbar;
    private Context mContext;
    private int mNowWeatherHeight = -1;
    private String mCity;
    private TextView mSelectCity;

    public static WeatherFragment newInstance() {
        return new WeatherFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        initView(view);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadLocation();
            }
        });
        presenter.loadLocation();
        return view;
    }

    @Override
    public void showLoading() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void showError() {
        Snackbar.make(mSwipeRefreshLayout, "天气加载失败", Snackbar.LENGTH_SHORT)
                .setAction("重试", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.loadLocation();
                    }
                })
                .show();
    }

    @Override
    public void stopLoading() {
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void showWeather(WeatherBean weather) {
        Logger.d(weather.getShowapi_res_code() + "");
        mCity = weather.getShowapi_res_body().getCityInfo().getC5();
        mTextViewRefreshTime.setText(weather.getShowapi_res_body().getNow().getTemperature_time() + "更新");
        mTextViewAirIndex.setText("空气指数" + weather.getShowapi_res_body().getNow().getAqi());
        mTextViewMaxTemp.setText("↑ " + weather.getShowapi_res_body().getF1().getDay_air_temperature() + "℃");
        mTextViewMinTemp.setText("↓ " + weather.getShowapi_res_body().getF1().getNight_air_temperature() + "℃");
        mTextViewWeather.setText(weather.getShowapi_res_body().getNow().getWeather());
        mTextViewTemp.setText(weather.getShowapi_res_body().getNow().getTemperature() + "℃");
        Glide.with(mContext).load(weather.getShowapi_res_body().getNow().getWeather_pic()).asBitmap().into(mImageViewWeather);
    }

    @Override
    public void setPresenter(WeatherContract.Presenter presenter) {
        if (presenter != null) {
            this.presenter = presenter;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            mToolbar.setTitle(mCity);
        }
        super.onHiddenChanged(hidden);
    }

    private static final int REQUEST_CODE_PICK_CITY = 0;

    @Override
    public void initView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.weather_swiperefresh);
        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        mRelativeLayout = (RelativeLayout) view.findViewById(R.id.main_now_weather);
        mNowWeatherHeight = SystemUtils.getDisplayHeight(mContext) - SystemUtils.getActionBarSize(mContext) - SystemUtils.getStatusBarHeight(mContext);
        mRelativeLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, mNowWeatherHeight));
        mToolbar = (Toolbar) getActivity().findViewById(R.id.main_toolbar);
        mTextViewRefreshTime = (TextView) view.findViewById(R.id.weather_time);
        mTextViewAirIndex = (TextView) view.findViewById(R.id.weather_air_index);
        mTextViewMaxTemp = (TextView) view.findViewById(R.id.temp_max);
        mTextViewMinTemp = (TextView) view.findViewById(R.id.temp_min);
        mTextViewWeather = (TextView) view.findViewById(R.id.weather_condition_tv);
        mImageViewWeather = (ImageView) view.findViewById(R.id.now_weather_img);
        mTextViewTemp = (TextView) view.findViewById(R.id.weather_tempeture);
        mSelectCity = (TextView) view.findViewById(R.id.weather_select);
        mSelectCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(mContext, CityPickerActivity.class),
                        REQUEST_CODE_PICK_CITY);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_PICK_CITY && resultCode == RESULT_OK) {
            if (data != null) {
                String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
//                Toast.makeText(mContext, "当前选择：" + city, Toast.LENGTH_SHORT).show();
                mToolbar.setTitle(city);
                presenter.requestData(city);
            }
        }
    }
}
