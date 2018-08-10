package com.ljn.xiaoruireading.view;

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

/**
 * Created by 12390 on 2018/8/9.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mBackButton;
    private TextView mRegButton;
    private TextView mLogButton;
    private TextView mForgetPswButton;
    private EditText mUsernameEdit;
    private EditText mPswEdit;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mInitComponent();
    }

    protected void mInitComponent(){
        mBackButton = (ImageView) findViewById(R.id.login_back_button);
        mRegButton = (TextView) findViewById(R.id.login_reg);
        mLogButton = (TextView) findViewById(R.id.login_log);
        mForgetPswButton = (TextView) findViewById(R.id.login_fgpsw_bt);
        mUsernameEdit = (EditText) findViewById(R.id.login_username);
        mPswEdit = (EditText) findViewById(R.id.login_psw);

        mBackButton.setOnClickListener(this);
        mRegButton.setOnClickListener(this);
        mLogButton.setOnClickListener(this);
        mForgetPswButton.setOnClickListener(this);

        TextWatcher mTextWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int id = R.drawable.text_login_logbt_border;
                boolean canLogin = false;
                if (mPswEdit.getText().length() > 0 && mUsernameEdit.getText().length() > 0) {
                    canLogin = true;
                    id = R.drawable.text_login_logbt_border_enable;
                }
                mLogButton.setBackground(getBaseContext().getResources().getDrawable(id));
                mLogButton.setClickable(canLogin);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mPswEdit.addTextChangedListener(mTextWatcher);
        mUsernameEdit.addTextChangedListener(mTextWatcher);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_back_button:
                finish();
                break;
            case R.id.login_reg:
                startActivity(new Intent(LoginActivity.this, RegActivity.class));
                break;
            case R.id.login_fgpsw_bt:
                mShowMessage("forget psw");
                break;
            case R.id.login_log:
                mShowMessage("doLogin");
                break;
        }
    }
}
