package com.ljn.xiaoruireading.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;

/**
 * Created by 12390 on 2018/8/9.
 */
public class RegActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mBackButton;
    private EditText mPhoneNumberEdit;
    private EditText mPswEdit;
    private EditText mRePswEdit;
    private EditText mCheckCodeEdit;
    private TextView mGetCheckCodeButton;
    private TextView mRegButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        mInitComponent();
    }
    protected void mInitComponent(){
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
                if(mPhoneNumberEdit.getText().length()==11){
                    mGetCheckCodeButton.setClickable(true);
                }
                if(mPhoneNumberEdit.getText().length()>0&&mPswEdit.getText().length()>0&&
                        mRePswEdit.getText().length()>0&&mCheckCodeEdit.getText().length()>0){
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
        switch (v.getId()){
            case R.id.reg_back_button:
                finish();
                break;
            case R.id.reg_get_check_code:
                mShowMessage("get check code");
                break;
            case R.id.reg_reg:
                mShowMessage("do reg");
                break;
        }
    }

}
