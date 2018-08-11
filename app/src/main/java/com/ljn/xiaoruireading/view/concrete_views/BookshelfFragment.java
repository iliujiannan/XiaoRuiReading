package com.ljn.xiaoruireading.view.concrete_views;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.util.BookShelfViewUtil;
import com.ljn.xiaoruireading.view.custom_view.ShelfAdapter;

/**
 * Created by 12390 on 2018/8/9.
 */
public class BookshelfFragment extends Fragment{

    private static final String tag = MainActivity.class.getSimpleName();

    private ShelfAdapter mMyAdapter;
    private GridView mGridView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf,container,false);
        mInitComponent(view);
        return view;
    }

    private void mInitComponent(View view){
        mMyAdapter = new ShelfAdapter(view.getContext());
        mGridView = (GridView) view.findViewById(R.id.bookshelf_grid);
        mGridView.setAdapter(mMyAdapter);
        mSetAllListener();

        updateView(view);
    }

    private void mSetAllListener(){
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Log.d(tag, "position = " + position);
                Toast.makeText(view.getContext(), "position = " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void updateView(View view) {

        String[] array = BookShelfViewUtil.listAssets(view.getContext());
        mMyAdapter.items.clear();

        for (String name : array) {
            ShelfAdapter.Item item = new ShelfAdapter.Item();
            item.filename = name;

            mMyAdapter.items.add(item);
        }

        mMyAdapter.notifyDataSetChanged();
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
