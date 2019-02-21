package mobile.push.android.baidu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.text.TextUtils;

import com.baidu.android.pushservice.PushConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobile.push.android.MobilePushMSGReceiver;
import mobile.push.android.util.JSONUtil;

public final class BaiduPushMessageReceiver extends PushMessageReceiver {

    @Override
    public void onBind(Context context, int errorCode, String appId, String userId, String channelId, String requestId) {
        super.onBind(context, errorCode, appId, userId, channelId, requestId);
        Map<String, Object> map = new HashMap<>();
        map.put(BaiduPushConstants.BIND_CHANNEL_KEY_RESULT_SUCCESS, errorCode == PushConstants.ERROR_SUCCESS);
        map.put(BaiduPushConstants.BIND_CHANNEL_KEY_RESULT_APPID, appId);
        map.put(BaiduPushConstants.BIND_CHANNEL_KEY_RESULT_USERID, userId);
        map.put(BaiduPushConstants.BIND_CHANNEL_KEY_RESULT_CHANNELID, channelId);
        Intent intent = new Intent();
        intent.setAction(BaiduPushConstants.ACTION_BIND_CHANNEL);
        intent.putExtra(BaiduPushConstants.KEY_EXTRA_MAP, JSONUtil.toJsonString(map));
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }

    @Override
    public void onMessage(Context context, String message, String customContentString) {
        super.onMessage(context, message, customContentString);
        if (!TextUtils.isEmpty(customContentString)) {
            MobilePushMSGReceiver.sendMessage(context, customContentString);
        }
    }

    @Override
    public void onNotificationArrived(Context context, String title, String description, String customContentString) {
        super.onNotificationArrived(context, title, description, customContentString);
        if (!TextUtils.isEmpty(customContentString)) {
            MobilePushMSGReceiver.sendNotification(context, customContentString);
        }
    }

    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {
        super.onNotificationClicked(context, title, description, customContentString);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(context.getPackageName());
        List<ResolveInfo> infos = context.getPackageManager().queryIntentActivities(intent, 0);
        if (infos != null && !infos.isEmpty()) {
            ResolveInfo info = infos.get(0);
            intent.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
            intent.putExtra(BaiduPushConstants.KEY_EXTRA_MAP, customContentString);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
