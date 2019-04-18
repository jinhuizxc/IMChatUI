package com.example.imchatui.model.message;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.imchatui.annotation.ContentTag;
import com.example.imchatui.annotation.PersistFlag;

import static com.example.imchatui.annotation.MessageContentType.ContentType_Typing;


/**
 * Created by heavyrain lee on 2017/12/6.
 */

@ContentTag(type = ContentType_Typing, flag = PersistFlag.Transparent)
public class TypingMessageContent extends MessageContent {

    public static final int TYPING_TEXT = 0;
    public static final int TYPING_VOICE = 1;
    public static final int TYPING_CAMERA = 2;
    public static final int TYPING_LOCATION = 3;
    public static final int TYPING_FILE = 4;

    private int type;

    public TypingMessageContent() {
    }

    public TypingMessageContent(int type) {
        this.type = type;
    }

    @Override
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public MessagePayload encode() {
        MessagePayload payload = new MessagePayload();
        payload.content = type + "";
        return payload;
    }


    @Override
    public void decode(MessagePayload payload) {
        type = Integer.parseInt(payload.content);
    }

    @Override
    public String digest() {
        return "";
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.type);
    }

    protected TypingMessageContent(Parcel in) {
        this.type = in.readInt();
    }

    public static final Parcelable.Creator<TypingMessageContent> CREATOR = new Parcelable.Creator<TypingMessageContent>() {
        @Override
        public TypingMessageContent createFromParcel(Parcel source) {
            return new TypingMessageContent(source);
        }

        @Override
        public TypingMessageContent[] newArray(int size) {
            return new TypingMessageContent[size];
        }
    };
}
