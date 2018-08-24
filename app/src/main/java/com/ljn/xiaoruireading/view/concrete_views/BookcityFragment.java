package com.ljn.xiaoruireading.view.concrete_views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseFragment;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.model.Book;
import com.ljn.xiaoruireading.model.BookCityModel;
import com.ljn.xiaoruireading.presenter.BookCityPresenter;
import com.ljn.xiaoruireading.util.HttpUtil;
import com.ljn.xiaoruireading.util.ImageUtil;
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


    BookCityPresenter bookCityPresenter;

    //最外层pager
    private ViewPager mPager;
    private BookCityPagerAdapter mPagerAdapter;
    private List<View> mPagers;

    //轮播图
    private List<ViewPager> mBookCityTurnList = new ArrayList<>();
    private List<BookCityTurnAdapter> mTurnAdapterList = new ArrayList<>();

    //end

    //内层listview

    private List<ListView> mBookCityListViewList = new ArrayList<>();
    private List<BookCityListAdapter> mBookCityListAdapterList = new ArrayList<>();

    private List<List<Book>> mBookList = new ArrayList();


    //分类
    private List<TextView> mClassViews;
    int[] ids = {R.id.page_bookcity_class1, R.id.page_bookcity_class2,
            R.id.page_bookcity_class3, R.id.page_bookcity_class4, R.id.page_bookcity_class5};


    private Integer mCurrPage = 0;

    private Integer mNumPerTurn = 4;

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

        mChangeClassTextColor(0);
        mUpdateData();

        mPagers = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            mInitOnePage(i);
        }

        mPagerAdapter = new BookCityPagerAdapter(mContext, mPagers);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new MyPagerListener());
    }

    private void mInitDoc(LinearLayout mDotContainer, int size, List<ImageView> imgs) {
        imgs = new ArrayList<>();
        mDotContainer.removeAllViews();


        for (int i = 0; i < size; i++) {
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
        List<ImageView> imgs = new ArrayList<>();

        mInitData();

        //轮播图
        View vpView = LayoutInflater.from(mContext).inflate(R.layout.item_bookcity_turn, null);
        //每一页
        View pgView = LayoutInflater.from(mContext).inflate(R.layout.page_bookcity, null);
        //小圆点
        mDotContainer = (LinearLayout) vpView.findViewById(R.id.bookcity_turn_dot);

        ViewPager mBookCityTurn = (ViewPager) vpView.findViewById(R.id.bookcity_turn_pager);
        BookCityTurnAdapter mTurnAdapter = new BookCityTurnAdapter(mContext);
        mTurnAdapter.mUpdateData(null);

        //每一页的listview

        ListView mBookCityListView = (ListView) pgView.findViewById(R.id.page_bookcity_listview);
        mBookCityListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mShowBookDetail(position - 1 + mNumPerTurn);
            }
        });
        BookCityListAdapter mBookCityListAdapter =
                new BookCityListAdapter(mContext, R.layout.item_bookcity_list, mBookList.get(ind));

        mBookCityTurn.setAdapter(mTurnAdapter);
        mBookCityListView.setAdapter(mBookCityListAdapter);

        mBookCityListView.addHeaderView(vpView);


        mInitDoc(mDotContainer, mNumPerTurn, imgs);

        mBookCityTurn.setOnPageChangeListener(new MyTurnListener(mBookCityTurn, mNumPerTurn));
        mTurnAdapter.setmListener(new BookCityTurnAdapter.LPagerImgClickListener() {
            @Override
            public void ImgClick(int position) {
                mShowBookDetail(position);
            }
        });


        //添加新的一页
        mBookCityTurnList.add(mBookCityTurn);
        mBookCityListViewList.add(mBookCityListView);
        mBookCityListAdapterList.add(mBookCityListAdapter);
        mTurnAdapterList.add(mTurnAdapter);
        mPagers.add(pgView);
    }


    private void mInitData() {
        mInitListViewData();

    }

    private void mInitListViewData() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Book book = new Book();
            books.add(book);
        }
        mBookList.add(books);

    }


    private void mUpdatePageData() {
        mUpdateTurnData();

        final List<Bitmap> bitmaps = new ArrayList<>();
        for (int i = mNumPerTurn; i < mBookList.get(mCurrPage).size(); i++) {
            Book book = mBookList.get(mCurrPage).get(i);
            Bitmap bitmap = ImageUtil.getHttpBitmap(HttpUtil.baseUri + book.getBookImg());
            bitmaps.add(bitmap);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mUpdateListData(bitmaps);
                mTurnAdapterList.get(mCurrPage).notifyDataSetChanged();
                mPagerAdapter.notifyDataSetChanged();
            }
        });

    }

    private void mUpdateTurnData() {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < mNumPerTurn; i++) {
            books.add(mBookList.get(mCurrPage).get(i));
        }
        final List<ImageView> imageViews = new ArrayList<>();
        for (Book book : books) {
            ImageView iv = new ImageView(mContext);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);

            Bitmap bitmap = ImageUtil.getHttpBitmap(HttpUtil.baseUri + book.getBookImg());
            iv.setImageBitmap(bitmap);
            imageViews.add(iv);
            mTurnAdapterList.get(mCurrPage).mUpdateData(imageViews);

        }
    }


    private void mUpdateListData(List<Bitmap> bitmaps) {
        List<Book> books = new ArrayList<>();
        for (int i = mNumPerTurn; i < mBookList.get(mCurrPage).size(); i++) {
            books.add(mBookList.get(mCurrPage).get(i));
        }
        BookCityListAdapter mBookCityListAdapter =
                new BookCityListAdapter(mContext, R.layout.item_bookcity_list, books);
        mBookCityListAdapter.setBitmaps(bitmaps);
        mBookCityListViewList.get(mCurrPage).setAdapter(mBookCityListAdapter);
    }


    private void mShowBookDetail(int ind) {
        Intent intent = new Intent(getActivity(), BookDetailActivity.class);
//        System.out.println("ind***" + ind);
//        System.out.println(mBookList.get(mCurrPage).get(ind).getBookName());
//        System.out.println(mBookList.get(mCurrPage).get(ind).getBookId());
        intent.putExtra("bookId", mBookList.get(mCurrPage).get(ind).getBookId());
        startActivityForResult(intent, 999);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bookCityPresenter = new BookCityPresenter();
        bookCityPresenter.attachView(this);
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
        for (int i = 0; i < ids.length; i++) {
            if (i != ind) {
                mClassViews.get(i).setTextColor(mContext.getResources().getColor(R.color.sys_font_gray));
            } else {
                mClassViews.get(i).setTextColor(mContext.getResources().getColor(R.color.sys_red));
            }
        }

    }

    public void mUpdateData() {
        bookCityPresenter.mGetBooks(mCurrPage.toString());
    }

    @Override
    public void onActionSucc(BaseModel result) {
        int i = mBookList.get(mCurrPage).size();
        for (int j = 0; j < i; j++) {
            mBookList.get(mCurrPage).remove(0);
        }
        for (Book book : ((BookCityModel) result).getBooks()) {
            mBookList.get(mCurrPage).add(book);
        }
        mUpdatePageData();


    }

    class MyPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            System.out.println(position);

        }

        @Override
        public void onPageSelected(int position) {
            mChangeClassTextColor(position);
            mCurrPage = position;
            mUpdateData();
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
