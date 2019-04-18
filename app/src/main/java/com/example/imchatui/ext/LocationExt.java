package com.example.imchatui.ext;

import android.content.Context;
import android.content.Intent;
import android.view.View;


import com.example.imchatui.R;
import com.example.imchatui.annotation.ExtContextMenuItem;
import com.example.imchatui.ext.core.ConversationExt;
import com.example.imchatui.location.MyLocationActivity;
import com.example.imchatui.location.data.LocationData;
import com.example.imchatui.model.Conversation;

import cn.wildfirechat.message.TypingMessageContent;

import static android.app.Activity.RESULT_OK;

public class LocationExt extends ConversationExt {

    /**
     * @param containerView 扩展view的container
     * @param conversation
     */
    @ExtContextMenuItem(title = "位置")
    public void pickLocation(View containerView, Conversation conversation) {
        Intent intent = new Intent(context, MyLocationActivity.class);
        startActivityForResult(intent, 100);
        TypingMessageContent content = new TypingMessageContent(TypingMessageContent.TYPING_LOCATION);
//        conversationViewModel.sendMessage(content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            LocationData locationData = (LocationData) data.getSerializableExtra("location");
//            conversationViewModel.sendLocationMessage(locationData);
        }
    }

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public int iconResId() {
        return R.mipmap.ic_func_location;
    }

    @Override
    public String title(Context context) {
        return "位置";
    }
}
