package com.ljn.xiaoruireading.view.custom_view.pageturn.utils;

import android.view.View;

/**
 * Created by 12390 on 2018/8/9.
 */

public class ViewUtils {

    /**
     * 测量、设置View默认宽高
     * @param defaultSize
     * @param measureSpec
     * @return
     */
    public static int measureSize(int defaultSize,int measureSpec) {
        int result = defaultSize;
        int specMode = View.MeasureSpec.getMode(measureSpec);
        int specSize = View.MeasureSpec.getSize(measureSpec);

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize;
        } else if (specMode == View.MeasureSpec.AT_MOST) {
            result = Math.min(result, specSize);
        }
        return result;
    }
}
