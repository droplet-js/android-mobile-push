package mobile.push.android.baidu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;

import com.baidu.android.pushservice.PushConstants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobile.push.android.MobilePushConstants;
import mobile.push.android.MobilePushMSGReceiver;
import mobile.push.android.util.JSONUtils;

public final class BaiduPushMessageReceiver extends PushMessageReceiver {

    @Override
    public void onBind(Context context, int errorCode, String appId, String userId, String channelId, String requestId) {
        Map<String, Object> map = new HashMap<>();
        map.put(BaiduPushConstants.BIND_CHANNEL_KEY_RESULT_ERRORCODE, errorCode);
        if (errorCode == PushConstants.ERROR_SUCCESS) {
            map.put(BaiduPushConstants.BIND_CHANNEL_KEY_RESULT_APPID, appId);
            map.put(BaiduPushConstants.BIND_CHANNEL_KEY_RESULT_USERID, userId);
            map.put(BaiduPushConstants.BIND_CHANNEL_KEY_RESULT_CHANNELID, channelId);
        }
        Intent intent = new Intent();
        intent.setAction(BaiduPushConstants.ACTION_BIND_CHANNEL);
        intent.putExtra(MobilePushConstants.KEY_EXTRA_MAP, JSONUtils.toJsonString(map));
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }

    @Override
    public void onMessage(Context context, String message, String customContentString) {
        MobilePushMSGReceiver.sendMessage(context, message, customContentString);
    }

    @Override
    public void onNotificationArrived(Context context, String title, String description, String customContentString) {
        MobilePushMSGReceiver.sendNotification(context, title, description, customContentString);
    }

    @Override
    public void onNotificationClicked(Context context, String title, String description, String customContentString) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setPackage(context.getPackageName());
        List<ResolveInfo> infos = context.getPackageManager().queryIntentActivities(intent, 0);
        if (infos != null && !infos.isEmpty()) {
            ResolveInfo info = infos.get(0);
            intent.setComponent(new ComponentName(info.activityInfo.packageName, info.activityInfo.name));
            intent.putExtra(MobilePushConstants.KEY_EXTRA_MAP, customContentString);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}
