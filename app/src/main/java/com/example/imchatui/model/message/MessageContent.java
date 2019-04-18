package com.example.imchatui.model.message;

import android.os.Parcelable;

import com.example.imchatui.annotation.ContentTag;
import com.example.imchatui.annotation.PersistFlag;

import java.util.List;


/**
 * Created by heavyrain lee on 2017/12/6.
 */

public abstract class MessageContent implements Parcelable {

    public abstract MessagePayload encode();

    public abstract void decode(MessagePayload payload);

    public abstract String digest();

    //0 普通消息, 1 部分提醒, 2 提醒全部
    public int mentionedType;

    //提醒对象，mentionedType 1时有效
    public List<String> mentionedTargets;

    public int getType() {
        ContentTag tag = getClass().getAnnotation(ContentTag.class);
        if (tag != null) {
            return tag.type();
        }
        return -1;
    }

    public PersistFlag getPersistFlag() {
        ContentTag tag = getClass().getAnnotation(ContentTag.class);
        if (tag != null) {
            return tag.flag();
        }
        return PersistFlag.No_Persist;
    }


}
