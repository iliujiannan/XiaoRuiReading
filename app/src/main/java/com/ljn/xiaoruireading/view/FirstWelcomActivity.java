package com.ljn.xiaoruireading.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;

public class FirstWelcomActivity extends BaseActivity implements View.OnClickListener {
    private TextView mLoginButton = null;
    private TextView mRegButton = null;
    private TextView mVisitButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_welcome);
        mInitComponent();

    }

    protected void mInitComponent(){
        mLoginButton = (TextView) findViewById(R.id.first_welcome_login_button);
        mRegButton = (TextView) findViewById(R.id.first_welcome_reg_button);
        mVisitButton = (TextView) findViewById(R.id.first_welcome_visit_button);
        mSetAllListener();
    }
    private void mSetAllListener(){
        mRegButton.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mVisitButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.first_welcome_login_button:
                startActivity(new Intent(FirstWelcomActivity.this,LoginActivity.class));
                break;
            case R.id.first_welcome_reg_button:
//                mShowMessage("reg");
                startActivity(new Intent(FirstWelcomActivity.this,RegActivity.class));
                break;
            case R.id.first_welcome_visit_button:
                startActivity(new Intent(FirstWelcomActivity.this,HomeActivity.class));
                finish();
                break;
        }
    }
}
