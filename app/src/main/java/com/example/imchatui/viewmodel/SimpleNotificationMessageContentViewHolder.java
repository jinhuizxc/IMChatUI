package com.example.imchatui.viewmodel;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.imchatui.R;
import com.example.imchatui.kit.annotation.LayoutRes;
import com.example.imchatui.kit.annotation.MessageContentType;
import com.example.imchatui.model.UiMessage;
import com.example.imchatui.viewmodel.MessageContentViewHolder;

import butterknife.BindView;
import cn.wildfirechat.message.notification.AddGroupMemberNotificationContent;
import cn.wildfirechat.message.notification.ChangeGroupNameNotificationContent;
import cn.wildfirechat.message.notification.ChangeGroupPortraitNotificationContent;
import cn.wildfirechat.message.notification.CreateGroupNotificationContent;
import cn.wildfirechat.message.notification.DismissGroupNotificationContent;
import cn.wildfirechat.message.notification.KickoffGroupMemberNotificationContent;
import cn.wildfirechat.message.notification.ModifyGroupAliasNotificationContent;
import cn.wildfirechat.message.notification.NotificationMessageContent;
import cn.wildfirechat.message.notification.QuitGroupNotificationContent;
import cn.wildfirechat.message.notification.RecallMessageContent;
import cn.wildfirechat.message.notification.TipNotificationContent;
import cn.wildfirechat.message.notification.TransferGroupOwnerNotificationContent;

@MessageContentType(value = {
        AddGroupMemberNotificationContent.class,
        ChangeGroupNameNotificationContent.class,
        ChangeGroupPortraitNotificationContent.class,
        CreateGroupNotificationContent.class,
        DismissGroupNotificationContent.class,
        DismissGroupNotificationContent.class,
        KickoffGroupMemberNotificationContent.class,
        ModifyGroupAliasNotificationContent.class,
        QuitGroupNotificationContent.class,
        TransferGroupOwnerNotificationContent.class,
        TipNotificationContent.class,
        RecallMessageContent.class,
        // TODO add more

})
@LayoutRes(resId = R.layout.conversation_item_notification)
/**
 * 小灰条消息, 居中显示，且不显示发送者，用于简单通知，如果需要扩展成复杂通知，可以参考 {@link ExampleRichNotificationMessageContentViewHolder}
 *
 */
public class SimpleNotificationMessageContentViewHolder extends MessageContentViewHolder {

    @BindView(R.id.notificationTextView)
    TextView notificationTextView;

    public SimpleNotificationMessageContentViewHolder(FragmentActivity activity, RecyclerView.Adapter adapter, View itemView) {
        super(activity, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message, int position) {
        super.onBind(message, position);
        onBind(message);
    }

    @Override
    public boolean contextMenuItemFilter(UiMessage uiMessage, String tag) {
        return true;
    }

    protected void onBind(UiMessage message) {
        String notification;
        try {
            notification = ((NotificationMessageContent) message.message.content).formatNotification();
        } catch (Exception e) {
            e.printStackTrace();
            notification = "message is invalid";
        }
        notificationTextView.setText(notification);
    }
}
