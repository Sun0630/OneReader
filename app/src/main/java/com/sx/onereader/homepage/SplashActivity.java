package com.sx.onereader.homepage;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.sx.onereader.R;

/**
 * @Author sunxin
 * @Date 2017/5/5 10:43
 * @Description 闪屏页面
 */

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                jumpToMain();
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!this.isTaskRoot()) {
            //判断该Activity是不是任务空间的源Activity，“非”也就是说是被系统重新实例化出来
            //如果你就放在launcher Activity中话，这里可以直接return了
            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;//finish()之后该活动会继续执行后面的代码，你可以logCat验证，加return避免可能的exception
            }
        }

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        Message msg = Message.obtain();
        msg.what = 1;
        mHandler.sendMessageDelayed(msg,2000);

    }

    private void jumpToMain() {
        Intent intent = new Intent();

        intent.setClass(SplashActivity.this, MainActivity.class);

        startActivity(intent);
        SplashActivity.this.finish();
    }
}
