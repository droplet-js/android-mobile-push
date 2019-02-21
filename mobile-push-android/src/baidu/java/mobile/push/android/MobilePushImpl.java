package mobile.push.android;

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
                            callback.onFailure(errorCode, "errorCode: " + errorCode);
                        }
                    }
                    MobilePushImpl.this.context.unregisterReceiver(this);
                }
            }
        }, intentFilter);
        PushManager.startWork(context, PushConstants.LOGIN_TYPE_API_KEY, appKey);
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
