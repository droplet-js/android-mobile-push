package mobile.push.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;

import java.util.Map;

import mobile.push.android.util.JSONUtil;

public abstract class MobilePushMSGReceiver extends BroadcastReceiver {

    private static final String ACTION_NOTIFICATION = "android.push.action.NOTIFICATION";
    private static final String ACTION_MESSAGE = "android.push.action.MESSAGE";

    private static final String KEY_EXTRA_MAP = "extraMap";

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
        String jsonStr = intent.getStringExtra(KEY_EXTRA_MAP);
        return JSONUtil.toMap(jsonStr);
    }

    public abstract void onMessage(Map<String, Object> map);

    public abstract void onNotification(Map<String, Object> map);

    // ---

    public static <MR extends MobilePushMSGReceiver> void registerReceiver(Context context, MR receiver) {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ACTION_NOTIFICATION);
        intentFilter.addAction(ACTION_MESSAGE);
        context.registerReceiver(receiver, intentFilter);
    }

    public static <MR extends MobilePushMSGReceiver> void unregisterReceiver(Context context, MR receiver) {
        context.unregisterReceiver(receiver);
    }

    public static void sendNotification(Context context, String jsonStr) {
        Intent intent = new Intent();
        intent.setAction(ACTION_NOTIFICATION);
        intent.putExtra(KEY_EXTRA_MAP, jsonStr);
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }

    public static void sendMessage(Context context, String jsonStr) {
        Intent intent = new Intent();
        intent.setAction(ACTION_MESSAGE);
        intent.putExtra(KEY_EXTRA_MAP, jsonStr);
        intent.setPackage(context.getPackageName());
        context.sendBroadcast(intent);
    }
}
