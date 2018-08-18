package com.ljn.xiaoruireading.view.concrete_views;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;
import com.ljn.xiaoruireading.base.BaseFragment;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.IBaseView;
import com.ljn.xiaoruireading.model.PersonalModel;
import com.ljn.xiaoruireading.presenter.PersonalPresenter;
import com.ljn.xiaoruireading.view.abstract_views.IPersonalView;
import de.hdodenhof.circleimageview.CircleImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by 12390 on 2018/8/9.
 */
public class PersonalFragment extends BaseFragment implements View.OnClickListener, IPersonalView {

    private TextView mPersonalLogARegButton;
    private CircleImageView mHead;
    private TextView mTotalReadTime;
    private CardView mRechargeBt;
    private CardView mChangePswBt;
    private CardView mChangeNicknameBt;
    private CardView mLogoutBt;
    private RelativeLayout mShopcartBt;
    private RelativeLayout mOrderBt;
    private RelativeLayout mMoneyBt;
    private PersonalPresenter mPersonalPresenter;

    @Override
    public int mGetContentViewId() {
        return R.layout.fragment_personal_information;
    }

    @Override
    protected void mInitAllMembersView(View mRootView) {
        mPersonalLogARegButton = (TextView) mRootView.findViewById(R.id.personal_nickname);
        mHead = (CircleImageView) mRootView.findViewById(R.id.personal_hp);
        mTotalReadTime = (TextView) mRootView.findViewById(R.id.personal_reading_time);
        mRechargeBt = (CardView) mRootView.findViewById(R.id.personal_recharge);
        mChangePswBt = (CardView) mRootView.findViewById(R.id.personal_change_psw);
        mChangeNicknameBt = (CardView) mRootView.findViewById(R.id.personal_channge_nickname);
        mLogoutBt = (CardView) mRootView.findViewById(R.id.personal_logout);
        mShopcartBt = (RelativeLayout) mRootView.findViewById(R.id.personal_shopcart);
        mOrderBt = (RelativeLayout) mRootView.findViewById(R.id.personal_order);
        mMoneyBt = (RelativeLayout) mRootView.findViewById(R.id.personal_money);
        mMoneyBt.setOnClickListener(this);
        mOrderBt.setOnClickListener(this);
        mShopcartBt.setOnClickListener(this);
        mLogoutBt.setOnClickListener(this);
        mChangeNicknameBt.setOnClickListener(this);
        mChangePswBt.setOnClickListener(this);
        mRechargeBt.setOnClickListener(this);
        mTotalReadTime.setOnClickListener(this);
        mPersonalLogARegButton.setOnClickListener(this);
        mHead.setOnClickListener(this);

        mUpdateDataAfterLogin();


    }

    public void mUpdateData(final BaseModel result) {
        String secretKey = MainActivity.mSharedPreferences.getString("secretKey", "");
        if (!secretKey.equals("")) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPersonalLogARegButton.setText(((PersonalModel) result).getUserData().getUserNickName());

                    URL url;
                    try {
                        url = new URL(((PersonalModel) result).getUserData().getUserPhoto());
//                        Bitmap pngBM = BitmapFactory.decodeStream(url.openStream());
//                        mHead.setImageBitmap(pngBM);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            });


        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPersonalLogARegButton.setText("点击登录/注册");
                }
            });

        }
    }

    public void mUpdateDataAfterLogin() {
//        System.out.println("***************调用前1");
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(BaseActivity.SP_NAME, Context.MODE_PRIVATE);
        String secretKey = sharedPreferences.getString("secretKey", "");
        Integer userId = sharedPreferences.getInt("userId", 0);
        if (userId != 0) {
            mPersonalPresenter.mGetInfromation(secretKey, userId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mPersonalPresenter = new PersonalPresenter();
        mPersonalPresenter.attachView(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personal_nickname:
                if (MainActivity.mSharedPreferences.getString("secretKey", "").equals("")) {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), 100);
                }
                break;
        }
    }

    @Override
    public void onActionSucc(BaseModel result) {
        ((HomeActivity) getActivity()).mUpdateAllFragmentData(result);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        System.out.println("***********回来了 resc:"+resultCode+"requ:"+requestCode);
        if (resultCode == 1) {
//            System.out.println("***************调用前");
            mUpdateDataAfterLogin();
        }
    }
}
