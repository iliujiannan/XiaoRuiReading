package com.ljn.xiaoruireading.view.concrete_views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private LinearLayout dotContainer;

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



        View vpView = LayoutInflater.from(mContext).inflate(R.layout.item_article_pager,null);

        //小圆点
        dotContainer = (LinearLayout) vpView.findViewById(R.id.article_dot);

        mArticlePager = (ViewPager) vpView.findViewById(R.id.article_pager);
        mAdapter = new ArticleAdapter(mContext, mViews);


        mArticleListView = (ListView) mRootView.findViewById(R.id.article_listview);
        mListAdapter = new ArticleListAdapter(mContext,R.layout.item_article_list,mArticleModels);

        mArticlePager.setAdapter(mAdapter);
        mArticleListView.setAdapter(mListAdapter);

        mArticleListView.addHeaderView(vpView);


        mInitDoc();

        mSetAllListener();
    }
    private void mInitDoc() {
        imgs = new ArrayList<>();
        dotContainer.removeAllViews();


        for (int i = 0; i < mViews.size(); i++) {
            ImageView imageView = new ImageView(mContext);
            if (i == 0) {
                imageView.setImageResource(R.drawable.doc_select);
            } else {
                imageView.setImageResource(R.drawable.doc_select_off);
            }
            //添加到集合
            imgs.add(imageView);
            //添加到线性布局
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            dotContainer.addView(imageView, params);
        }
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
        mInitViewPagerData();
        mInitListViewData();

    }
    private void mInitListViewData(){
        mArticleModels = new ArrayList<>();
        for(int i=0;i<5;i++) {
            ArticleModel articleModel = new ArticleModel();
            mArticleModels.add(articleModel);
        }

    }

    private void mInitViewPagerData(){
        mViews = new ArrayList<View>();
        String[] array = BookShelfViewUtil.listAssets(mContext);
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
