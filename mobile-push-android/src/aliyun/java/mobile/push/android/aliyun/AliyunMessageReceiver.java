package mobile.push.android.aliyun;

import android.content.Context;

import com.alibaba.sdk.android.push.MessageReceiver;
import com.alibaba.sdk.android.push.notification.CPushMessage;

import java.util.Map;

import mobile.push.android.MobilePushMSGReceiver;
import mobile.push.android.util.JSONUtils;

public final class AliyunMessageReceiver extends MessageReceiver {

    @Override
    public void onNotification(Context context, String title, String summary, Map<String, String> extraMap) {
        MobilePushMSGReceiver.sendNotification(context, title, summary, JSONUtils.toJsonString(extraMap));
    }

    @Override
    public void onMessage(Context context, CPushMessage cPushMessage) {
        MobilePushMSGReceiver.sendMessage(context, cPushMessage.getTitle(), cPushMessage.getContent());
    }

    @Override
    public void onNotificationOpened(Context context, String title, String summary, String extraMap) {

    }

    @Override
    protected void onNotificationClickedWithNoAction(Context context, String title, String summary, String extraMap) {

    }

    @Override
    protected void onNotificationReceivedInApp(Context context, String title, String summary, Map<String, String> extraMap, int openType, String openActivity, String openUrl) {

    }

    @Override
    protected void onNotificationRemoved(Context context, String messageId) {

    }
}
