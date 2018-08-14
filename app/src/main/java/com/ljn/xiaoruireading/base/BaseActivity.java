package com.ljn.xiaoruireading.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.view.concrete_views.FinishListActivity;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/10.
 */
public abstract class BaseActivity extends AppCompatActivity implements IBaseView{
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //透明状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        FinishListActivity.getInstance().addActivity(this);

        //初始化加载logo
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
    }
    protected void mShowMessage(String msg){
        Toast.makeText(getApplicationContext(), msg,
                Toast.LENGTH_SHORT).show();
    }


    protected abstract void mInitComponent();

    @Override
    public void showLoading() {
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }
    @Override
    public void hideLoading() {
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    @Override
    public void showErr() {
        mShowMessage(getResources().getString(R.string.app_err_msg));
    }
    @Override
    public Context getContext() {
        return BaseActivity.this;
    }


    @Override
    public void onActionSucc(Map result) {

    }

    @Override
    public void onActionFailed(Map result) {

    }
}
