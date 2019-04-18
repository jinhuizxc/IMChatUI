package com.example.imchatui.ext.core;

import com.example.imchatui.ext.ExampleAudioInputExt;
import com.example.imchatui.ext.FileExt;
import com.example.imchatui.ext.ImageExt;
import com.example.imchatui.ext.LocationExt;
import com.example.imchatui.ext.ShootExt;
import com.example.imchatui.ext.VoipExt;
import com.example.imchatui.model.Conversation;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;


public class ConversationExtManager {
    private static ConversationExtManager instance;
    private List<ConversationExt> conversationExts;

    private ConversationExtManager() {
        conversationExts = new ArrayList<>();
        init();
    }

    public static synchronized ConversationExtManager getInstance() {
        if (instance == null) {
            instance = new ConversationExtManager();
        }
        return instance;
    }

    private void init() {
        registerExt(ImageExt.class);
        registerExt(VoipExt.class);
        registerExt(ShootExt.class);
        registerExt(FileExt.class);
        registerExt(LocationExt.class);
        registerExt(ExampleAudioInputExt.class);
    }

    public void registerExt(Class<? extends ConversationExt> clazz) {
        Constructor constructor;
        try {
            constructor = clazz.getConstructor();
            ConversationExt ext = (ConversationExt) constructor.newInstance();
            conversationExts.add(ext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unregisterExt(Class<? extends ConversationExt> clazz) {
        // TODO
    }

    public List<ConversationExt> getConversationExts(Conversation conversation) {
        List<ConversationExt> currentExts = new ArrayList<>();
        for (ConversationExt ext : this.conversationExts) {
            if (!ext.filter(conversation)) {
                currentExts.add(ext);
            }
        }
        return currentExts;
    }
}
