package io.github.v7lin.mobile.push;

import android.app.Activity;
import android.os.Bundle;

import java.util.Map;

import mobile.push.android.MobilePush;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobilePush.with(this)
                .startWork("xxx", "xxx", true, new MobilePush.Callback() {
                    @Override
                    public void onSuccess(Map<String, Object> resp) {

                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage) {

                    }
                });
    }
}
