package com.example.imchatui.main;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.imchatui.R;
import com.example.imchatui.adapter.HomeFragmentPagerAdapter;
import com.example.imchatui.app.Config;
import com.example.imchatui.base.BaseActivity;
import com.example.imchatui.utils.UIUtils;
import com.king.zxing.Intents;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import cn.wildfirechat.model.Conversation;
import cn.wildfirechat.model.UserInfo;
import q.rorbin.badgeview.QBadgeView;


public class MainActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    private List<Fragment> mFragmentList = new ArrayList<>(4);

    @BindView(R.id.bottomNavigationView)
    BottomNavigationView bottomNavigationView;
    @BindView(R.id.vpContent)
    ViewPager mVpContent;

    private QBadgeView unreadMessageUnreadBadgeView;
    private QBadgeView unreadFriendRequestBadgeView;

    private static final int REQUEST_CODE_SCAN_QR_CODE = 100;

    private ConversationListFragment conversationListFragment;
    private ContactFragment_new contactFragment;
    private DiscoveryFragment discoveryFragment;
    private MeFragment meFragment;

    @Override
    protected int contentLayout() {
        return R.layout.main_activity;
    }

    @Override
    protected void afterViews() {
        initView();

        // TODO
//        ConversationListViewModel conversationListViewModel = ViewModelProviders
//                .of(this, new ConversationListViewModelFactory(Arrays.asList(Conversation.ConversationType.Single, Conversation.ConversationType.Group, Conversation.ConversationType.Channel), Arrays.asList(0)))
//                .get(ConversationListViewModel.class);
//        conversationListViewModel.unreadCountLiveData().observe(this, unreadCount -> {
//
//            if (unreadCount != null && unreadCount.unread > 0) {
//                if (unreadMessageUnreadBadgeView == null) {
//                    BottomNavigationMenuView bottomNavigationMenuView = ((BottomNavigationMenuView) bottomNavigationView.getChildAt(0));
//                    View view = bottomNavigationMenuView.getChildAt(0);
//                    unreadMessageUnreadBadgeView = new QBadgeView(MainActivity.this);
//                    unreadMessageUnreadBadgeView.bindTarget(view);
//                }
//                unreadMessageUnreadBadgeView.setBadgeNumber(unreadCount.unread);
//            } else if (unreadMessageUnreadBadgeView != null) {
//                unreadMessageUnreadBadgeView.hide(true);
//            }
//        });
//
//        ContactViewModel contactViewModel = ViewModelProviders.of(this).get(ContactViewModel.class);
//        contactViewModel.friendRequestUpdatedLiveData().observe(this, count -> {
//            if (count == null || count == 0) {
//                if (unreadFriendRequestBadgeView != null) {
//                    unreadFriendRequestBadgeView.hide(true);
//                }
//            } else {
//                if (unreadFriendRequestBadgeView == null) {
//                    BottomNavigationMenuView bottomNavigationMenuView = ((BottomNavigationMenuView) bottomNavigationView.getChildAt(0));
//                    View view = bottomNavigationMenuView.getChildAt(1);
//                    unreadFriendRequestBadgeView = new QBadgeView(MainActivity.this);
//                    unreadFriendRequestBadgeView.bindTarget(view);
//                }
//                unreadFriendRequestBadgeView.setBadgeNumber(count);
//            }
//        });
//        checkDisplayName();
    }

    public void hideUnreadFriendRequestBadgeView() {
        if (unreadFriendRequestBadgeView != null) {
            unreadFriendRequestBadgeView.hide(true);
            unreadFriendRequestBadgeView = null;
        }
    }

    @Override
    protected int menu() {
        return R.menu.main;
    }

    @Override
    protected boolean showHomeMenuItem() {
        return false;
    }

    private void initView() {
        setTitle(UIUtils.getString(R.string.app_name));

        //设置ViewPager的最大缓存页面
        mVpContent.setOffscreenPageLimit(3);

        conversationListFragment = new ConversationListFragment();
        contactFragment = new ContactFragment_new();
        discoveryFragment = new DiscoveryFragment();
        meFragment = new MeFragment();
        mFragmentList.add(conversationListFragment);
        mFragmentList.add(contactFragment);
        mFragmentList.add(discoveryFragment);
        mFragmentList.add(meFragment);
        mVpContent.setAdapter(new HomeFragmentPagerAdapter(getSupportFragmentManager(), mFragmentList));
        mVpContent.setOnPageChangeListener(this);

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.conversation_list:
                    mVpContent.setCurrentItem(0);
                    break;
                case R.id.contact:
                    mVpContent.setCurrentItem(1);
                    break;
                case R.id.discovery:
                    mVpContent.setCurrentItem(2);
                    break;
                case R.id.me:
                    mVpContent.setCurrentItem(3);
                    break;
                default:
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search:
                showSearchPortal();
                break;
            case R.id.chat:
                createConversation();
                break;
            case R.id.add_contact:
                searchUser();
                break;
            case R.id.scan_qrcode:
//                startActivityForResult(new Intent(this, CaptureActivity.class), REQUEST_CODE_SCAN_QR_CODE);
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSearchPortal() {
//        Intent intent = new Intent(this, SearchPortalActivity.class);
//        startActivity(intent);
    }

    private void createConversation() {
//        Intent intent = new Intent(this, CreateConversationActivity.class);
//        startActivity(intent);
    }

    private void searchUser() {
//        Intent intent = new Intent(this, SearchUserActivity.class);
//        startActivity(intent);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                bottomNavigationView.setSelectedItemId(R.id.conversation_list);
                break;
            case 1:
                bottomNavigationView.setSelectedItemId(R.id.contact);
                break;
            case 2:
                bottomNavigationView.setSelectedItemId(R.id.discovery);
                break;
            case 3:
                bottomNavigationView.setSelectedItemId(R.id.me);
                break;
            default:
                break;
        }
//        contactFragment.showQuickIndexBar(position == 1);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state != ViewPager.SCROLL_STATE_IDLE) {
            //滚动过程中隐藏快速导航条
//            contactFragment.showQuickIndexBar(false);
        } else {
//            contactFragment.showQuickIndexBar(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_SCAN_QR_CODE && data != null) {
            String result = data.getStringExtra(Intents.Scan.RESULT);
            onScanPcQrCode(result);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void onScanPcQrCode(String qrcode) {
        String prefix = qrcode.substring(0, qrcode.lastIndexOf('/') + 1);
        String value = qrcode.substring(qrcode.lastIndexOf("/") + 1);
//        Uri uri = Uri.parse(value);
//        uri.getAuthority();
//        uri.getQuery()
        switch (prefix) {
            case Config.QR_CODE_PREFIX_PC_SESSION:
                pcLogin(value);
                break;
            case Config.QR_CODE_PREFIX_USER:
                showUser(value);
                break;
            case Config.QR_CODE_PREFIX_GROUP:
                joinGroup(value);
                break;
            default:
                Toast.makeText(this, "qrcode: " + qrcode, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void pcLogin(String token) {
//        Intent intent = new Intent(this, PCLoginActivity.class);
//        intent.putExtra("token", token);
//        startActivity(intent);
    }

    private void showUser(String uid) {

//        UserViewModel userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
//        UserInfo userInfo = userViewModel.getUserInfo(uid, true);
//        if (userInfo == null) {
//            return;
//        }
//        Intent intent = new Intent(this, UserInfoActivity.class);
//        intent.putExtra("userInfo", userInfo);
//        startActivity(intent);
    }

    private void joinGroup(String groupId) {
//        Intent intent = new Intent(this, GroupInfoActivity.class);
//        intent.putExtra("groupId", groupId);
//        startActivity(intent);
    }

    private void checkDisplayName() {
//     9
    }

    private void updateDisplayName() {
        MaterialDialog dialog = new MaterialDialog.Builder(this)
                .content("修改个人昵称？")
                .positiveText("修改")
                .negativeText("取消")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        Intent intent = new Intent(MainActivity.this, ChangeMyNameActivity.class);
//                        startActivity(intent);
                    }
                }).build();
        dialog.show();
    }
}
