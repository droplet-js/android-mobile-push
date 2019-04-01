package mobile.push.android;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

import java.util.List;

final class MobilePushImpl extends MobilePush {

    public MobilePushImpl(Context context) {
        super(context);
    }

    @Override
    public MobilePush init(Application app) {
        PushServiceFactory.init(app);
        return this;
    }

    @Override
    public void startWork(String appKey, String appSecret, boolean enableDebug, Callback callback) {
        PushServiceFactory.getCloudPushService()
                .register(context, appKey, appSecret, new AdaptAliyunCallback(callback));
        if (enableDebug) {
            PushServiceFactory.getCloudPushService()
                    .setLogLevel(CloudPushService.LOG_DEBUG);
        }
    }

    @Override
    public void bindAccount(String account) {
        PushServiceFactory.getCloudPushService()
                .bindAccount(account, new AdaptAliyunCallback(null));
    }

    @Override
    public void unbindAccount(String account) {
        PushServiceFactory.getCloudPushService()
                .unbindAccount(new AdaptAliyunCallback(null));
    }

    @Override
    public void bindTags(List<String> tags) {
        PushServiceFactory.getCloudPushService()
                .bindTag(CloudPushService.DEVICE_TARGET, toArray(tags), null, new AdaptAliyunCallback(null));
    }

    @Override
    public void unbindTags(List<String> tags) {
        PushServiceFactory.getCloudPushService()
                .unbindTag(CloudPushService.DEVICE_TARGET, toArray(tags), null, new AdaptAliyunCallback(null));
    }

    private String[] toArray(List<String> tags) {
        if (tags != null && !tags.isEmpty()) {
            String[] newTags = new String[tags.size()];
            tags.toArray(newTags);
            return newTags;
        }
        return new String[0];
    }

    private final class AdaptAliyunCallback implements CommonCallback {

        private Callback callback;

        public AdaptAliyunCallback(Callback callback) {
            this.callback = callback;
        }

        @Override
        public void onSuccess(String response) {
            if (callback != null) {
                callback.onSuccess(response);
            }
        }

        @Override
        public void onFailed(String errorCode, String errorMessage) {
            if (callback != null) {
                callback.onFailure(errorCode, errorMessage);
            }
        }
    }
}
