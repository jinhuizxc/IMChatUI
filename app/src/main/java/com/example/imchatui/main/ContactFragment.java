//package com.example.imchatui.main;
//
//import android.arch.lifecycle.Observer;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import java.util.List;
//
//
//import cn.wildfirechat.model.UserInfo;
//
//public class ContactFragment extends BaseContactFragment implements QuickIndexBar.OnLetterUpdateListener {
//
//    private UserViewModel userViewModel;
//
//    private Observer<Integer> friendRequestUpdateLiveDataObserver = count -> {
//        FriendRequestValue requestValue = new FriendRequestValue(count == null ? 0 : count);
//        contactAdapter.updateHeader(0, requestValue);
//    };
//
//    private Observer<Object> contactListUpdateLiveDataObserver = o -> {
//        List<UserInfo> userInfos = contactViewModel.getContacts(false);
//        contactAdapter.setContacts(userInfoToUIUserInfo(userInfos));
//        contactAdapter.notifyDataSetChanged();
//
//        for (UserInfo info : userInfos) {
//            if (info.name == null || info.displayName == null) {
//                userViewModel.getUserInfo(info.uid, true);
//            }
//        }
//    };
//
//    private Observer<List<UserInfo>> userInfoLiveDataObserver = userInfos -> {
//        contactAdapter.updateContacts(userInfoToUIUserInfo(userInfos));
//    };
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = super.onCreateView(inflater, container, savedInstanceState);
//
//        contactViewModel.contactListUpdatedLiveData().observeForever(contactListUpdateLiveDataObserver);
//        contactViewModel.friendRequestUpdatedLiveData().observeForever(friendRequestUpdateLiveDataObserver);
//
//        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
//        userViewModel.userInfoLiveData().observeForever(userInfoLiveDataObserver);
//        return view;
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        contactViewModel.contactListUpdatedLiveData().removeObserver(contactListUpdateLiveDataObserver);
//        contactViewModel.friendRequestUpdatedLiveData().removeObserver(friendRequestUpdateLiveDataObserver);
//        userViewModel.userInfoLiveData().removeObserver(userInfoLiveDataObserver);
//    }
//
//    @Override
//    public void initHeaderViewHolders() {
//        addHeaderViewHolder(FriendRequestViewHolder.class, new FriendRequestValue(contactViewModel.getUnreadFriendRequestCount()));
//        addHeaderViewHolder(GroupViewHolder.class, new GroupValue());
//        addHeaderViewHolder(ChannelViewHolder.class, new HeaderValue());
//    }
//
//    @Override
//    public void initFooterViewHolders() {
//        addFooterViewHolder(ContactCountViewHolder.class, new ContactCountFooterValue());
//    }
//
//    @Override
//    public void onContactClick(UIUserInfo userInfo) {
//        Intent intent = new Intent(getActivity(), UserInfoActivity.class);
//        intent.putExtra("userInfo", userInfo.getUserInfo());
//        startActivity(intent);
//    }
//
//    @Override
//    public void onHeaderClick(int index) {
//        switch (index) {
//            case 0:
//                ((MainActivity) getActivity()).hideUnreadFriendRequestBadgeView();
//                showFriendRequest();
//                break;
//            case 1:
//                showGroupList();
//                break;
//            case 2:
//                showChannelList();
//                break;
//            default:
//                break;
//        }
//    }
//
//    private void showFriendRequest() {
//        FriendRequestValue value = new FriendRequestValue(0);
//        contactAdapter.updateHeader(0, value);
//
//        contactViewModel.clearUnreadFriendRequestStatus();
//        Intent intent = new Intent(getActivity(), FriendRequestListActivity.class);
//        startActivity(intent);
//    }
//
//    private void showGroupList() {
//        Intent intent = new Intent(getActivity(), GroupListActivity.class);
//        startActivity(intent);
//    }
//
//    private void showChannelList() {
//        Intent intent = new Intent(getActivity(), ChannelListActivity.class);
//        startActivity(intent);
//    }
//}
