package com.ljn.xiaoruireading.view.concrete_views;

import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseFragment;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.view.custom_view.bookshelf.BookShelfViewUtil;
import com.ljn.xiaoruireading.view.custom_view.bookshelf.ShelfAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 12390 on 2018/8/9.
 */
public class BookshelfFragment extends BaseFragment {

    private ShelfAdapter mMyAdapter;
    private GridView mGridView;
    private TextView mReadTime;
    private TextView mSaying;
    private TextView mSayingAuthor;

    private boolean mIsLongPress = false;


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

        mGridView.setAdapter(mMyAdapter);
        mSetAllListener();

        updateView(view);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void mSetAllListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if(!mIsLongPress) {
                    Intent intent = new Intent(getActivity(), ReaderActivity.class);
                    intent.putExtra("book_id", position);
                    intent.putExtra("uri_type", 0);
                    startActivity(intent);
                    //update
                }else{
                    mIsLongPress = false;
                }
            }
        });
        mGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                //doDelete
                mShowMessage("item " + position + "被长按");
                mIsLongPress = true;
                return false;
            }
        });
    }


    private void updateView(View view) {

        String[] array = BookShelfViewUtil.listAssets(view.getContext());
        mMyAdapter.items.clear();
        List<String> realList = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            String temp = array[i];
            if (mIsLegle(temp)) {
                realList.add(temp);
            }
        }
        for (String name : realList) {
            ShelfAdapter.Item item = new ShelfAdapter.Item();
            item.filename = name;

            mMyAdapter.items.add(item);
        }

        mMyAdapter.notifyDataSetChanged();
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



}
