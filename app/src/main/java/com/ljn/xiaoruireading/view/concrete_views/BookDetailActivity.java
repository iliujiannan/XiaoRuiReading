package com.ljn.xiaoruireading.view.concrete_views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;

/**
 * Created by 12390 on 2018/8/18.
 */
public class BookDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        mInitComponent();
    }

    @Override
    protected void mInitComponent() {

    }
}
