package com.ljn.xiaoruireading.view.concrete_views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.ImageView;
import android.widget.ListView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseFragment;
import com.ljn.xiaoruireading.model.ArticleModel;
import com.ljn.xiaoruireading.view.concrete_views.Adapter.ArticleAdapter;
import com.ljn.xiaoruireading.view.concrete_views.Adapter.ArticleListAdapter;
import com.ljn.xiaoruireading.view.custom_view.bookshelf.BookShelfViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 12390 on 2018/8/9.
 */
public class ArticleFragment extends BaseFragment implements ViewPager.OnPageChangeListener {


    //轮播图
    private ViewPager mArticlePager;
    private ArticleAdapter mAdapter;
    private List<View> mViews;
    private List<ImageView> imgs;

    private Handler mHandler;
    private Timer mTimer;
    private TimerTask mTask;
    private boolean mIsTurning = false;
    //end


    private ListView mArticleListView;
    private ArticleListAdapter mListAdapter;
    private List<ArticleModel> mArticleModels;



    @Override
    public int mGetContentViewId() {
        return R.layout.fragment_article;
    }

    @Override
    protected void mInitAllMembersView(View mRootView) {

        mInitData(mRootView);


        mArticlePager = (ViewPager) mRootView.findViewById(R.id.article_pager);
        mAdapter = new ArticleAdapter(mContext, mViews);


        mArticleListView = (ListView) mRootView.findViewById(R.id.article_listview);
        mListAdapter = new ArticleListAdapter(mContext,R.layout.item_article_list,mArticleModels);

        mArticlePager.setAdapter(mAdapter);
        mArticleListView.setAdapter(mListAdapter);


        mSetAllListener();
    }

    private  void mSetAllListener(){
        mArticlePager.setOnPageChangeListener(this);
        mAdapter.setmListener(new ArticleAdapter.LPagerImgClickListener() {
            @Override
            public void ImgClick(int position) {
                mShowMessage("图片" + position + "被点击了");
            }
        });
    }


    private void mInitData(View view) {
        mInitViewPagerData(view);
        mInitListViewData(view);

    }
    private void mInitListViewData(View view){
        mArticleModels = new ArrayList<>();
        for(int i=0;i<5;i++) {
            ArticleModel articleModel = new ArticleModel();
            mArticleModels.add(articleModel);
        }

    }

    private void mInitViewPagerData(View view){
        mViews = new ArrayList<View>();
        String[] array = BookShelfViewUtil.listAssets(view.getContext());
        List<String> realList = new ArrayList<>();
        for(int i=0;i<array.length;i++){
            String temp = array[i];
            if(mIsLegle(temp)){
                realList.add(temp);
            }
        }
        for (String name : realList) {
            ImageView iv = new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Bitmap bitmap = BookShelfViewUtil.readCover(name, mContext);

            iv.setImageBitmap(bitmap);

            mViews.add(iv);
        }

        imgs = new ArrayList<ImageView>(mViews.size());
    }

    private void mImgPlay(final int pos){
        if(!mIsTurning){
            mIsTurning = true;
            mTask = new TimerTask() {
                @Override
                public void run() {
                    Message msg = new Message();
                    msg.what = 1;
                    mHandler.sendMessage(msg);
                }
            };
            mTimer = new Timer();
            mTimer.schedule(mTask, 3000, 3000);
            mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    mArticlePager.setCurrentItem((pos+1)%mViews.size());
                }
            };
        }else{
            mHandler.removeMessages(1);
            mTimer.cancel();
            mTask.cancel();
            mIsTurning = false;
            mImgPlay(pos%mViews.size());
        }

    }


    private boolean mIsLegle(String temp){
        if(temp.contains("article")){
            return true;
        }
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mImgPlay(position);
    }

    @Override
    public void onPageSelected(int position) {

    }


    @Override
    public void onPageScrollStateChanged(int state) {
    }



}
