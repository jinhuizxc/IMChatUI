package com.example.imchatui.widget.keybord;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.imchatui.widget.keybord.InputAwareLayout;


public class KeyboardHeightFrameLayout extends FrameLayout implements InputAwareLayout.InputView {

    public KeyboardHeightFrameLayout(@NonNull Context context) {
        super(context);
    }

    public KeyboardHeightFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public KeyboardHeightFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public KeyboardHeightFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    public void show(int height, boolean immediate) {
        // TODO
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = height;
        getChildAt(0).setVisibility(VISIBLE);
        setVisibility(VISIBLE);
    }

    @Override
    public void hide(boolean immediate) {
        setVisibility(GONE);
    }

    @Override
    public boolean isShowing() {
        return getVisibility() == VISIBLE;
    }
}
