package com.ljn.xiaoruireading.view.concrete_views;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.TypedValue;
import android.view.*;
import android.widget.*;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;
import com.ljn.xiaoruireading.util.FileUtil;
import com.ljn.xiaoruireading.view.concrete_views.Adapter.DialogListAdapter;
import com.ljn.xiaoruireading.view.custom_view.ReaderText.ReaderTextView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by 12390 on 2018/8/11.
 */
public class ReaderActivity extends BaseActivity implements ViewPager.OnPageChangeListener, View.OnClickListener {
    private View mPage1, mPage2, mPage3;

    private RelativeLayout mPageTopBar;
    private RelativeLayout mPageBottomBar;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager;
    private ImageView mBackButton;
    private ImageView mMoreButton;
    private TextView mCatalogButton;
    private TextView mSettingButton;
    private ReaderTextView mText;

    private Integer mCurrentCat = 1;
    private Integer mCurrentPage = 0;
    private String mBookname;
    private Integer mCap;
    private String content = "";

    private Integer mCurrentFontSize = 16;

    private Integer mMaxPage = -1;

    private Integer mMaxFontNumPerPage = 472;

    private List<View> mViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        //设置当前窗体为全屏显示
        window.setFlags(flag, flag);
        setContentView(R.layout.activity_reader);
        mSharedPreferences = getSharedPreferences(BaseActivity.SP_NAME, MODE_PRIVATE);

        mInitComponent();

        mInitData();

        mInitPos();

    }

    private void mInitPos(){
        mCurrentCat = mSharedPreferences.getInt("lastCap", 0);
        Integer lastReadPos = mSharedPreferences.getInt("lastFontPos", 0);
        String bookName = mSharedPreferences.getString("lastReadBookName", "");
        if(lastReadPos!=0&&bookName.equals(mBookname)){
            mCurrentPage = lastReadPos/mMaxFontNumPerPage;
            mViewPager.setCurrentItem(mCurrentPage);
            mUpdateOnePage(mCurrentPage);
            mShowMessage("已为您定位到上回阅读的地方");
        }else{
            mCurrentCat = 1;
            mUpdateOnePage(mCurrentPage);
        }
    }

    private void mInitData() {
        Intent intent = getIntent();
        if (intent != null) {
            mBookname = intent.getStringExtra("bookName");
            mCap = intent.getIntExtra("bookCap", 5);
        }
        mGetOneCap();
    }

    private void mGetOneCap() {

        if (mCurrentCat <= mCap) {
            content = FileUtil.readFileByChars(FileUtil.mGetRootPath() + FileUtil.mCachePath + mBookname + FileUtil.mFileMid + mCurrentCat.toString() + FileUtil.mFileType);
            mInitThreePage();

        } else {
            mShowMessage("已经是最后一章啦");
        }


    }

    protected void mInitComponent() {
        mViewPager = (ViewPager) findViewById(R.id.page_pager);

        mPageTopBar = (RelativeLayout) findViewById(R.id.page_topbar);
        mPageBottomBar = (RelativeLayout) findViewById(R.id.page_bottombar);
        mBackButton = (ImageView) findViewById(R.id.page_back);
        mMoreButton = (ImageView) findViewById(R.id.page_more_button);
        mCatalogButton = (TextView) findViewById(R.id.page_catalog);
        mSettingButton = (TextView) findViewById(R.id.page_setting);

        mPageTopBar.setVisibility(View.INVISIBLE);
        mPageBottomBar.setVisibility(View.INVISIBLE);

        mBackButton.setOnClickListener(this);
        mSettingButton.setOnClickListener(this);
        mCatalogButton.setOnClickListener(this);

        //mInitThreePage();


    }


    private void mInitThreePage() {
        LayoutInflater inflater = getLayoutInflater();
        mPage1 = inflater.inflate(R.layout.item_page1, null);
        mPage2 = inflater.inflate(R.layout.item_page2, null);
        mPage3 = inflater.inflate(R.layout.item_page3, null);
        mViews = new ArrayList<>();// 将要分页显示的View装入数组中
        mViews.add(mPage1);
        mViews.add(mPage2);
        mViews.add(mPage3);


        mPagerAdapter = new MPagerAdaper();


        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mCurrentPage = position;
        mUpdateOnePage(position);
        if(position+1<=mMaxPage){
            mUpdateOnePage(position+1);
        }
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.item_page1_text:
            case R.id.item_page2_text:
                mChangePageSettingState();
                break;
            case R.id.page_back:
//                System.out.println("back");
                finish();
                break;
            case R.id.page_setting:
                mChangePageSettingState();
                mShowSettingDialog();
                break;
            case R.id.page_catalog:
                mChangePageSettingState();
                mShowCaDialog();
                break;
        }

    }

    private void mShowSettingDialog(){
//        System.out.println("show");
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_page_setting);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM);
        lp.height = (int) (d.getHeight() * 0.3);
        lp.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(lp);
        dialog.show();

        TextView addBt = (TextView) window.findViewById(R.id.font_setting_right);
        TextView reduceBt = (TextView) window.findViewById(R.id.font_setting_left);
        final TextView size = (TextView) window.findViewById(R.id.font_setting_size);

        addBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentFontSize++;
                size.setText(mCurrentFontSize.toString());
                mUpdateOnePage(mCurrentPage);
            }
        });
        reduceBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentFontSize--;
                size.setText(mCurrentFontSize.toString());
                mUpdateOnePage(mCurrentPage);
            }
        });
        size.setText(mCurrentFontSize.toString());

    }

    private void mShowCaDialog(){
//        System.out.println("show");
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_page_ca);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER_VERTICAL | Gravity.BOTTOM);
        lp.height = (int) (d.getHeight()*0.6);
        lp.width = getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(lp);
        dialog.show();

        ListView listView = (ListView) window.findViewById(R.id.dialog_page_ca_list);
        List<Integer> caps = new ArrayList<>();
        for(int i=1;i<=mCap;i++){
            caps.add(mCap);
        }
        DialogListAdapter adapter = new DialogListAdapter(this, R.layout.item_dialog_list, caps);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCurrentCat = position+1;
                mCurrentPage = 0;
                dialog.cancel();
                mGetOneCap();
            }
        });

    }

    private void mUpdateOnePage(int position){


        if(mCurrentCat<=mCap) {


//        Log.i("mCurrentRealPageNums*", Integer.valueOf(position).toString());

            mPutOnePageContent(position);

            if (mCurrentPage >= mMaxPage) {
                mCurrentCat++;
                mCurrentPage = 0;
                mGetOneCap();
            }
        }else{
            mShowMessage("已经是最后一章啦");
        }
    }

    private void mPutOnePageContent(int pos){
        ReaderTextView contentText = null;
        TextView chapterText = null, readRateText = null, pageNumText = null;

        switch (pos%3){
            case 0:
                contentText = (ReaderTextView) mPage1.findViewById(R.id.item_page1_text);
                chapterText = (TextView) mPage1.findViewById(R.id.page1_chapter_id);
                readRateText = (TextView) mPage1.findViewById(R.id.page1_read_rate);
                pageNumText = (TextView) mPage1.findViewById(R.id.page1_page_num);

                break;
            case 1:
                contentText = (ReaderTextView) mPage2.findViewById(R.id.item_page2_text);
                chapterText = (TextView) mPage2.findViewById(R.id.page2_chapter_id);
                readRateText = (TextView) mPage2.findViewById(R.id.page2_read_rate);
                pageNumText = (TextView) mPage2.findViewById(R.id.page2_page_num);
                break;
            case 2:
                contentText = (ReaderTextView) mPage3.findViewById(R.id.item_page3_text);
                chapterText = (TextView) mPage3.findViewById(R.id.page3_chapter_id);
                readRateText = (TextView) mPage3.findViewById(R.id.page3_read_rate);
                pageNumText = (TextView) mPage3.findViewById(R.id.page3_page_num);
                break;

        }

        mMaxFontNumPerPage = contentText.getEstimatedLength();
        mMaxPage = content.length() / mMaxFontNumPerPage;
        System.out.println("defalut:" + mMaxFontNumPerPage);


//            System.out.println("maxsize=" + contentText.getEstimatedLength());
        contentText.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCurrentFontSize);

        String realContent;
//            System.out.println("length:" + content.length());
        Integer start = mMaxFontNumPerPage * pos;
//            System.out.println("start:" + start);
        if (pos >= mMaxPage) {
            realContent = content.substring(start, content.length() - 1);


        } else {
            realContent = content.substring(start, mMaxFontNumPerPage * (pos + 1));
        }
        contentText.setText(realContent);

        chapterText.setText("第" + mCurrentCat.toString() + "章");


        double a = ((pos + 1) * mMaxFontNumPerPage);
        double b = content.length();
        NumberFormat nbf = NumberFormat.getNumberInstance();
        nbf.setMinimumFractionDigits(1);
        String rate = nbf.format(a / b);
        readRateText.setText(rate + "%");

        Integer c = pos + 1;
        pageNumText.setText(c.toString());

        contentText.setOnClickListener(this);
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
            return 100;
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

    @Override
    protected void onDestroy() {
        mSharedPreferences.edit().putString("lastReadBookName",mBookname).apply();
        mSharedPreferences.edit().putInt("lastCap", mCurrentCat).apply();
        mSharedPreferences.edit().putInt("lastFontPos", mCurrentPage*mMaxFontNumPerPage).apply();
        super.onDestroy();
    }
}
