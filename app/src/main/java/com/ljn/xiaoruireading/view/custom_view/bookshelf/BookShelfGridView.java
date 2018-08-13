package com.ljn.xiaoruireading.view.custom_view.bookshelf;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import com.ljn.xiaoruireading.R;

/**
 * Created by 12390 on 2018/8/9.
 */
public class BookShelfGridView extends GridView {

    private static final String tag = BookShelfGridView.class.getSimpleName();


    private Bitmap background;
    private Bitmap dock;

    public BookShelfGridView(Context context, AttributeSet attrs) {
        super(context, attrs);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.bookshelf_layer_center);
        dock = BitmapFactory.decodeResource(getResources(), R.drawable.bookshelf_dock);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {


        int width = getWidth();
        int height = getHeight();

        int childCount = getChildCount();

        View child = childCount > 0 ? getChildAt(0) : null;

        int top = null != child ? child.getTop() : 0;


        int childHeight = child.getHeight();

        int backgroundWidth = background.getWidth();
        int backgroundHeight = background.getHeight();
        int dockHeight = dock.getHeight();

        Log.d(tag, "childHeight = "+childHeight+", dockHeight = "+dockHeight);
        Log.d(tag, "childCount = " + childCount + ", top = " + top + ", bwidth = " + backgroundWidth + ", bheight = " + backgroundHeight + ", width = " + width + ", height = " + height);


        for (int y = top; y < height; y += backgroundHeight) {
            for (int x = 0; x < width; x += backgroundWidth) {
                canvas.drawBitmap(background, x, y, null);//画背景图
                canvas.drawBitmap(dock, x, y + backgroundHeight - dockHeight, null);//画托盘图
            }
        }

        super.dispatchDraw(canvas);
    }


}
