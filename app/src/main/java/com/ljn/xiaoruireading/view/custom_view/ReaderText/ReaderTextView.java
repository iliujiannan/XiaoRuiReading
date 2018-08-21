package com.ljn.xiaoruireading.view.custom_view.ReaderText;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by 12390 on 2018/8/21.
 */
public class ReaderTextView extends TextView {
    public ReaderTextView(Context context) {
        super(context);
    }

    public ReaderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ReaderTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    // 构造函数略...
    public int getEstimatedLength() {
        int height = getHeight();
        int lineHeight = getLineHeight();
        int linecount = height / lineHeight;

        float textSize = getTextSize();
        float linewords = getWidth() / textSize;
        return (int) (linecount * linewords);
    }

}
