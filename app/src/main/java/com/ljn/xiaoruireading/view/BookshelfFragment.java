package com.ljn.xiaoruireading.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ljn.xiaoruireading.R;

/**
 * Created by 12390 on 2018/8/9.
 */
public class BookshelfFragment extends Fragment{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookshelf,container,false);
        return view;
    }
}
