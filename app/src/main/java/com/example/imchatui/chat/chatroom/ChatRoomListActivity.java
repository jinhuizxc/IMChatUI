package com.example.imchatui.chat.chatroom;


import com.example.imchatui.R;
import com.example.imchatui.base.BaseActivity;

public class ChatRoomListActivity extends BaseActivity {

    @Override
    protected void afterViews() {
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.containerFrameLayout, new ChatRoomListFragment())
                .commit();
    }

    @Override
    protected int contentLayout() {
        return R.layout.fragment_container_activity;
    }
}
