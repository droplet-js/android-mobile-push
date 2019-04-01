package io.github.v7lin.mobilepush;

import android.app.Application;
import android.util.Log;

import mobile.push.android.MobilePush;

public class AppConfig extends Application {

    private static final String ALIYUN_APPKEY = "25904462";
    private static final String ALIYUN_APPSECRET = "eea48bc18188a59a3d2fcd85375b293a";

    @Override
    public void onCreate() {
        super.onCreate();
        MobilePush.with(this)
                .init(this)
                .startWork(ALIYUN_APPKEY, ALIYUN_APPSECRET, true, new MobilePush.Callback() {
                    @Override
                    public void onSuccess(String response) {
                        Log.e("TAG", "response: " + response);
                    }

                    @Override
                    public void onFailure(String errorCode, String errorMessage) {
                        Log.e("TAG", "errorCode: " + errorCode + "; errorMessage: " + errorMessage);
                    }
                });
    }
}
