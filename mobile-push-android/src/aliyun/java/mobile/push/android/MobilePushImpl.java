package mobile.push.android;

import android.content.Context;

import java.util.List;

final class MobilePushImpl extends MobilePush {

    public MobilePushImpl(Context context) {
        super(context);
    }

    @Override
    public void startWork(String appKey, String appSecret, boolean enableDebug, Callback callback) {

    }

    @Override
    public void bindTags(List<String> tags) {

    }

    @Override
    public void unbindTags(List<String> tags) {

    }
}
