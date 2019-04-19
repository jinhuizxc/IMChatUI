package com.example.imchatui.viewmodel;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.imchatui.R;
import com.example.imchatui.kit.annotation.MessageContextMenuItem;
import com.example.imchatui.model.UiMessage;


import butterknife.BindView;

import cn.wildfirechat.message.Message;
import cn.wildfirechat.message.MessageContent;
import cn.wildfirechat.message.core.MessageDirection;
import cn.wildfirechat.message.core.MessageStatus;
import cn.wildfirechat.message.notification.NotificationMessageContent;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.model.UserInfo;
import cn.wildfirechat.remote.ChatManager;

/**
 * 普通消息
 */
public abstract class NormalMessageContentViewHolder extends MessageContentViewHolder {

    @BindView(R.id.portraitImageView)
    ImageView portraitImageView;
    @BindView(R.id.errorLinearLayout)
    LinearLayout errorLinearLayout;
    @BindView(R.id.nameTextView)
    TextView nameTextView;

    public NormalMessageContentViewHolder(FragmentActivity activity, RecyclerView.Adapter adapter, View itemView) {
        super(activity, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message, int position) {
        super.onBind(message, position);
        this.message = message;
        this.position = position;

        setSenderAvatar(message.message);
        setSenderName(message.message);
        setSendStatus(message.message);
        try {
            onBind(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (message.isFocus) {
            highlightItem(itemView, message);
        }
    }

    protected abstract void onBind(UiMessage message);

    /**
     * when animation finish, do not forget to set {@link UiMessage#isFocus} to {@code true}
     *
     * @param itemView the item view
     * @param message  the message to highlight
     */
    protected void highlightItem(View itemView, UiMessage message) {
        Animation animation = new AlphaAnimation((float) 0.4, (float) 0.2);
        itemView.setBackgroundColor(itemView.getResources().getColor(R.color.colorPrimary));
        animation.setRepeatCount(2);
        animation.setDuration(500);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                itemView.setBackground(null);
                message.isFocus = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        itemView.startAnimation(animation);
    }

    // TODO 也用注解来做？
    public boolean checkable(UiMessage message) {
        return true;
    }

    // TODO
//    @Nullable
//    @OnClick(R.id.errorLinearLayout)
//    public void onRetryClick(View itemView) {
//        new MaterialDialog.Builder(context)
//                .content("重新发送?")
//                .negativeText("取消")
//                .positiveText("重发")
//                .onPositive((dialog, which) -> conversationViewModel.resendMessage(message.message))
//                .build()
//                .show();
//    }


    @MessageContextMenuItem(tag = MessageContextMenuItemTags.TAG_RECALL, title = "撤回", priority = 10)
    public void recall(View itemView, UiMessage message) {
        // TODO
//        conversationViewModel.recallMessage(message.message);
    }

    @MessageContextMenuItem(tag = MessageContextMenuItemTags.TAG_DELETE, title = "删除", confirm = true, confirmPrompt = "确认删除此消息", priority = 11)
    public void removeMessage(View itemView, UiMessage message) {
        // TODO
//        conversationViewModel.deleteMessage(message.message);
    }

    @MessageContextMenuItem(tag = MessageContextMenuItemTags.TAG_FORWARD, title = "转发", priority = 11)
    public void forwardMessage(View itemView, UiMessage message) {
        // TODO
//        Intent intent = new Intent(context, ForwardActivity.class);
//        intent.putExtra("message", message.message);
//        context.startActivity(intent);
    }

    @MessageContextMenuItem(tag = MessageContextMenuItemTags.TAG_CHANEL_PRIVATE_CHAT, title = "私聊", priority = 12)
    public void startChanelPrivateChat(View itemView, UiMessage message) {
        // TODO
//        Intent intent = ConversationActivity.buildConversationIntent(context, Conversation.ConversationType.Channel, message.message.conversation.target, message.message.conversation.line, message.message.sender);
//        context.startActivity(intent);
    }

    @Override
    public boolean contextMenuItemFilter(UiMessage uiMessage, String tag) {
        Message message = uiMessage.message;
        if (MessageContextMenuItemTags.TAG_RECALL.equals(tag)) {
            long delta = ChatManager.Instance().getServerDeltaTime();
            long now = System.currentTimeMillis();
            if (message.direction == MessageDirection.Send
                    && TextUtils.equals(message.sender, ChatManager.Instance().getUserId())
                    && now - (message.serverTime - delta) < 60 * 1000) {
                return false;
            } else {
                return true;
            }
        }

        if (uiMessage.message.content instanceof NotificationMessageContent && MessageContextMenuItemTags.TAG_FORWARD.equals(tag)) {
            return true;
        }

        //   TODO 只有channel 主可以发起
//        if (MessageContextMenuItemTags.TAG_CHANEL_PRIVATE_CHAT.equals(tag)) {
//            if (uiMessage.message.conversation.type == Conversation.ConversationType.Channel
//                    && uiMessage.message.direction == MessageDirection.Receive
//                    && conversationViewModel.getChannelPrivateChatUser() == null) {
//                return false;
//            }
//            return true;
//        }
        return false;
    }

    private void setSenderAvatar(Message item) {
        // TODO get user info from viewModel
        UserInfo userInfo = ChatManagerHolder.gChatManager.getUserInfo(item.sender, false);
        if (userInfo != null && !TextUtils.isEmpty(userInfo.portrait) && portraitImageView != null) {
            // TODO
//            GlideApp
//                    .with(context)
//                    .load(userInfo.portrait)
//                    .transforms(new CenterCrop(), new RoundedCorners(10))
//                    .into(portraitImageView);
        }
    }

    private void setSenderName(Message item) {
        if (item.conversation.type == Conversation.ConversationType.Single) {
            nameTextView.setVisibility(View.GONE);
        } else {
//            itemView.findViewById(R.id.tvName).setVisibility(View.VISIBLE);
            // TODO
        }
    }

    protected void setSendStatus(Message item) {
        MessageContent msgContent = item.content;
        MessageStatus sentStatus = item.status;
        if (sentStatus == MessageStatus.Sending) {
            errorLinearLayout.setVisibility(View.GONE);
        } else if (sentStatus == MessageStatus.Send_Failure) {
            errorLinearLayout.setVisibility(View.VISIBLE);
        } else if (sentStatus == MessageStatus.Sent) {
            errorLinearLayout.setVisibility(View.GONE);
        }
    }
}
