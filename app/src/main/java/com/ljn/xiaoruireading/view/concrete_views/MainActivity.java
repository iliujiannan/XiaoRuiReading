package com.ljn.xiaoruireading.view.concrete_views;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;

/**
 * Created by 12390 on 2018/8/9.
 */
public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_main);
        requestAllPower();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                mSharedPreferences = getSharedPreferences(SP_NAME, MODE_PRIVATE);

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

    public void requestAllPower() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

}
