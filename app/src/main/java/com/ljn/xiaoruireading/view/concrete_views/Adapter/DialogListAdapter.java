package com.ljn.xiaoruireading.view.concrete_views.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.model.Article;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

/**
 * Created by 12390 on 2018/8/15.
 */
public class DialogListAdapter extends ArrayAdapter {
    int mResourceId;

    public DialogListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.mResourceId = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Integer ca = position + 1;
        View view = LayoutInflater.from(getContext()).inflate(mResourceId, null);//实例化一个对象
        TextView textView = (TextView) view.findViewById(R.id.item_dialog_ca);
        textView.setText("第" + ca.toString() + "章");
        //set resource
        return view;
    }
}
