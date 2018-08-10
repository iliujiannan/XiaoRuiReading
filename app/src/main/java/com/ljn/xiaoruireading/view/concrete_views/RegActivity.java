package com.ljn.xiaoruireading.view.concrete_views;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;
import com.ljn.xiaoruireading.presenter.RegPresenter;
import com.ljn.xiaoruireading.view.abstract_views.IRegView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 12390 on 2018/8/9.
 */
public class RegActivity extends BaseActivity implements View.OnClickListener, IRegView {
    private ImageView mBackButton;
    private EditText mPhoneNumberEdit;
    private EditText mPswEdit;
    private EditText mRePswEdit;
    private EditText mCheckCodeEdit;
    private TextView mGetCheckCodeButton;
    private TextView mRegButton;

    RegPresenter mRegPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        mInitComponent();

        mRegPresenter = new RegPresenter();
        mRegPresenter.attachView(this);
    }

    protected void mInitComponent() {
        mBackButton = (ImageView) findViewById(R.id.reg_back_button);
        mPhoneNumberEdit = (EditText) findViewById(R.id.reg_username);
        mPswEdit = (EditText) findViewById(R.id.reg_psw);
        mRePswEdit = (EditText) findViewById(R.id.reg_repsw);
        mCheckCodeEdit = (EditText) findViewById(R.id.reg_check_code);
        mGetCheckCodeButton = (TextView) findViewById(R.id.reg_get_check_code);
        mRegButton = (TextView) findViewById(R.id.reg_reg);


        TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int id = R.drawable.text_login_logbt_border;
                boolean canReg = false;
                if (mPhoneNumberEdit.getText().length() == 11) {
                    mGetCheckCodeButton.setClickable(true);
                } else {
                    mGetCheckCodeButton.setClickable(false);
                }
                if (mPhoneNumberEdit.getText().length() > 0 && mPswEdit.getText().length() > 0 &&
                        mRePswEdit.getText().length() > 0 && mCheckCodeEdit.getText().length() > 0) {
                    id = R.drawable.text_login_logbt_border_enable;
                    canReg = true;
                }
                mRegButton.setClickable(canReg);
                mRegButton.setBackground(getBaseContext().getResources().getDrawable(id));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        mGetCheckCodeButton.setClickable(false);
        mRegButton.setClickable(false);

        mBackButton.setOnClickListener(this);
        mPhoneNumberEdit.addTextChangedListener(mTextWatcher);
        mPswEdit.addTextChangedListener(mTextWatcher);
        mRePswEdit.addTextChangedListener(mTextWatcher);
        mCheckCodeEdit.addTextChangedListener(mTextWatcher);
        mGetCheckCodeButton.setOnClickListener(this);
        mRegButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.isClickable()) {

            switch (v.getId()) {
                case R.id.reg_back_button:
                    finish();
                    break;
                case R.id.reg_get_check_code:
                    mShowMessage("get check code");
                    break;
                case R.id.reg_reg:
//                mShowMessage("do reg");
                    Map user = new HashMap<String, String>();
                    user.put("phone", mPhoneNumberEdit.getText().toString());
                    user.put("psw", mPswEdit.getText().toString());
                    user.put("repsw", mRePswEdit.getText().toString());
                    user.put("checkcode", mCheckCodeEdit.getText().toString());
                    mRegPresenter.registe(user);
                    break;
            }
        }

    }

    @Override
    public void mOnRegSuccess() {
        startActivity(new Intent(RegActivity.this, HomeActivity.class));
        finish();
    }

    @Override
    public void mOnRegFailure() {

    }
}
