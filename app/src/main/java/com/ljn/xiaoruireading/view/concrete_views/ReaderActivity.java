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
    private View mPage1, mPage2;

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
    private String mBookname = "侯府春暖";
    private Integer mCap = 5;
    private Integer mBookId;
    private Integer userId = 0;
    private String secretKey = "";
    private String content = "";

    private Integer mCurrentFontSize = 16;

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
        userId = mSharedPreferences.getInt("userId", 0);
        secretKey = mSharedPreferences.getString("secretKey", "");


        mInitComponent();
        mInitData();

    }

    private void mInitData() {
        Intent intent = getIntent();
        if (intent != null) {
            mBookId = intent.getIntExtra("bookId", -1);
            mBookname = intent.getStringExtra("bookName");
            mCap = intent.getIntExtra("bookCap", -1);
        }
        mGetOneCap();
    }

    private void mGetOneCap() {

        if (mCurrentCat <= mCap) {
            content = FileUtil.readFileByChars(FileUtil.mGetRootPath() + FileUtil.mCachePath + mBookname + FileUtil.mFileMid + mCurrentCat.toString() + FileUtil.mFileType);
            mInitTowPage();
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

        mInitTowPage();


    }


    private void mInitTowPage() {
        LayoutInflater inflater = getLayoutInflater();
        mPage1 = inflater.inflate(R.layout.item_page1, null);
        mPage2 = inflater.inflate(R.layout.item_page2, null);
        mViews = new ArrayList<>();// 将要分页显示的View装入数组中
        mViews.add(mPage1);
        mViews.add(mPage2);


        mPagerAdapter = new MPagerAdaper();


        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        mUpdateOnePage(position);
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
            mCurrentPage = position;

//        Log.i("mCurrentRealPageNums*", Integer.valueOf(position).toString());

            ReaderTextView contentText;
            TextView chapterText, readRateText, pageNumText;

            if (mCurrentPage % 2 == 1) {
                //初始化每一页的四个view
                contentText = (ReaderTextView) findViewById(R.id.item_page2_text);
                chapterText = (TextView) findViewById(R.id.page2_chapter_id);
                readRateText = (TextView) findViewById(R.id.page2_read_rate);
                pageNumText = (TextView) findViewById(R.id.page2_page_num);
            } else {
                //初始化每一页的四个view
                contentText = (ReaderTextView) findViewById(R.id.item_page1_text);
                chapterText = (TextView) findViewById(R.id.page1_chapter_id);
                readRateText = (TextView) findViewById(R.id.page1_read_rate);
                pageNumText = (TextView) findViewById(R.id.page1_page_num);
            }

            System.out.println("maxsize=" + contentText.getEstimatedLength());
            contentText.setTextSize(TypedValue.COMPLEX_UNIT_SP, mCurrentFontSize);

            String realContent;
//            System.out.println("length:" + content.length());
            Integer start = contentText.getEstimatedLength() * mCurrentPage;
//            System.out.println("start:" + start);
            if (mCurrentPage >= (content.length() / contentText.getEstimatedLength())) {
                realContent = content.substring(start, content.length() - 1);


            } else {
                realContent = content.substring(start, contentText.getEstimatedLength() * (mCurrentPage + 1));
            }
            contentText.setText(realContent);

            chapterText.setText("第" + mCurrentCat.toString() + "章");


            double a = ((mCurrentPage + 1) * contentText.getEstimatedLength());
            double b = content.length();
            NumberFormat nbf = NumberFormat.getNumberInstance();
            nbf.setMinimumFractionDigits(1);
            String rate = nbf.format(a / b);
            readRateText.setText(rate + "%");

            Integer c = mCurrentPage + 1;
            pageNumText.setText(c.toString());

            contentText.setOnClickListener(this);


            if (mCurrentPage >= (content.length() / contentText.getEstimatedLength())) {
                mCurrentCat++;
                mCurrentPage = 0;
                mGetOneCap();
            }
        }else{
            mShowMessage("已经是最后一章啦");
        }
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
