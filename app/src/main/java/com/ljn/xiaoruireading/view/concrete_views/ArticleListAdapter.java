package com.ljn.xiaoruireading.view.concrete_views;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by 12390 on 2018/8/15.
 */
public class ArticleListAdapter extends ArrayAdapter {
    public ArticleListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return super.getView(position, convertView, parent);
    }
}
