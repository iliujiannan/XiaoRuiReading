package com.ljn.xiaoruireading.view.concrete_views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.model.Book;
import com.ljn.xiaoruireading.model.BookDetailModel;
import com.ljn.xiaoruireading.presenter.BookDetailPresenter;
import com.ljn.xiaoruireading.util.HttpUtil;
import com.ljn.xiaoruireading.util.ImageUtil;

/**
 * Created by 12390 on 2018/8/18.
 */
public class BookDetailActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackButton;
    private TextView mTitle;
    private TextView mAuthor;
    private TextView mLabel;
    private TextView mCa;
    private TextView mPrice;
    private TextView mLa;
    private TextView mDes;
    private TextView mBottBt;
    private ImageView mBottImg;
    private ImageView mBookImg;

    private RelativeLayout mBott;

    public static String[] labels = {"全部","出版", "女生", "男生", "悬疑"};

    private BookDetailPresenter bookDetailPresenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        bookDetailPresenter = new BookDetailPresenter();
        bookDetailPresenter.attachView(this);
        mInitComponent();
    }

    @Override
    protected void mInitComponent() {
        System.out.println("id is:" + getIntent().getIntExtra("bookId", 0));

        mBackButton = (ImageView) findViewById(R.id.book_detail_back_bt);
        mTitle = (TextView) findViewById(R.id.book_detail_title);
        mAuthor = (TextView) findViewById(R.id.book_detail_author);
        mLabel = (TextView) findViewById(R.id.book_detail_lable);
        mCa = (TextView) findViewById(R.id.book_detail_ca);
        mPrice = (TextView) findViewById(R.id.book_detail_price);
        mLa = (TextView) findViewById(R.id.book_detail_la);
        mDes = (TextView) findViewById(R.id.book_detail_description);
        mBottBt = (TextView) findViewById(R.id.book_detail_bootombt);
        mBottImg = (ImageView) findViewById(R.id.book_detail_botoomic);
        mBott = (RelativeLayout) findViewById(R.id.book_detail_bott);
        mBookImg = (ImageView) findViewById(R.id.book_detail_bookimg);

        mBackButton.setOnClickListener(this);

        mBott.setOnClickListener(this);

        bookDetailPresenter.mGetBookDetail(getIntent().getIntExtra("bookId", 0));
    }

    @Override
    public void onActionSucc(final BaseModel result) {
        final Book book = ((BookDetailModel) result).getBook();

        final Bitmap bitmap = ImageUtil.getHttpBitmap(HttpUtil.baseUri + book.getBookImg());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                mTitle.setText(book.getBookName());
                mAuthor.setText(book.getBookAuthor());
                mLabel.setText(labels[Integer.valueOf(book.getBookLabel())]);
                mCa.setText("共" + book.getBookChapterAmount().toString() + "章");
                mPrice.setText("￥" + book.getBookPrice().toString() + "书币");
                mLa.setText(book.getBookLoadingAmount().toString() + "次下载");
                mBookImg.setImageBitmap(bitmap);
                String des = book.getBookDescription();
                des.replaceAll(" ", "");
                if(des.length()>200){
                    des = book.getBookDescription().substring(0,200) + "...";
                }

                mDes.setText(des);
                if(book.getBookPrice()==0){
                    mBottImg.setImageResource(R.drawable.ic_add2shelf);
                    mBottBt.setText("加入书架");
                }else {
                    mBottImg.setImageResource(R.drawable.ic_shopcart_white);
                    mBottBt.setText("加入购物车");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

    }
}
