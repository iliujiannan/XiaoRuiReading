package com.ljn.xiaoruireading.view.concrete_views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseFragment;
import com.ljn.xiaoruireading.model.BookCityModel;
import com.ljn.xiaoruireading.view.concrete_views.Adapter.BookCityListAdapter;
import com.ljn.xiaoruireading.view.concrete_views.Adapter.BookCityPagerAdapter;
import com.ljn.xiaoruireading.view.concrete_views.Adapter.BookCityTurnAdapter;
import com.ljn.xiaoruireading.view.custom_view.bookshelf.BookShelfViewUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 12390 on 2018/8/9.
 */
public class BookcityFragment extends BaseFragment implements View.OnClickListener {


    //最外层pager
    private ViewPager mPager;
    private BookCityPagerAdapter mPagerAdapter;
    List<View> mPagers;

    //轮播图
    private ViewPager mBookCityTurn;
    private BookCityTurnAdapter mTurnAdapter;


    //end

    //内层listview

    private ListView mBookCityListView;
    private BookCityListAdapter mBookCityListAdapter;
    private List<BookCityModel> mBookCityModels;


    //分类
    private List<TextView> mClassViews;
    int[] ids = {R.id.page_bookcity_class1, R.id.page_bookcity_class2,
            R.id.page_bookcity_class3, R.id.page_bookcity_class4, R.id.page_bookcity_class5};


    private Integer mCurrPage = 0;

    @Override
    public int mGetContentViewId() {
        return R.layout.fragment_bookcity;
    }

    @Override
    protected void mInitAllMembersView(View mRootView) {


        mPager = (ViewPager) mRootView.findViewById(R.id.bookcity_pager);

        mClassViews = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            TextView tmpv = (TextView) mRootView.findViewById(ids[i]);
            tmpv.setOnClickListener(this);
            mClassViews.add(tmpv);
        }


        mPagers = new ArrayList<>();

        for (int i = 1; i < 6; i++) {
            mInitOnePage(i);
        }

        mPagerAdapter = new BookCityPagerAdapter(mContext, mPagers);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new MyPagerListener());
    }

    private void mInitDoc(LinearLayout mDotContainer, List<View> mTurnViews, List<ImageView> imgs) {
        imgs = new ArrayList<>();
        mDotContainer.removeAllViews();


        for (int i = 0; i < mTurnViews.size(); i++) {
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
            mDotContainer.addView(imageView, params);
        }
    }

    private void mInitOnePage(int ind) {
        LinearLayout mDotContainer;
        List<View> mTurnViews = new ArrayList<>();
        List<ImageView> imgs = new ArrayList<>();


        mInitData(mTurnViews, imgs);
        //轮播图
        View vpView = LayoutInflater.from(mContext).inflate(R.layout.item_bookcity_turn, null);
        //每一页
        View pgView = LayoutInflater.from(mContext).inflate(R.layout.page_bookcity, null);
        //小圆点
        mDotContainer = (LinearLayout) vpView.findViewById(R.id.bookcity_turn_dot);

        mBookCityTurn = (ViewPager) vpView.findViewById(R.id.bookcity_turn_pager);
        mTurnAdapter = new BookCityTurnAdapter(mContext, mTurnViews);

        //每一页的listview

        mBookCityListView = (ListView) pgView.findViewById(R.id.page_bookcity_listview);
        mBookCityListAdapter = new BookCityListAdapter(mContext, R.layout.item_bookcity_list, mBookCityModels);

        mBookCityTurn.setAdapter(mTurnAdapter);
        mBookCityListView.setAdapter(mBookCityListAdapter);

        mBookCityListView.addHeaderView(vpView);


        mInitDoc(mDotContainer, mTurnViews, imgs);

        mSetAllListener(mTurnViews.size());


        mPagers.add(pgView);
    }

    private void mSetAllListener(int size) {
        mBookCityTurn.setOnPageChangeListener(new MyTurnListener(mBookCityTurn, size));
        mTurnAdapter.setmListener(new BookCityTurnAdapter.LPagerImgClickListener() {
            @Override
            public void ImgClick(int position) {
                mShowMessage("图片" + position + "被点击了");
            }
        });
    }


    private void mInitData(List<View> mTurnViews, List<ImageView> imgs) {
        mInitTurnData(mTurnViews, imgs);
        mInitListViewData();

    }

    private void mInitListViewData() {
        mBookCityModels = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            BookCityModel bookCityModel = new BookCityModel();
            mBookCityModels.add(bookCityModel);
        }

    }

    private void mInitTurnData(List<View> mTurnViews, List<ImageView> imgs) {

        String[] array = BookShelfViewUtil.listAssets(mContext);
        List<String> realList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            String temp = array[i];
            if (mIsLegle(temp)) {
                realList.add(temp);
            }
        }
        for (String name : realList) {
            ImageView iv = new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Bitmap bitmap = BookShelfViewUtil.readCover(name, mContext);

            iv.setImageBitmap(bitmap);

            mTurnViews.add(iv);
        }

    }


    private boolean mIsLegle(String temp) {
        if (temp.contains("article")) {
            return true;
        }
        return false;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    public void onClick(View v) {
        for (int i = 0; i < ids.length; i++) {
            if (ids[i] == v.getId()) {
                mPager.setCurrentItem(i);
                mChangeClassTextColor(i);
            }
        }
    }

    private void mChangeClassTextColor(int ind) {
        mCurrPage = ind;
        for (int i = 0; i < ids.length; i++) {
            if (i != ind) {
                mClassViews.get(i).setTextColor(mContext.getResources().getColor(R.color.sys_font_gray));
            } else {
                mClassViews.get(ind).setTextColor(mContext.getResources().getColor(R.color.sys_red));
            }
        }

    }

    class MyPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mChangeClassTextColor(position);
        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    class MyTurnListener implements ViewPager.OnPageChangeListener {
        private Handler mTurnHandler;
        private Timer mTurnTimer;
        private TimerTask mTurnTask;
        private boolean mTurnIsTurning = false;
        ViewPager __turn;

        int _size;

        MyTurnListener(ViewPager _turn, int size) {
            __turn = _turn;
            _size = size;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            mImgPlay(position, __turn);
        }

        @Override
        public void onPageSelected(int position) {

        }


        @Override
        public void onPageScrollStateChanged(int state) {


        }

        private void mImgPlay(final int pos, final ViewPager turn) {
            if (!mTurnIsTurning) {
                mTurnIsTurning = true;
                mTurnTask = new TimerTask() {
                    @Override
                    public void run() {
                        Message msg = new Message();
                        msg.what = 1;
                        mTurnHandler.sendMessage(msg);
                    }
                };
                mTurnTimer = new Timer();
                mTurnTimer.schedule(mTurnTask, 3000, 3000);
                mTurnHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        turn.setCurrentItem((pos + 1) % _size);
                    }
                };
            } else {
                mTurnHandler.removeMessages(1);
                mTurnTimer.cancel();
                mTurnTask.cancel();
                mTurnIsTurning = false;
                mImgPlay(pos % _size, turn);
            }

        }
    }


}
