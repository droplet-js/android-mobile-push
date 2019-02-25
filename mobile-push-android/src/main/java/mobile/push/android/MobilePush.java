package mobile.push.android;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.util.List;
import java.util.Map;

import mobile.push.android.util.JSONUtils;

public abstract class MobilePush {

    protected final Context context;

    public MobilePush(Context context) {
        this.context = context.getApplicationContext() != null ? context.getApplicationContext() : context;
    }

    public abstract void init(Application app);

    public abstract void startWork(String appKey, String appSecret, boolean enableDebug, Callback callback);

    public abstract void bindAccount(String account);

    public abstract void unbindAccount(String account);

    public final Map<String, Object> obtainPushMessage(Intent intent) {
        Bundle extras = intent.getExtras();
        if (extras != null && extras.containsKey(MobilePushConstants.KEY_EXTRA_MAP)) {
            String jsonStr = intent.getStringExtra(MobilePushConstants.KEY_EXTRA_MAP);
            Map<String, Object> map = JSONUtils.toMap(jsonStr);
            return map;
        }
        return null;
    }

    public abstract void bindTags(List<String> tags);

    public abstract void unbindTags(List<String> tags);

    public final void cancelAll() {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

    public interface Callback {
        void onSuccess(String response);

        void onFailure(String errorCode, String errorMessage);
    }

    // ---

    private static volatile MobilePush sInstance;

    public static synchronized MobilePush with(Context context) {
        if (sInstance == null) {
            sInstance = new MobilePushImpl(context);
        }
        return sInstance;
    }
}
