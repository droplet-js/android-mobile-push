package mobile.push.android;

import android.app.Application;
import android.content.Context;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;

final class MobilePushImpl extends MobilePush {

    public MobilePushImpl(Context context) {
        super(context);
    }

    @Override
    public void init(Application app) {
        PushServiceFactory.init(app);
    }

    @Override
    public void startWork(String appKey, String appSecret, boolean enableDebug, Callback callback) {
        PushServiceFactory.getCloudPushService()
                .register(context, appKey, appSecret, new AdaptAliyunCallback(callback));
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
    public void bindTags(String[] tags) {
        PushServiceFactory.getCloudPushService()
                .bindTag(CloudPushService.DEVICE_TARGET, tags, null, new AdaptAliyunCallback(null));
    }

    @Override
    public void unbindTags(String[] tags) {
        PushServiceFactory.getCloudPushService()
                .unbindTag(CloudPushService.DEVICE_TARGET, tags, null, new AdaptAliyunCallback(null));
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
