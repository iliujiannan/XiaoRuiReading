package com.ljn.xiaoruireading.view.concrete_views.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by 12390 on 2018/8/15.
 */

public class BookCityPagerAdapter extends PagerAdapter {

    Context ctx;
    List<View> mlist;

    public BookCityPagerAdapter(Context ctx, List<View> mlist) {
        this.ctx = ctx;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        container.addView(mlist.get(position));
        return mlist.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mlist.get(position));

    }


}
