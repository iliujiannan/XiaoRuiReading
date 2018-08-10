package com.ljn.xiaoruireading.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.ljn.xiaoruireading.R;

/**
 * Created by 12390 on 2018/8/9.
 */
public class MainActivity extends BaseActivity {
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_main);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                mSharedPreferences = getSharedPreferences("xrreading", MODE_PRIVATE);

                if (mSharedPreferences.getBoolean("isFirst", true)) {
                    mSharedPreferences.edit().putBoolean("isFirst", false).commit();
                    startActivity(new Intent(MainActivity.this, FirstWelcomActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }
            }
        }, 3000);


    }

    @Override
    protected void mInitComponent() {

    }
}
