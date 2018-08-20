package com.ljn.xiaoruireading.view.concrete_views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;

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
        Intent intent;
        switch (v.getId()){
            case R.id.first_welcome_login_button:
                intent = new Intent(FirstWelcomActivity.this,LoginActivity.class);
                intent.putExtra("from", "wel");
                startActivity(intent);
                break;
            case R.id.first_welcome_reg_button:
//                mShowMessage("reg");
                intent = new Intent(FirstWelcomActivity.this,RegActivity.class);
                intent.putExtra("from", "wel");
                startActivity(intent);
                break;
            case R.id.first_welcome_visit_button:
                startActivity(new Intent(FirstWelcomActivity.this,HomeActivity.class));
                break;
        }
        finish();
    }

}
