package com.ljn.xiaoruireading.view.custom_view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 12390 on 2018/8/20.
 */
public class ScrollingTextView extends TextView {


    public ScrollingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public ScrollingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollingTextView(Context context) {
        super(context);
    }

    @Override

    public boolean isFocused() {
        //就是把这里返回true即可
        return true;
    }
}
