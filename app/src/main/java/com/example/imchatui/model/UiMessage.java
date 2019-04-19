package com.example.imchatui.model;

import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Objects;

import cn.wildfirechat.message.Message;

public class UiMessage {

    public boolean isPlaying;
    public boolean isDownloading;
    public boolean isFocus;
    /**
     * media类型消息，上传或下载的进度
     */
    public int progress;
    public Message message;

    public UiMessage(Message message) {
        this.message = message;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UiMessage uiMessage = (UiMessage) o;
        return Objects.equals(message, uiMessage.message);
    }

    @Override
    public int hashCode() {

        return Objects.hash(message);
    }

}
