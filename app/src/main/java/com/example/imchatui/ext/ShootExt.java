package com.example.imchatui.ext;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.imchatui.R;
import com.example.imchatui.annotation.ExtContextMenuItem;
import com.example.imchatui.ext.core.ConversationExt;
import com.example.imchatui.model.Conversation;
import com.example.imchatui.takephoto.TakePhotoActivity;
import com.example.imchatui.utils.ImageUtils;

import java.io.File;


import cn.wildfirechat.message.TypingMessageContent;

import static android.app.Activity.RESULT_OK;

public class ShootExt extends ConversationExt {

    /**
     * @param containerView 扩展view的container
     * @param conversation
     */
    @ExtContextMenuItem(title = "拍摄")
    public void shoot(View containerView, Conversation conversation) {
        Intent intent = new Intent(context, TakePhotoActivity.class);
        startActivityForResult(intent, 100);
        TypingMessageContent content = new TypingMessageContent(TypingMessageContent.TYPING_CAMERA);
//        conversationViewModel.sendMessage(content);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            String path = data.getStringExtra("path");
            if (data.getBooleanExtra("take_photo", true)) {
                //照片
//                conversationViewModel.sendImgMsg(ImageUtils.genThumbImgFile(path), new File(path));
            } else {
                //小视频
//                conversationViewModel.sendVideoMsg(new File(path));
            }
        }
    }

    @Override
    public int priority() {
        return 100;
    }

    @Override
    public int iconResId() {
        return R.mipmap.ic_func_pic;
    }

    @Override
    public String title(Context context) {
        return "拍摄";
    }
}
