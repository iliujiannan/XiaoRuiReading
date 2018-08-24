package com.ljn.xiaoruireading.view.concrete_views;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.util.MyDateUtil;

import java.util.Date;

/**
 * Created by 12390 on 2018/8/8.
 */
public class HomeActivity extends BaseActivity implements View.OnClickListener{

    private TextView mTabBookshelf;
    private TextView mTabBookcity;
    private TextView mTabBookhouse;
    private TextView mTabPersonal;

    private FragmentTransaction transaction;
    private Fragment mBookcityFragment,mBookhouseFragment,mBookshelfFragment,mPersonalFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_base);

        mInitComponent();
        mSharedPreferences = getSharedPreferences(BaseActivity.SP_NAME, MODE_PRIVATE);

        requestAllPower();
        mSetStatusBar(getResources().getColor(R.color.sys_transparent));
    }

    protected void mInitComponent(){
        transaction = getFragmentManager().beginTransaction();

        mTabPersonal = (TextView)this.findViewById(R.id.txt_personal);
        mTabBookhouse = (TextView)this.findViewById(R.id.txt_article);
        mTabBookcity = (TextView)this.findViewById(R.id.txt_bookcity);
        mTabBookshelf = (TextView)this.findViewById(R.id.txt_bookshelf);

        mTabBookshelf.setSelected(true);
        mBookshelfFragment = new BookshelfFragment();
        transaction.add(R.id.fragment_container,mBookshelfFragment);
        transaction.commit();

        mTabPersonal.setOnClickListener(this);
        mTabBookhouse.setOnClickListener(this);
        mTabBookcity.setOnClickListener(this);
        mTabBookshelf.setOnClickListener(this);

    }
    public void hideAllFragment(FragmentTransaction transaction){
        if(mBookhouseFragment!=null) {
            transaction.hide(mBookhouseFragment);
        }
        if(mBookcityFragment!=null) {
            transaction.hide(mBookcityFragment);
        }
        if(mBookshelfFragment!=null){
            transaction.hide(mBookshelfFragment);
        }
        if(mPersonalFragment!=null){
            transaction.hide(mPersonalFragment);
        }



    }
    public void selected(){
        mTabBookhouse.setSelected(false);
        mTabBookcity.setSelected(false);
        mTabBookshelf.setSelected(false);
        mTabPersonal.setSelected(false);
    }
    @Override
    public void onClick(View v) {
        transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_bookshelf:
                selected();
                mTabBookshelf.setSelected(true);
                if(mBookshelfFragment==null){
                    mBookshelfFragment = new BookshelfFragment();
                    transaction.add(R.id.fragment_container,mBookshelfFragment);
                }else{
                    transaction.show(mBookshelfFragment);
                }
                ((BookshelfFragment)mBookshelfFragment).mUpdateShelf();
                break;

            case R.id.txt_bookcity:
                selected();

                mTabBookcity.setSelected(true);
                if(mBookcityFragment==null){
                    mBookcityFragment = new BookcityFragment();
                    transaction.add(R.id.fragment_container,mBookcityFragment);
                }else{
                    transaction.show(mBookcityFragment);
                }
                break;

            case R.id.txt_article:
                selected();
                mTabBookhouse.setSelected(true);
                if(mBookhouseFragment==null){
                    mBookhouseFragment = new ArticleFragment();
                    transaction.add(R.id.fragment_container,mBookhouseFragment);
                }else{
                    transaction.show(mBookhouseFragment);
                }
                break;

            case R.id.txt_personal:
                selected();
                mTabPersonal.setSelected(true);
                if(mPersonalFragment==null){
                    mPersonalFragment = new PersonalFragment();
                    transaction.add(R.id.fragment_container,mPersonalFragment);
                }else{
                    transaction.show(mPersonalFragment);
                }
                break;
        }

        transaction.commit();
    }

    public void mUpdateAllFragmentData(BaseModel result){
        ((PersonalFragment)mPersonalFragment).mUpdateData(result);
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
