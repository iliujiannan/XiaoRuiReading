package com.ljn.xiaoruireading.view.concrete_views;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseFragment;

/**
 * Created by 12390 on 2018/8/9.
 */
public class BookhouseFragment extends BaseFragment{
    @Override
    public int mGetContentViewId() {
        return R.layout.fragment_bookhouse;
    }

    @Override
    protected void mInitAllMembersView(View mRootView) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
