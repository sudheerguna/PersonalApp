package com.doodleblue.personalapp.CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by satish on 1/12/2019.
 */

public class CustomTextview_Bold extends TextView {
    public CustomTextview_Bold(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextview_Bold(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextview_Bold(Context context) {
        super(context);
        init();
    }

    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Comfortaa-Bold.ttf");
        setTypeface(tf);
    }
}