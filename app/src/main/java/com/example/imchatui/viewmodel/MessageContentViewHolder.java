package com.example.imchatui.viewmodel;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.imchatui.R;
import com.example.imchatui.adapter.ConversationMessageAdapter;
import com.example.imchatui.kit.conversation.ConversationViewModel;
import com.example.imchatui.model.UiMessage;
import com.example.imchatui.utils.TimeUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.wildfirechat.message.Message;

public abstract class MessageContentViewHolder extends RecyclerView.ViewHolder {

    protected FragmentActivity context;
    protected View itemView;
    protected UiMessage message;
    protected int position;
    protected RecyclerView.Adapter adapter;
    protected ConversationViewModel conversationViewModel;

    @BindView(R.id.timeTextView)
    TextView timeTextView;


    public MessageContentViewHolder(FragmentActivity activity, RecyclerView.Adapter adapter, View itemView) {
        super(itemView);
        this.context = activity;
        this.itemView = itemView;
        this.adapter = adapter;
        conversationViewModel = ViewModelProviders.of(context).get(ConversationViewModel.class);
        ButterKnife.bind(this, itemView);
    }

    public void onBind(UiMessage message, int position) {
        setMessageTime(message.message, position);
    }

    /**
     * @param uiMessage
     * @param
     * @return 返回true，将从context menu中排除
     */

    public abstract boolean contextMenuItemFilter(UiMessage uiMessage, String itemTitle);

    /**
     * 设置消息时间
     * @param item
     * @param position
     */
    protected void setMessageTime(Message item, int position) {
        long msgTime = item.serverTime;
        if (position > 0) {
            Message preMsg = ((ConversationMessageAdapter) adapter).getItem(position - 1).message;
            long preMsgTime = preMsg.serverTime;
            if (msgTime - preMsgTime > (5 * 60 * 1000)) {
                timeTextView.setVisibility(View.VISIBLE);
                timeTextView.setText(TimeUtils.getMsgFormatTime(msgTime));
            } else {
                timeTextView.setVisibility(View.GONE);
            }
        } else {
            timeTextView.setVisibility(View.VISIBLE);
            timeTextView.setText(TimeUtils.getMsgFormatTime(msgTime));
        }
    }

}
