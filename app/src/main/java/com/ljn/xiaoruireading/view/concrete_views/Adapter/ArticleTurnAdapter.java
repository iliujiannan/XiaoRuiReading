package com.ljn.xiaoruireading.view.concrete_views.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.ljn.xiaoruireading.view.custom_view.bookshelf.BookShelfViewUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12390 on 2018/8/15.
 */

public class ArticleTurnAdapter extends PagerAdapter {

    Context ctx;
    List<View> mTurnViews;
    private boolean isFirst = true;

    public void setmListener(LPagerImgClickListener mListener) {
        this.mListener = mListener;
    }

    LPagerImgClickListener mListener;

    public ArticleTurnAdapter(Context ctx) {
        this.ctx = ctx;
        this.mTurnViews = new ArrayList<>();
        mUpdateData(null);
    }

    @Override
    public int getCount() {
        return mTurnViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        container.addView(mTurnViews.get(position));
        mTurnViews.get(position).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(ctx, "点击了" + position, Toast.LENGTH_SHORT).show();
                if (mListener != null) {
                    mListener.ImgClick(position);
                }
            }
        });
        return mTurnViews.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mTurnViews.get(position));

    }

    public interface LPagerImgClickListener {
        void ImgClick(int position);
    }

    public void mUpdateData(List<ImageView> imageViews){
        if(isFirst){
            mFirstInit();
            isFirst = false;
        }else {

            System.out.println(imageViews.size());
            int ind = 0;
            for (ImageView img: imageViews) {
                if(ind<mTurnViews.size()){
                    mTurnViews.set(ind++, img);
                }else{
                    mTurnViews.add(img);
                }
            }
        }
    }

    private void mFirstInit(){
        String[] array = BookShelfViewUtil.listAssets(ctx);
        List<String> realList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            String temp = array[i];
            if (mIsLegle(temp)) {
                realList.add(temp);
            }
        }
        for (String name : realList) {
            ImageView iv = new ImageView(ctx);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Bitmap bitmap = BookShelfViewUtil.readCover(name, ctx);

            iv.setImageBitmap(bitmap);

            mTurnViews.add(iv);
        }
    }
    private boolean mIsLegle(String temp){
        if(temp.contains("article")){
            return true;
        }
        return false;
    }
}
