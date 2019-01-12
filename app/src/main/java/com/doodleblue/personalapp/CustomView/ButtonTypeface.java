package com.doodleblue.personalapp.CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by satish on 1/12/2019.
 */
public class ButtonTypeface extends Button {
    public ButtonTypeface(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ButtonTypeface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonTypeface(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Comfortaa-Bold.ttf");
        setTypeface(tf);
    }
}


