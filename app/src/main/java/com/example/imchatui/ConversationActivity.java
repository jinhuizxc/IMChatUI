package com.example.imchatui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;

import com.example.imchatui.adapter.ConversationMessageAdapter;
import com.example.imchatui.base.BaseActivity;
import com.example.imchatui.model.Conversation;
import com.example.imchatui.widget.ConversationInputPanel;
import com.example.imchatui.widget.InputAwareLayout;
import com.example.imchatui.widget.KeyboardAwareLinearLayout;

import butterknife.BindView;

/**
 * TODO 冲突解决的方案,
 * 1. 解析包错误问题, 问题解决！
 * 2. 键盘冲突问题;
 */
public class ConversationActivity extends BaseActivity implements
        KeyboardAwareLinearLayout.OnKeyboardShownListener,
        KeyboardAwareLinearLayout.OnKeyboardHiddenListener, ConversationInputPanel.OnConversationInputPanelStateChangeListener {

    public static final int REQUEST_PICK_MENTION_CONTACT = 100;

    private Conversation conversation;
    private boolean loadingNewMessage;
    private boolean shouldContinueLoadNewMessage = false;

    private static final int MESSAGE_LOAD_COUNT_PER_TIME = 20;
    private static final int MESSAGE_LOAD_AROUND = 10;

    @BindView(R.id.rootLinearLayout)
    InputAwareLayout rootLinearLayout;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.msgRecyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.inputPanelFrameLayout)
    ConversationInputPanel inputPanel;

    private ConversationMessageAdapter adapter;
    private boolean moveToBottom = true;
//    private ConversationViewModel conversationViewModel;
//    private UserViewModel userViewModel;
//    private ChatRoomViewModel chatRoomViewModel;

    private Handler handler;
    private long initialFocusedMessageId;
    // 用户channel主发起，针对某个用户的会话
    private String channelPrivateChatUser;
    private String conversationTitle = "";
    private SharedPreferences sharedPreferences;

    @Override
    protected int contentLayout() {
        return R.layout.activity_conversation;
    }

    @Override
    protected void afterViews() {
        super.afterViews();
        initView();
//        sharedPreferences = getSharedPreferences("sticker", Context.MODE_PRIVATE);
//        Intent intent = getIntent();
//        conversation = intent.getParcelableExtra("conversation");
//        conversationTitle = intent.getStringExtra("conversationTitle");
//        initialFocusedMessageId = intent.getLongExtra("toFocusMessageId", -1);
//        if (conversation == null) {
//            finish();
//        }
//        setupConversation(conversation);
//        conversationViewModel.clearUnreadStatus(conversation);
    }

    private void initView() {
        handler = new Handler();
        rootLinearLayout.addOnKeyboardShownListener(this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 向上滑动，不在底部，收到消息时，不滑动到底部, 发送消息时，可以强制置为true
                if (newState != RecyclerView.SCROLL_STATE_IDLE) {
                    return;
                }
//                if (!recyclerView.canScrollVertically(1)) {
//                    moveToBottom = true;
//                    if (initialFocusedMessageId != -1 && !loadingNewMessage && shouldContinueLoadNewMessage) {
//                        int lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
//                        if (lastVisibleItem > adapter.getItemCount() - 3) {
//                            loadMoreNewMessages();
//                        }
//                    }
//                } else {
//                    moveToBottom = false;
//                }
            }
        });

        inputPanel.init(this, rootLinearLayout);
        inputPanel.setOnConversationInputPanelStateChangeListener(this);
    }


    @Override
    public void onKeyboardHidden() {
        inputPanel.onKeyboardHidden();
    }

    @Override
    public void onKeyboardShown() {
        inputPanel.onKeyboardShown();
//        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onInputPanelExpanded() {
//        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onInputPanelCollapsed() {
        // do nothing
    }
}
