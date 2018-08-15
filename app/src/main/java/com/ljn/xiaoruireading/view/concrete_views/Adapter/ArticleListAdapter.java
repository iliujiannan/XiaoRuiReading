package com.ljn.xiaoruireading.view.concrete_views.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.model.ArticleModel;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

/**
 * Created by 12390 on 2018/8/15.
 */
public class ArticleListAdapter extends ArrayAdapter {
    int mResourceId;
    public ArticleListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.mResourceId = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ArticleModel article = (ArticleModel) getItem(position); // 获取当前项的Fruit实例


        View view = LayoutInflater.from(getContext()).inflate(mResourceId, null);//实例化一个对象
        CircleImageView photo = (CircleImageView) view.findViewById(R.id.article_list_photo);//获取该布局内的图片视图
        TextView nickname = (TextView) view.findViewById(R.id.article_list_nickname);//获取该布局内的文本视图
        TextView title = (TextView) view.findViewById(R.id.article_list_title);
        TextView description = (TextView) view.findViewById(R.id.article_list_description);
        ImageView img = (ImageView) view.findViewById(R.id.article_list_image);

        //set resource
        return view;
    }
}
