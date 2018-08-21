package com.ljn.xiaoruireading.view.concrete_views;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
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
import com.ljn.xiaoruireading.util.FileUtil;
import com.ljn.xiaoruireading.util.HttpUtil;
import com.ljn.xiaoruireading.util.ImageUtil;
import com.ljn.xiaoruireading.view.abstract_views.IBookDetailView;
import org.michaelevans.colorart.library.ColorArt;

import java.io.File;

/**
 * Created by 12390 on 2018/8/18.
 */
public class BookDetailActivity extends BaseActivity implements View.OnClickListener, IBookDetailView {

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
    private RelativeLayout mTopBar;

    private RelativeLayout mBott;

    private Integer userId = -1;
    private String secretKey = "";
    private Integer mBookId = -1;

    private Book book;

    public static String[] labels = {"全部", "出版", "女生", "男生", "悬疑"};

    private BookDetailPresenter bookDetailPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        bookDetailPresenter = new BookDetailPresenter();
        bookDetailPresenter.attachView(this);

        mSharedPreferences = getSharedPreferences(BaseActivity.SP_NAME, MODE_PRIVATE);
        userId = mSharedPreferences.getInt("userId", -1);
        secretKey = mSharedPreferences.getString("secretKey", "");
        mBookId = getIntent().getIntExtra("bookId", -1);
        mInitComponent();

    }

    @Override
    protected void mInitComponent() {
        System.out.println("id is:" + mBookId);

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
        mTopBar = (RelativeLayout) findViewById(R.id.book_detail_top_bar);

        mBackButton.setOnClickListener(this);

        mBott.setOnClickListener(this);

        bookDetailPresenter.mGetBookDetail(mBookId);
    }

    @Override
    public void onActionSucc(final BaseModel result) {
        book = ((BookDetailModel) result).getBook();

        final Bitmap bitmap = ImageUtil.getHttpBitmap(HttpUtil.baseUri + book.getBookImg());

        final ColorArt colorArt = new ColorArt(bitmap);

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
                mTopBar.setBackgroundColor(colorArt.getBackgroundColor());
                String des = book.getBookDescription();
                des.replaceAll(" ", "");
                if (des.length() > 200) {
                    des = book.getBookDescription().substring(0, 200) + "...";
                }

                mDes.setText(des);
                if (book.getBookPrice() == 0) {
                    mBottImg.setImageResource(R.drawable.ic_add2shelf);
                    mBottBt.setText("加入书架");
                } else {
                    mBottImg.setImageResource(R.drawable.ic_shopcart_white);
                    mBottBt.setText("加入购物车");
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.book_detail_back_bt:
                finish();
                break;
            case R.id.book_detail_bott:
                if (userId != -1) {
                    if (mBottBt.getText().toString().equals("加入书架")) {
                        mAddBook(1);
                    } else {
                        mAddBook(0);
                    }
                } else {
                    mShowMessage("请先登录后再操作");
                }
                break;
        }
    }

    private void mAddBook(int flag) {
        bookDetailPresenter.mAddBook(userId, secretKey, mBookId, flag);
    }

    @Override
    public void mOnAddSucc(BaseModel result) {
        mDoLoad();
    }

    private void mDoLoad() {
        Looper.prepare();
        mCheckPermission();


        String fileDir = FileUtil.mGetRootPath() + FileUtil.mCachePath + book.getBookName();
        String result = "下载成功";
        if (FileUtil.mCreateCacheDir(fileDir)) {
//            System.out.println("mkdir succ");
            for (int i = 0; i < book.getBookChapterAmount(); i++) {
                Integer si = i + 1;
                String content = FileUtil.getFileContent(HttpUtil.baseUri + book.getBookLocation() + si.toString() + FileUtil.mFileType);
//                System.out.println(content);
                if (FileUtil.mSaveFile(content, fileDir + FileUtil.mFileMid + si.toString() + FileUtil.mFileType)) {
                    System.out.println(fileDir + FileUtil.mFileMid + si.toString() + FileUtil.mFileType);
                } else {
                    result = "下载失败";
                }

            }

        } else {
            result = "下载失败";
        }

        mShowMessage(result);
        Looper.loop();
    }

    @Override
    public void mOnAddFailed(String msg) {
        onActionFailed(msg);
    }


    private void mCheckPermission() {
        PackageManager pm = getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", "com.ljn.xiaoruireading"));
        if (!permission) {
            mShowMessage("未获得文件读取权限，请添加权限后重新打开此应用");
            finish();
        } else {
            mShowMessage("权限已获得");
        }
    }
}
