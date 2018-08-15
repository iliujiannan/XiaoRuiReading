package com.ljn.xiaoruireading.base;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/14.
 */
public abstract class BaseFragment extends Fragment implements IBaseView{
    public abstract int mGetContentViewId();
    protected abstract void mInitAllMembersView(View mRootView);
    protected Context mContext;
    protected LayoutInflater mInflater;
    protected View mRootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(mGetContentViewId(), container, false);
        this.mContext = getActivity();
        this.mInflater = inflater;
        mInitAllMembersView(mRootView);
        return mRootView;
    }
    @Override
    public void showLoading() {
        checkActivityAttached();
        ((BaseActivity) mContext).showLoading();
    }
    @Override
    public void hideLoading() {
        checkActivityAttached();
        ((BaseActivity) mContext).hideLoading();
    }

    @Override
    public void showErr() {
        checkActivityAttached();
        ((BaseActivity) mContext).showErr();
    }
    protected boolean isAttachedContext(){
        return getActivity() != null;
    }
    /**
     * 检查activity连接情况
     */
    public void checkActivityAttached() {
        if (getActivity() == null) {
            throw new ActivityNotAttachedException();
        }
    }
    public static class ActivityNotAttachedException extends RuntimeException {
        public ActivityNotAttachedException() {
            super("Fragment has disconnected from Activity ! - -.");
        }
    }

    @Override
    public void onActionSucc(Map result) {

    }

    @Override
    public void onActionFailed(Map result) {

    }
    protected void mShowMessage(String msg){
        Toast.makeText(getActivity(), msg,
                Toast.LENGTH_SHORT).show();
    }
}
