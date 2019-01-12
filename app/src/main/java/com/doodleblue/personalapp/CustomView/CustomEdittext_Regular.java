package com.doodleblue.personalapp.CustomView;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by satish on 1/12/2019.
 */

public class CustomEdittext_Regular extends EditText {
    public CustomEdittext_Regular(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }
    public CustomEdittext_Regular(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public CustomEdittext_Regular(Context context) {
        super(context);
        init();
    }
    private void init() {
        Typeface tf = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Comfortaa-Regular.ttf");
        setTypeface(tf);
    }
}