package oem.push.android;

import android.content.Context;

public abstract class MobilePush {

    protected final Context context;

    public MobilePush(Context context) {
        this.context = context.getApplicationContext() != null ? context.getApplicationContext() : context;
    }

    private static volatile MobilePush sInstance;

    public static synchronized MobilePush with(Context context) {
        if (sInstance == null) {
            sInstance = new MobilePushImpl(context);
        }
        return sInstance;
    }
}
