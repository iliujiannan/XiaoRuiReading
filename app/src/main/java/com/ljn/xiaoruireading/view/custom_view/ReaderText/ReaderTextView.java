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
//        Log.i("ljn:", String.valueOf(height));
        int lineHeight = getLineHeight();
//        Log.i("ljn:", String.valueOf(lineHeight));
        int linecount = (height / lineHeight)-1;
        float textSize = getTextSize();
        float linewords = getWidth() / textSize;
        if((int) (linecount * linewords)==0)
            return 1;
        return (int) (linecount * linewords);

    }

}
