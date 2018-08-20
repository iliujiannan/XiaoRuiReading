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
import com.ljn.xiaoruireading.model.ArticleModel;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

/**
 * Created by 12390 on 2018/8/15.
 */
public class ArticleListAdapter extends ArrayAdapter {
    int mResourceId;
    List<Bitmap> bitmaps;
    List<String> userNames;
    List<Bitmap> authorPhotos;

    public ArticleListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.mResourceId = resource;

    }


    public void setBitmaps(List<Bitmap> bitmaps) {
        this.bitmaps = bitmaps;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public void setAuthorPhotos(List<Bitmap> authorPhotos) {
        this.authorPhotos = authorPhotos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Article article = (Article) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(mResourceId, null);//实例化一个对象
        CircleImageView photo = (CircleImageView) view.findViewById(R.id.article_list_photo);//获取该布局内的图片视图
        TextView nickname = (TextView) view.findViewById(R.id.article_list_nickname);//获取该布局内的文本视图
        TextView title = (TextView) view.findViewById(R.id.article_list_title);
        TextView description = (TextView) view.findViewById(R.id.article_list_description);
        ImageView img = (ImageView) view.findViewById(R.id.article_list_image);

        if (article.getArticleTitle() != null) {
            img.setImageBitmap(bitmaps.get(position));
            String tempTitle = article.getArticleTitle();
            if (article.getArticleTitle().length() > 10) {
                tempTitle = article.getArticleTitle().substring(0, 10) + "...";
            }
            String tempDes = article.getArticleDescription();
            if(article.getArticleDescription().length()>40){
                tempDes = article.getArticleDescription().substring(0, 40) + "...";
            }
            title.setText(tempTitle);
            description.setText(tempDes);
            photo.setImageBitmap(authorPhotos.get(position));
            nickname.setText(userNames.get(position));
        }

        //set resource
        return view;
    }
}
