package com.example.imchatui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.wildfirechat.model.Conversation;

public class ChatListActivity extends AppCompatActivity {

    @BindView(R.id.bt_go)
    Button btGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_go)
    public void onViewClicked() {
//        Intent intent = ConversationActivity.buildConversationIntent(this,
//                        Conversation.ConversationType.Channel, channelInfo.channelId, 0);
//        startActivity(intent);
    }
}
