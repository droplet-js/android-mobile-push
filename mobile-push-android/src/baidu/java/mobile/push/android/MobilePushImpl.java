package mobile.push.android;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;

import java.util.List;
import java.util.Map;

import mobile.push.android.baidu.BaiduPushConstants;
import mobile.push.android.util.JSONUtils;

final class MobilePushImpl extends MobilePush {

    public MobilePushImpl(Context context) {
        super(context);
    }

    @Override
    public MobilePush init(Application app) {
        return this;
    }

    @Override
    public void startWork(String appKey, String appSecret, boolean enableDebug, Callback callback) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BaiduPushConstants.ACTION_BIND_CHANNEL);
        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (TextUtils.equals(BaiduPushConstants.ACTION_BIND_CHANNEL, intent.getAction())) {
                    String jsonStr = intent.getStringExtra(MobilePushConstants.KEY_EXTRA_MAP);
                    Map<String, Object> map = JSONUtils.toMap(jsonStr);
                    final int errorCode = Integer.parseInt(map.get(BaiduPushConstants.BIND_CHANNEL_KEY_RESULT_ERRORCODE).toString());
                    if (errorCode == PushConstants.ERROR_SUCCESS) {
                        map.remove(BaiduPushConstants.BIND_CHANNEL_KEY_RESULT_ERRORCODE);
                        if (callback != null) {
                            callback.onSuccess(JSONUtils.toJsonString(map));
                        }
                    } else {
                        if (callback != null) {
                            callback.onFailure(String.valueOf(errorCode), "错误码: " + errorCode);
                        }
                    }
                    MobilePushImpl.this.context.unregisterReceiver(this);
                }
            }
        }, intentFilter);
        PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY, appKey);
    }

    @Override
    public void bindAccount(String account) {
        // 百度移动推送不支持帐号绑定功能
    }

    @Override
    public void unbindAccount(String account) {
        // 百度移动推送不支持帐号解绑定功能
    }

    @Override
    public void bindTags(List<String> tags) {
        PushManager.setTags(context, tags);
    }

    @Override
    public void unbindTags(List<String> tags) {
        PushManager.delTags(context, tags);
    }
}
