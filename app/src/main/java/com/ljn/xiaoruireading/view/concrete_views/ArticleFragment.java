package com.ljn.xiaoruireading.view.concrete_views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.*;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseFragment;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.model.Article;
import com.ljn.xiaoruireading.model.ArticleModel;
import com.ljn.xiaoruireading.model.PersonalModel;
import com.ljn.xiaoruireading.presenter.ArticlePresenter;
import com.ljn.xiaoruireading.util.HttpUtil;
import com.ljn.xiaoruireading.util.ImageUtil;
import com.ljn.xiaoruireading.view.concrete_views.Adapter.ArticleTurnAdapter;
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
    private ArticleTurnAdapter mAdapter;
    private List<ImageView> imgs;
    private LinearLayout dotContainer;

    private Handler mHandler;
    private Timer mTimer;
    private TimerTask mTask;
    private boolean mIsTurning = false;
    //end
    public static Integer mNumPerTurn = 4;


    private ListView mArticleListView;
    private ArticleListAdapter mListAdapter;
    private List<Article> articles;
    private List<PersonalModel.User> users;

    ArticlePresenter articlePresenter;



    @Override
    public int mGetContentViewId() {
        return R.layout.fragment_article;
    }

    @Override
    protected void mInitAllMembersView(View mRootView) {

        mInitData();



        View vpView = LayoutInflater.from(mContext).inflate(R.layout.item_article_pager,null);

        //小圆点
        dotContainer = (LinearLayout) vpView.findViewById(R.id.article_dot);

        mArticlePager = (ViewPager) vpView.findViewById(R.id.article_pager);
        mAdapter = new ArticleTurnAdapter(mContext);


        mArticleListView = (ListView) mRootView.findViewById(R.id.article_listview);
        mListAdapter = new ArticleListAdapter(mContext,R.layout.item_article_list,articles);

        mArticlePager.setAdapter(mAdapter);
        mArticleListView.setAdapter(mListAdapter);

        mArticleListView.addHeaderView(vpView);


        mInitDoc();

        mSetAllListener();

        mUpdateData();
    }

    public void mUpdateTurnAndList(){

        mUpdateTurn();
        List<Bitmap> bitmaps = new ArrayList<>();
        List<Article> articleList = new ArrayList<>();
        List<Bitmap> photos = new ArrayList<>();
        List<String> names = new ArrayList<>();
        for(int i=mNumPerTurn;i<articles.size();i++){
            Bitmap bitmap = ImageUtil.getHttpBitmap(HttpUtil.baseUri + articles.get(i).getArticleImg());
            bitmaps.add(bitmap);
            articleList.add(articles.get(i));
            photos.add(ImageUtil.getHttpBitmap(HttpUtil.baseUri + users.get(i).getUserPhoto()));
            names.add(users.get(i).getUserNickName());
        }
        mListAdapter = new ArticleListAdapter(mContext, R.layout.item_article_list, articleList);
        mListAdapter.setBitmaps(bitmaps);
        mListAdapter.setAuthorPhotos(photos);
        mListAdapter.setUserNames(names);


        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
                mArticleListView.setAdapter(mListAdapter);
            }
        });
    }

    private void mUpdateTurn(){
        List<ImageView> imageViews = new ArrayList<>();
        for(int i=0;i<mNumPerTurn;i++){
            ImageView iv = new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Bitmap bitmap = ImageUtil.getHttpBitmap(HttpUtil.baseUri + articles.get(i).getArticleImg());
            System.out.println(HttpUtil.baseUri + articles.get(i).getArticleImg());
            iv.setImageBitmap(bitmap);
            imageViews.add(iv);

        }
        mAdapter.mUpdateData(imageViews);
    }
    private void mInitDoc() {
        imgs = new ArrayList<>();
        dotContainer.removeAllViews();
        for (int i = 0; i < mNumPerTurn; i++) {
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
        mArticleListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mShowDetail(mNumPerTurn+position);
            }
        });
        mAdapter.setmListener(new ArticleTurnAdapter.LPagerImgClickListener() {
            @Override
            public void ImgClick(int position) {
                mShowDetail(position+1);
            }
        });
    }

    private void mShowDetail(int ind){
        Intent intent = new Intent(getActivity(), ArticleDetailActivity.class);
        intent.putExtra("articleId", articles.get(ind).getArticleId()-1);
        startActivity(intent);
    }
    private void mUpdateData(){
        articlePresenter.mGetArticles();
    }


    @Override
    public void onActionSucc(BaseModel result) {
        articles = ((ArticleModel)result).getArticles();
        users = ((ArticleModel)result).getUsers();
        mUpdateTurnAndList();
    }

    private void mInitData() {
        mInitListViewData();

    }
    private void mInitListViewData(){
        articles = new ArrayList<>();
        for(int i=0;i<5;i++) {
            Article article = new Article();
            articles.add(article);
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
                    mArticlePager.setCurrentItem((pos+1)%mNumPerTurn);
                }
            };
        }else{
            mHandler.removeMessages(1);
            mTimer.cancel();
            mTask.cancel();
            mIsTurning = false;
            mImgPlay(pos%mNumPerTurn);
        }

    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        articlePresenter = new ArticlePresenter();
        articlePresenter.attachView(this);
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
