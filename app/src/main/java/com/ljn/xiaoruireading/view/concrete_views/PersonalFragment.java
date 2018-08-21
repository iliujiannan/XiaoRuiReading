package com.ljn.xiaoruireading.view.concrete_views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.widget.CardView;
import android.text.method.PasswordTransformationMethod;
import android.view.*;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;
import com.ljn.xiaoruireading.base.BaseFragment;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.model.PersonalModel;
import com.ljn.xiaoruireading.presenter.PersonalPresenter;
import com.ljn.xiaoruireading.util.HttpUtil;
import com.ljn.xiaoruireading.util.ImageUtil;
import com.ljn.xiaoruireading.view.abstract_views.IPersonalView;
import de.hdodenhof.circleimageview.CircleImageView;

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

    private String secretKey = "";
    private Integer userID = 0;
    private String nickName;


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

        mLogoutBt.setVisibility(View.INVISIBLE);
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

        mSharedPreferences = getActivity().getSharedPreferences(BaseActivity.SP_NAME, Context.MODE_PRIVATE);
        mUpdateDataAfterLogin();


    }

    public void mUpdateData(final BaseModel result) {

        mSharedPreferences.edit().putInt("dailyReadTime", ((PersonalModel) result).getUserData().getUserReadDailly()).commit();
        if (!secretKey.equals("")) {
            System.out.println(HttpUtil.baseUri + ((PersonalModel) result).getUserData().getUserPhoto());
            final Bitmap bm = ImageUtil.getHttpBitmap(HttpUtil.baseUri + ((PersonalModel) result).getUserData().getUserPhoto());
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nickName = ((PersonalModel) result).getUserData().getUserNickName();
                    mPersonalLogARegButton.setText(nickName);
                    mTotalReadTime.setText("累计阅读" + ((PersonalModel) result).getUserData().getUserReadTotally() + "分钟");
                    mLogoutBt.setVisibility(View.VISIBLE);

                     mHead.setImageBitmap(bm);

                }
            });


        } else {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mPersonalLogARegButton.setText("点击登录/注册");
                    mLogoutBt.setVisibility(View.INVISIBLE);

                }
            });


        }
    }

    public void mUpdateDataAfterLogin() {
//        System.out.println("***************调用前1");
        secretKey = mSharedPreferences.getString("secretKey", "");
        userID = mSharedPreferences.getInt("userId", 0);
        if (userID != 0) {
            mPersonalPresenter.mGetInfromation(secretKey, userID);
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
                if (secretKey.equals("")) {
                    startActivityForResult(new Intent(getActivity(), LoginActivity.class), 100);
                }
                break;
            case R.id.personal_logout:
                mPersonalPresenter.mLogout(userID, secretKey);
                break;
            case R.id.personal_change_psw:
                mShowDialogueChangePsw();
                break;
            case R.id.personal_channge_nickname:
                mShowDialogueChangeNickname();
                break;
            case R.id.personal_money:
                mPersonalPresenter.mGetMoneyInfo(userID, secretKey);
                break;
            case R.id.personal_recharge:
                System.out.println("******recharge");
                mShowDialogueRecharge();
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

    @Override
    public void mLogoutSucc(BaseModel result) {


        mClearUserData();
        mUpdateData(result);
    }

    private void mClearUserData() {

        secretKey = "";
        userID = 0;
        mSharedPreferences.edit().putString("secretKey", "").commit();
        mSharedPreferences.edit().putInt("userId", 0).commit();
    }

    @Override
    public void mLogoutFailed(String msg) {
        onActionFailed(msg);
    }

    @Override
    public void mChangePswSucc(BaseModel result) {
        mClearUserData();
        mUpdateData(result);
        startActivityForResult(new Intent(getActivity(), LoginActivity.class), 100);
    }

    @Override
    public void mChangePswFailed(String msg) {
        onActionFailed(msg);
    }

    @Override
    public void mChangeNicknameSucc(BaseModel result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mPersonalLogARegButton.setText(nickName);
            }
        });
        Looper.prepare();
        mShowMessage(result.getMsg());
        Looper.loop();
    }

    @Override
    public void mChangeNicknameFailed(String msg) {
        onActionFailed(msg);
    }

    @Override
    public void mRechargeSucc(BaseModel result) {
        mShowMessage(result.getMsg());
    }

    @Override
    public void mRechargeFailed(String msg) {
        onActionFailed(msg);
    }

    @Override
    public void mGetMoneyInfoSucc(final BaseModel result) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mShowDialogueMyMoney(((PersonalModel) result).getUserMoney());
            }
        });

    }

    @Override
    public void mGetMoneyInfoFailed(String msg) {
        onActionFailed(msg);
    }

    private void mShowDialogueChangePsw() {

        final EditText mEditText1 = new EditText(mContext);
        final EditText mEditText2 = new EditText(mContext);

        mEditText1.setTextSize(16);
        mEditText2.setTextSize(16);


        mEditText1.setMaxEms(16);
        mEditText2.setMaxEms(16);

        mEditText1.setSingleLine(true);
        mEditText2.setSingleLine(true);

        mEditText1.setHintTextColor(getResources().getColor(R.color.sys_font_gray));
        mEditText2.setHintTextColor(getResources().getColor(R.color.sys_font_gray));
        mEditText1.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEditText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
        mEditText1.setHint("旧密码");
        mEditText2.setHint("新密码");


        LinearLayout mLinearLayout = new LinearLayout(mContext);

        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.addView(mEditText1);
        mLinearLayout.addView(mEditText2);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String oldPsw = mEditText1.getText().toString();
                String newPsw = mEditText2.getText().toString();
                mPersonalPresenter.mAlterPsw(userID, oldPsw, newPsw);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog mInputDialog = builder.create();
        mInputDialog.setTitle("修改密码");
        mInputDialog.setView(mLinearLayout);


        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = mInputDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        lp.height = (int) (d.getHeight() * 0.3);
        lp.width = (int) getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(lp);

        mInputDialog.show();
    }

    private void mShowDialogueChangeNickname() {

        final EditText mEditText1 = new EditText(mContext);
        mEditText1.setTextSize(16);
        mEditText1.setMaxEms(20);
        mEditText1.setSingleLine(true);
        mEditText1.setHintTextColor(getResources().getColor(R.color.sys_font_gray));
        mEditText1.setHint("新昵称");
        LinearLayout mLinearLayout = new LinearLayout(mContext);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.addView(mEditText1);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nickName = mEditText1.getText().toString();
                mPersonalPresenter.mAlterNickName(userID, secretKey, nickName);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog mInputDialog = builder.create();
        mInputDialog.setTitle("修改昵称");
        mInputDialog.setView(mLinearLayout);


        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = mInputDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        lp.height = (int) (d.getHeight() * 0.3);
        lp.width = (int) getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(lp);

        mInputDialog.show();
    }

    private void mShowDialogueRecharge() {
        final EditText mEditText1 = new EditText(mContext);
        mEditText1.setTextSize(16);
        mEditText1.setMaxEms(20);
        mEditText1.setSingleLine(true);
        mEditText1.setHintTextColor(getResources().getColor(R.color.sys_font_gray));
        mEditText1.setHint("充值金额");
        LinearLayout mLinearLayout = new LinearLayout(mContext);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.addView(mEditText1);

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Integer money = Integer.valueOf(mEditText1.getText().toString());
                mPersonalPresenter.mRecharge(userID, secretKey, money);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog mInputDialog = builder.create();
        mInputDialog.setTitle("书币充值");
        mInputDialog.setView(mLinearLayout);


        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = mInputDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        lp.height = (int) (d.getHeight() * 0.3);
        lp.width = (int) getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(lp);

        mInputDialog.show();


    }

    private void mShowDialogueMyMoney(Integer money) {
        TextView textView = new TextView(mContext);
        textView.setText("当前拥有" + money + "书币");
        textView.setGravity(Gravity.CENTER);
        LinearLayout mLinearLayout = new LinearLayout(mContext);
        mLinearLayout.setOrientation(LinearLayout.VERTICAL);
        mLinearLayout.addView(textView);
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        AlertDialog mInputDialog = builder.create();
        mInputDialog.setView(mLinearLayout);

        mInputDialog.setTitle("我的书币");

        WindowManager m = getActivity().getWindowManager();
        Display d = m.getDefaultDisplay();
        Window window = mInputDialog.getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        window.setGravity(Gravity.CENTER);
        lp.height = (int) (d.getHeight() * 0.3);
        lp.width = (int) getResources().getDisplayMetrics().widthPixels;
        window.setAttributes(lp);

        mInputDialog.show();
    }


}
