package com.ljn.xiaoruireading.view.concrete_views.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.model.ArticleModel;
import com.ljn.xiaoruireading.model.Book;
import com.ljn.xiaoruireading.model.BookCityModel;
import com.ljn.xiaoruireading.util.HttpUtil;
import com.ljn.xiaoruireading.util.ImageUtil;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.List;

/**
 * Created by 12390 on 2018/8/15.
 */
public class BookCityListAdapter extends ArrayAdapter {
    List<Bitmap> bitmaps;
    int mResourceId;
    public BookCityListAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
        this.mResourceId = resource;

    }

    public void setBitmaps(List<Bitmap> bitmaps){
        this.bitmaps = bitmaps;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final Book book = (Book) getItem(position); // 获取当前项的Fruit实例


        View view = LayoutInflater.from(getContext()).inflate(mResourceId, null);//实例化一个对象
        TextView bookName = (TextView) view.findViewById(R.id.bookcity_list_title);//获取该布局内的文本视图
        TextView description = (TextView) view.findViewById(R.id.bookcity_list_description);
        TextView author = (TextView) view.findViewById(R.id.bookcity_author);
        TextView la = (TextView) view.findViewById(R.id.bookcity_list_load);
        final ImageView img = (ImageView) view.findViewById(R.id.bookcity_list_image);
        if(book.getBookName()!=null&&bitmaps!=null) {
            bookName.setText(book.getBookName());
            description.setText(book.getBookDescription());
            author.setText(book.getBookAuthor());
            img.setImageBitmap(bitmaps.get(position));
            if(book.getBookLoadingAmount()==0){
                la.setText("0");
            }else {
                la.setText(book.getBookLoadingAmount().toString());
            }


        }

        //set resource
        return view;
    }
}
