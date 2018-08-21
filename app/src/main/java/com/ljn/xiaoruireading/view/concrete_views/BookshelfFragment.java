package com.ljn.xiaoruireading.view.concrete_views;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;
import com.ljn.xiaoruireading.base.BaseFragment;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.model.Book;
import com.ljn.xiaoruireading.model.BookShelfModel;
import com.ljn.xiaoruireading.presenter.BookShelfPresenter;
import com.ljn.xiaoruireading.util.HttpUtil;
import com.ljn.xiaoruireading.util.ImageUtil;
import com.ljn.xiaoruireading.util.MyDateUtil;
import com.ljn.xiaoruireading.view.abstract_views.IBookShelfView;
import com.ljn.xiaoruireading.view.custom_view.bookshelf.BookShelfViewUtil;
import com.ljn.xiaoruireading.view.custom_view.bookshelf.ShelfAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 12390 on 2018/8/9.
 */
public class BookshelfFragment extends BaseFragment implements IBookShelfView {

    private ShelfAdapter mMyAdapter;
    private GridView mGridView;
    private TextView mReadTime;

    private TextView mSaying;
    private TextView mSayingAuthor;

    private List<Book> books;

    private BookShelfPresenter bookShelfPresenter;
    private boolean mIsLongPress = false;

    private Integer userId = 0;
    private String secretKey = "";
    private Integer dailyReadTime = 0;

    private String beginTime;

    private Integer lastBookId;


    @Override
    public int mGetContentViewId() {
        return R.layout.fragment_bookshelf;
    }

    @Override
    protected void mInitAllMembersView(View view) {
        mMyAdapter = new ShelfAdapter(view.getContext());
        mGridView = (GridView) view.findViewById(R.id.bookshelf_grid);
        mReadTime = (TextView) view.findViewById(R.id.bookshelf_top_text_minute);
        mSaying = (TextView) view.findViewById(R.id.bookshelf_saying);
        mSayingAuthor = (TextView) view.findViewById(R.id.bookshelf_saying_author);

        mReadTime.setText(dailyReadTime.toString());

        mGridView.setAdapter(mMyAdapter);
        mSetAllListener();
        String[] array = BookShelfViewUtil.listAssets(view.getContext());
        List<String> names = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            names.add(array[i]);
        }
        updateView(true, names);

        mUpdateShelf();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        bookShelfPresenter = new BookShelfPresenter();
        bookShelfPresenter.attachView(this);
        mSharedPreferences = getActivity().getSharedPreferences(BaseActivity.SP_NAME, getActivity().MODE_PRIVATE);
        updateUserData();


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void updateUserData() {
        userId = mSharedPreferences.getInt("userId", 0);
        secretKey = mSharedPreferences.getString("secretKey", "");
        dailyReadTime = mSharedPreferences.getInt("dailyReadTime", 0);

    }

    private void mSetAllListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (!mIsLongPress) {
                    Intent intent = new Intent(getActivity(), ReaderActivity.class);
                    if (books != null) {
                        lastBookId = books.get(position).getBookId();
                        intent.putExtra("bookId", books.get(position).getBookId());
                        intent.putExtra("bookName", books.get(position).getBookName());
                        intent.putExtra("bookCap", books.get(position).getBookChapterAmount());
                        beginTime = MyDateUtil.dateToString(new Date());
                    }
                    startActivityForResult(intent, 998);
                    //update
                } else {
                    mIsLongPress = false;
                }
            }
        });
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //doDelete
                final int pos = position;
                AlertDialog.Builder delDialog = new AlertDialog.Builder(mContext);
                delDialog.setNeutralButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        bookShelfPresenter.mDelBook(userId, secretKey, books.get(pos).getBookId());
                    }
                }).show();
                mIsLongPress = true;
                return false;
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(!secretKey.equals("")) {
            long time = MyDateUtil.getDistanceTimeInMinute(beginTime, MyDateUtil.dateToString(new Date()));
            Integer readtime = Integer.valueOf((int) time);
            dailyReadTime +=readtime;
            mSharedPreferences.edit().putInt("dailyReadTime", dailyReadTime);
            bookShelfPresenter.mSetReadTime(userId, secretKey, readtime, lastBookId);
        }
        mUpdateShelf();
    }

    private void updateView(boolean isFirst, List<String> bookNames) {


        mMyAdapter.items.clear();
        List<String> realList = new ArrayList<>();
        for (int i = 0; i < bookNames.size(); i++) {
            String temp = bookNames.get(i);
            if (isFirst) {
                if (mIsLegle(temp)) {
                    realList.add(temp);
                }
            } else {
                realList.add(temp);
            }
        }
        List<Bitmap> bitmaps = new ArrayList<>();
        for (String name : realList) {
            ShelfAdapter.Item item = new ShelfAdapter.Item();
            item.filename = name;
            mMyAdapter.items.add(item);
            if (!isFirst) {
                Bitmap bitmap = ImageUtil.getHttpBitmap(HttpUtil.baseUri + name);
                bitmaps.add(bitmap);
            }
        }
        if (!isFirst) {
            mMyAdapter.setFirst(false);
            mMyAdapter.setBitmapList(bitmaps);
        }
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mMyAdapter.notifyDataSetChanged();
            }
        });

    }


    public void mUpdateShelf() {
        userId = mSharedPreferences.getInt("userId", 0);
        secretKey = mSharedPreferences.getString("secretKey", "");
        dailyReadTime = mSharedPreferences.getInt("dailyReadTime", 0);
        if (!secretKey.equals("")) {
            bookShelfPresenter.mGetBookShelfData(userId, secretKey);
        }
    }

    private boolean mIsLegle(String temp) {
        if (temp.contains("shelf")) {
            return true;
        }
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bookshelf, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void mOnDelSucc(BaseModel result) {
        Looper.prepare();
        mShowMessage("成功");
        Looper.loop();
        mUpdateShelf();
    }

    @Override
    public void mOnDelFailed(String msg) {
        onActionFailed(msg);
    }

    @Override
    public void mOnSetSucc(BaseModel result) {
        onActionFailed(result.getMsg());
    }

    @Override
    public void mOnSetFailed(String msg) {
        onActionFailed(msg);
    }

    @Override
    public void onActionSucc(BaseModel result) {
        books = ((BookShelfModel) result).getBooks();
        List<String> names = new ArrayList<>();
        for (Book b : books) {
            names.add(b.getBookImg());
        }
        updateView(false, names);
    }
}
