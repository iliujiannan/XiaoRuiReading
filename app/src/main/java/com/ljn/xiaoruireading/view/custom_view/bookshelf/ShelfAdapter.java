package com.ljn.xiaoruireading.view.custom_view.bookshelf;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.ljn.xiaoruireading.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12390 on 2018/8/9.
 */
public class ShelfAdapter extends BaseAdapter {

    private List<Bitmap> bitmapList;
    private boolean isFirst = true;

    public final ArrayList<Item> items = new ArrayList<Item>();
    private LayoutInflater mInflater;

    private Context mContext;
    public ShelfAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public boolean isFirst() {
        return isFirst;
    }

    public void setFirst(boolean first) {
        isFirst = first;
    }

    public List<Bitmap> getBitmapList() {
        return bitmapList;
    }

    public void setBitmapList(List<Bitmap> bitmapList) {
        this.bitmapList = bitmapList;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_bookshelf, parent, false);

            holder = new ViewHolder();
            holder.iv_image = (ImageView)convertView.findViewById(R.id.item_bookshelf_image);

            convertView.setTag(holder);

        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        Item item = items.get(position);
        Bitmap bitmap;
        if(isFirst) {

            bitmap = BookShelfViewUtil.readCover(item.filename, mContext);
        }else{
            bitmap = bitmapList.get(position);
        }

        holder.iv_image.setImageBitmap(bitmap);


        return convertView;
    }

    public static class ViewHolder{
        ImageView iv_image;
    }

    public static class Item {
        public String filename;
    }
}
