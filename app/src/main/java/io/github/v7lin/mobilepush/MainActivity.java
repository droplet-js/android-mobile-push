package io.github.v7lin.mobilepush;

import android.app.Activity;
import android.os.Bundle;

import mobile.push.android.MobilePush;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobilePush.with(this)
                .startWork("xxx", "xxx", true, new MobilePush.Callback() {
                    @Override
                    public void onSuccess(String response) {

                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {

                    }
                });
    }
}
