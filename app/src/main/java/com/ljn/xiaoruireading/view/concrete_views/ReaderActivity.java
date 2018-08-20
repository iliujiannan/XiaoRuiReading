package com.ljn.xiaoruireading.view.concrete_views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12390 on 2018/8/11.
 */
public class ReaderActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private View mPage1, mPage2;

    private RelativeLayout mPageTopBar;
    private RelativeLayout mPageBottomBar;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private ImageView mBackButton;
    private ImageView mMoreButton;
    private TextView mCatalogButton;
    private TextView mSettingButton;

    private List<View> mViews;
    public static String[] mData = {"一路上黎簇都没有说话，他看着窗外的街道，心中想着，自己是否应该跳下车去，然后一路狂奔。 可是自己能狂奔回哪儿呢？老娘那里？算了吧，老娘虽然还是关心他，但是，老娘已经有了自己的家庭，那里是容不下他的。老爹那里？估计又是一顿胖揍。 自己竟然是在这种时候，明白了什么叫无家可归，他觉得有些可笑。" +
            "", "page1", "page2", "page3", "page4", "page5", "page6", "page7", "page8", "page9"};

    private Integer mCurrentRealPageNums = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_reader);
        mInitData();
        mInitComponent();
    }

    private void mInitData() {
        Intent intent = getIntent();
        if (intent != null) {
            Integer bookId = intent.getIntExtra("book_id", -1);
            Integer uri_type = intent.getIntExtra("uri_type", -1);
            Log.i("ljn:", "" + bookId);
        }
    }

    protected void mInitComponent() {
        mViewPager = (ViewPager) findViewById(R.id.page_pager);
        LayoutInflater inflater = getLayoutInflater();
        mPage1 = inflater.inflate(R.layout.item_page1, null);
        mPage2 = inflater.inflate(R.layout.item_page2, null);
        mPageTopBar = (RelativeLayout) findViewById(R.id.page_topbar);
        mPageBottomBar = (RelativeLayout) findViewById(R.id.page_bottombar);
        mBackButton = (ImageView) findViewById(R.id.page_back_button);
        mMoreButton = (ImageView) findViewById(R.id.page_more_button);
        mCatalogButton = (TextView) findViewById(R.id.page_catalog);
        mSettingButton = (TextView) findViewById(R.id.page_setting);


        mPageTopBar.setVisibility(View.INVISIBLE);
        mPageBottomBar.setVisibility(View.INVISIBLE);

        mViews = new ArrayList<>();// 将要分页显示的View装入数组中
        mViews.add(mPage1);
        mViews.add(mPage2);


        mPagerAdapter = new MPagerAdaper();


        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);


    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentRealPageNums = position;

//        Log.i("mCurrentRealPageNums*", Integer.valueOf(position).toString());

        TextView contentText,chapterText, readRateText, pageNumText;

        int id = R.id.item_page1_text;
        if (mCurrentRealPageNums % 2 == 1) {
            id = R.id.item_page2_text;
        }

        //初始化每一页的四个view
        contentText = (TextView) findViewById(id);
        chapterText = (TextView) findViewById(id);
        readRateText = (TextView) findViewById(id);
        pageNumText = (TextView) findViewById(id);


        contentText.setText(mData[mCurrentRealPageNums]);
        contentText.setOnClickListener(this);


    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        mChangePageSettingState();
    }



    class MPagerAdaper extends PagerAdapter {

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i("destroyItem_position", Integer.valueOf(position).toString());
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View mCurrentView = mViews.get(position % mViews.size());
            Log.i("instant_position", Integer.valueOf(position).toString());

            if (mCurrentView.getParent() != null) {
                ((ViewPager) mCurrentView.getParent()).removeView(mCurrentView);
            }
            container.addView(mCurrentView);
            return mCurrentView;
        }

        @Override
        public int getCount() {
            return 10;
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return view == o;
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_MENU) {
            if (event.getDownTime() > 1000) {
//                mShowMessage("long");
                mChangePageSettingState();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void mChangePageSettingState() {
        int flags = (mPageTopBar.getVisibility() == View.INVISIBLE)
                ? WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
                : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        int cFlag = (mPageTopBar.getVisibility() != View.INVISIBLE)
                ? WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
                : WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().clearFlags(cFlag);
        getWindow().setFlags(flags, flags);
        mPageTopBar.setVisibility((mPageTopBar.getVisibility() == View.INVISIBLE) ? View.VISIBLE : View.INVISIBLE);
        mPageBottomBar.setVisibility((mPageBottomBar.getVisibility() == View.INVISIBLE) ? View.VISIBLE : View.INVISIBLE);
    }

}
