package com.example.imchatui.chat.chatroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.imchatui.ConversationActivity;
import com.example.imchatui.R;

import butterknife.ButterKnife;
import butterknife.OnClick;

import cn.wildfirechat.model.Conversation;

/**
 * 聊天室界面
 */
public class ChatRoomListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chatroom_list_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.chatRoomTextView_0, R.id.chatRoomTextView_1, R.id.chatRoomTextView_2})
    void joinChatRoom(View view) {
        String roomId = "chatroom1";
        String title = "聊天室1";
        if (view.getId() == R.id.chatRoomTextView_1) {
            roomId = "chatroom2";
            title = "聊天室2";
        } else if (view.getId() == R.id.chatRoomTextView_2) {
            roomId = "chatroom3";
            title = "聊天室3";
        }

        //todo 这里应该是先进入到ConversationActivity界面，然后在界面内joinchatroom？
        Intent intent = new Intent(getActivity(), ConversationActivity.class);
        Conversation conversation = new Conversation(Conversation.ConversationType.ChatRoom, roomId);
        intent.putExtra("conversation", conversation);
        intent.putExtra("conversationTitle", title);
        startActivity(intent);
    }
}
