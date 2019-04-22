package com.example.imchatui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;

import com.example.imchatui.adapter.ConversationMessageAdapter;
import com.example.imchatui.base.BaseActivity;
import com.example.imchatui.kit.conversation.ConversationViewModel;
import com.example.imchatui.widget.ConversationInputPanel;
import com.example.imchatui.widget.keybord.InputAwareLayout;
import com.example.imchatui.widget.keybord.KeyboardAwareLinearLayout;

import butterknife.BindView;
import cn.wildfirechat.model.Conversation;

/**
 * TODO 冲突解决的方案,
 * 1. 解析包错误问题, 问题解决！
 * 2. 键盘冲突问题;
 *
 * 查看具体的log信息；
 * gradlew processDebugManifest --stacktrace
 *
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
    private ConversationViewModel conversationViewModel;
//    private UserViewModel userViewModel;
//    private ChatRoomViewModel chatRoomViewModel;

    private Handler handler;
    private long initialFocusedMessageId;
    // 用户channel主发起，针对某个用户的会话
    private String channelPrivateChatUser;
    private String conversationTitle = "";
    private SharedPreferences sharedPreferences;

    public static Intent buildConversationIntent(Context context, Conversation.ConversationType type, String target, int line) {
        return buildConversationIntent(context, type, target, line, -1);
    }

    public static Intent buildConversationIntent(Context context, Conversation.ConversationType type, String target, int line, long toFocusMessageId) {
        Conversation conversation = new Conversation(type, target, line);
        return buildConversationIntent(context, conversation, null, toFocusMessageId);
    }

    public static Intent buildConversationIntent(Context context, Conversation conversation, String channelPrivateChatUser, long toFocusMessageId) {
        Intent intent = new Intent(context, ConversationActivity.class);
        intent.putExtra("conversation", conversation);
        intent.putExtra("toFocusMessageId", toFocusMessageId);
        intent.putExtra("channelPrivateChatUser", channelPrivateChatUser);
        return intent;
    }



    @Override
    protected int contentLayout() {
        return R.layout.activity_conversation;
    }

    @Override
    protected void afterViews() {
        super.afterViews();
        initView();
        sharedPreferences = getSharedPreferences("sticker", Context.MODE_PRIVATE);
        Intent intent = getIntent();
        conversation = intent.getParcelableExtra("conversation");
        conversationTitle = intent.getStringExtra("conversationTitle");
        initialFocusedMessageId = intent.getLongExtra("toFocusMessageId", -1);
        // TODO
//        if (conversation == null) {
//            finish();
//        }
        setupConversation(conversation);
//        conversationViewModel.clearUnreadStatus(conversation);
    }

    private void setupConversation(Conversation conversation) {
        // FIXME: 2018/11/24 崩溃，重启之后，conversation是null
//        if (conversationViewModel == null) {
//            conversationViewModel = ViewModelProviders.of(this, new ConversationViewModelFactory(conversation, channelPrivateChatUser)).get(ConversationViewModel.class);
//
//            conversationViewModel.messageLiveData().observeForever(messageLiveDataObserver);
//            conversationViewModel.messageUpdateLiveData().observeForever(messageUpdateLiveDatObserver);
//            conversationViewModel.messageRemovedLiveData().observeForever(messageRemovedLiveDataObserver);
//            conversationViewModel.mediaUpdateLiveData().observeForever(mediaUploadedLiveDataObserver);
//
//            userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
//            userViewModel.userInfoLiveData().observeForever(userInfoUpdateLiveDataObserver);
//        } else {
//            conversationViewModel.setConversation(conversation, channelPrivateChatUser);
//        }

        inputPanel.setupConversation(conversationViewModel, conversation);

//        MutableLiveData<List<UiMessage>> messages;
//        if (initialFocusedMessageId != -1) {
//            shouldContinueLoadNewMessage = true;
//            messages = conversationViewModel.loadAroundMessages(initialFocusedMessageId, MESSAGE_LOAD_AROUND);
//        } else {
//            messages = conversationViewModel.getMessages();
//        }

        // load message
//        swipeRefreshLayout.setRefreshing(true);
//        messages.observe(this, uiMessages -> {
//            swipeRefreshLayout.setRefreshing(false);
//            adapter.setMessages(uiMessages);
//            adapter.notifyDataSetChanged();
//
//            if (adapter.getItemCount() > 1) {
//                int initialMessagePosition;
//                if (initialFocusedMessageId != -1) {
//                    initialMessagePosition = adapter.getMessagePosition(initialFocusedMessageId);
//                    if (initialMessagePosition != -1) {
//                        recyclerView.scrollToPosition(initialMessagePosition);
//                        adapter.highlightFocusMessage(initialMessagePosition);
//                    }
//                } else {
//                    moveToBottom = true;
//                    recyclerView.scrollToPosition(adapter.getItemCount() - 1);
//                }
//            }
//        });
        if (conversation.type == cn.wildfirechat.model.Conversation.ConversationType.ChatRoom) {
            // TODO
//            joinChatRoom();
        }

        setTitle();
    }

    // TODO
    private void setTitle() {
        if (!TextUtils.isEmpty(conversationTitle)) {
            setTitle(conversationTitle);
        }

//        if (conversation.type == Conversation.ConversationType.Single) {
//            UserInfo userInfo = ChatManagerHolder.gChatManager.getUserInfo(conversation.target, false);
//            conversationTitle = userInfo.displayName;
//        } else if (conversation.type == Conversation.ConversationType.Group) {
//            GroupInfo groupInfo = ChatManagerHolder.gChatManager.getGroupInfo(conversation.target, false);
//            if (groupInfo != null) {
//                conversationTitle = groupInfo.name;
//            }
//        } else if (conversation.type == Conversation.ConversationType.Channel) {
//            ChannelViewModel channelViewModel = ViewModelProviders.of(this).get(ChannelViewModel.class);
//            ChannelInfo channelInfo = channelViewModel.getChannelInfo(conversation.target, false);
//            if (channelInfo != null) {
//                conversationTitle = channelInfo.name;
//            }
//
//            if (!TextUtils.isEmpty(channelPrivateChatUser)) {
//                UserInfo channelPrivateChatUserInfo = userViewModel.getUserInfo(channelPrivateChatUser, false);
//                if (channelPrivateChatUserInfo != null) {
//                    conversationTitle += "@" + channelPrivateChatUserInfo.displayName;
//                } else {
//                    conversationTitle += "@<" + channelPrivateChatUser + ">";
//                }
//            }
//        }
        setTitle(conversationTitle);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        conversation = intent.getParcelableExtra("conversation");
        initialFocusedMessageId = intent.getLongExtra("toFocusMessageId", -1);
        channelPrivateChatUser = intent.getStringExtra("channelPrivateChatUser");
//        setupConversation(conversation);
    }


    private void initView() {
        handler = new Handler();
        rootLinearLayout.addOnKeyboardShownListener(this);

        swipeRefreshLayout.setOnRefreshListener(() -> loadMoreOldMessages());

        // message list
        adapter = new ConversationMessageAdapter(this);
        // TODO
//        adapter.setOnPortraitClickListener(this);
//        adapter.setOnPortraitLongClickListener(this);

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
                if (!recyclerView.canScrollVertically(1)) {
                    moveToBottom = true;
                    if (initialFocusedMessageId != -1 && !loadingNewMessage && shouldContinueLoadNewMessage) {
                        int lastVisibleItem = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                        if (lastVisibleItem > adapter.getItemCount() - 3) {
                            // TODO
//                            loadMoreNewMessages();
                        }
                    }
                } else {
                    moveToBottom = false;
                }
            }
        });

        inputPanel.init(this, rootLinearLayout);
        inputPanel.setOnConversationInputPanelStateChangeListener(this);
    }

    /**
     * 加载旧的信息
     */
    private void loadMoreOldMessages() {
//        long fromIndex = Long.MAX_VALUE;
//        if (adapter.getMessages() != null && !adapter.getMessages().isEmpty()) {
//            fromIndex = adapter.getItem(0).message.messageId;
//        }
//        conversationViewModel.loadOldMessages(fromIndex, MESSAGE_LOAD_COUNT_PER_TIME)
//                .observe(this, uiMessages -> {
//                    adapter.addMessagesAtHead(uiMessages);
//
//                    swipeRefreshLayout.setRefreshing(false);
//                });
    }


    @Override
    public void onKeyboardHidden() {
        inputPanel.onKeyboardHidden();
    }

    @Override
    public void onKeyboardShown() {
        inputPanel.onKeyboardShown();
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onInputPanelExpanded() {
        recyclerView.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onInputPanelCollapsed() {
        // do nothing
    }
}
