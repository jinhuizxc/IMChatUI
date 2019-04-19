package com.example.imchatui.viewmodel;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.imchatui.R;
import com.example.imchatui.adapter.ConversationMessageAdapter;
import com.example.imchatui.kit.annotation.EnableContextMenu;
import com.example.imchatui.kit.annotation.LayoutRes;
import com.example.imchatui.kit.annotation.MessageContentType;
import com.example.imchatui.model.UiMessage;
import com.example.imchatui.preview.MMPreviewActivity;
import com.example.imchatui.widget.BubbleImageView;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.OnClick;
import cn.wildfirechat.message.VideoMessageContent;

@MessageContentType(VideoMessageContent.class)
@LayoutRes(resId = R.layout.conversation_item_video_send)
@EnableContextMenu
public class VideoMessageContentViewHolder extends MediaMessageContentViewHolder {

    @BindView(R.id.imageView)
    BubbleImageView imageView;
    @BindView(R.id.playImageView)
    ImageView playImageView;

    public VideoMessageContentViewHolder(FragmentActivity context, RecyclerView.Adapter adapter, View itemView) {
        super(context, adapter, itemView);
    }

    @Override
    public void onBind(UiMessage message) {
        VideoMessageContent fileMessage = (VideoMessageContent) message.message.content;
        if (fileMessage.getThumbnail() != null && fileMessage.getThumbnail().getWidth() > 0) {
            imageView.setImageBitmap(fileMessage.getThumbnail());
            playImageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setImageResource(R.mipmap.img_video_default);
            playImageView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.videoContentLayout)
    void play() {
        List<UiMessage> messages = ((ConversationMessageAdapter) adapter).getMessages();
        List<UiMessage> mmMessages = new ArrayList<>();
        for (UiMessage msg : messages) {
            if (msg.message.content.getType() == cn.wildfirechat.message.core.MessageContentType.ContentType_Image
                    || msg.message.content.getType() == cn.wildfirechat.message.core.MessageContentType.ContentType_Video) {
                mmMessages.add(msg);
            }
        }
        if (mmMessages.isEmpty()) {
            return;
        }

        int current = 0;
        for (int i = 0; i < mmMessages.size(); i++) {
            if (message.message.messageId == mmMessages.get(i).message.messageId) {
                current = i;
                break;
            }
        }
        // TODO 视频播放部分
        MMPreviewActivity.startActivity(context, mmMessages, current);
    }

    @Override
    public boolean contextMenuItemFilter(UiMessage uiMessage, String tag) {
        if (MessageContextMenuItemTags.TAG_FORWARD.equals(tag)) {
            return true;
        } else {
            return super.contextMenuItemFilter(uiMessage, tag);
        }
    }
}
