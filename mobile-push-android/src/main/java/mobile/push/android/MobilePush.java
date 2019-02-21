package mobile.push.android;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;

import java.util.List;
import java.util.Map;

public abstract class MobilePush {

    protected final Context context;

    public MobilePush(Context context) {
        this.context = context.getApplicationContext() != null ? context.getApplicationContext() : context;
    }

    public abstract void startWork(String appKey, String appSecret, boolean enableDebug, Callback callback);

    public abstract Map<String, Object> obtainPushMessage(Intent intent);

    public abstract void bindTags(List<String> tags);

    public abstract void unbindTags(List<String> tags);

    public final void cancelAll() {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancelAll();
    }

    public interface Callback {
        public void onSuccess(Map<String, Object> resp);
        public void onFailure(Map<String, Object> errorRes);
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
