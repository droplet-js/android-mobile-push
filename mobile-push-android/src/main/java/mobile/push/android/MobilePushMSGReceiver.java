package mobile.push.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

import mobile.push.android.util.JSONUtils;

public abstract class MobilePushMSGReceiver extends BroadcastReceiver {

    private static final String ACTION_NOTIFICATION = "android.push.action.NOTIFICATION";
    private static final String ACTION_MESSAGE = "android.push.action.MESSAGE";

    @Override
    public final void onReceive(Context context, Intent intent) {
        if (TextUtils.equals(ACTION_NOTIFICATION, intent.getAction())) {
            Map<String, Object> extraMap = extMap(intent);
            onNotification(extraMap);
        } else if (TextUtils.equals(ACTION_MESSAGE, intent.getAction())) {
            Map<String, Object> extraMap = extMap(intent);
            onMessage(extraMap);
        }
    }

    private Map<String, Object> extMap(Intent intent) {
        String jsonStr = intent.getStringExtra(MobilePushConstants.KEY_EXTRA_MAP);
        return JSONUtils.toMap(jsonStr);
    }

    public abstract void onMessage(Map<String, Object> map);

    public abstract void onNotification(Map<String, Object> map);

    // ---

    public static <MR extends MobilePushMSGReceiver> void registerReceiver(Context context,
                                                                           MR receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_NOTIFICATION);
        intentFilter.addAction(ACTION_MESSAGE);
        context.registerReceiver(receiver, intentFilter);
    }

    public static <MR extends MobilePushMSGReceiver> void unregisterReceiver(Context context,
                                                                             MR receiver) {
        context.unregisterReceiver(receiver);
    }

    public static void sendMessage(Context context, String message, String content) {
        Map<String, Object> map = new HashMap<>();
        map.put(MobilePushConstants.KEY_MESSAGE, message);
        map.put(MobilePushConstants.KEY_CONTENT, content);

        Intent intent = new Intent();
        intent.setAction(ACTION_MESSAGE);
        intent.putExtra(MobilePushConstants.KEY_EXTRA_MAP, JSONUtils.toJsonString(map));
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }

    public static void sendNotification(Context context, String title, String description,
                                        String content) {
        Map<String, Object> map = new HashMap<>();
        map.put(MobilePushConstants.KEY_TITLE, title);
        map.put(MobilePushConstants.KEY_DESCRIPTION, description);
        map.put(MobilePushConstants.KEY_CONTENT, content);

        Intent intent = new Intent();
        intent.setAction(ACTION_NOTIFICATION);
        intent.putExtra(MobilePushConstants.KEY_EXTRA_MAP, JSONUtils.toJsonString(map));
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }
}
