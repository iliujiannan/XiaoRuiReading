package com.ljn.xiaoruireading.view.concrete_views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseFragment;

/**
 * Created by 12390 on 2018/8/9.
 */
public class PersonalFragment extends BaseFragment implements View.OnClickListener{

    private TextView mPersonalLogARegButton;

    @Override
    public int mGetContentViewId() {
        return R.layout.fragment_personal_information;
    }

    @Override
    protected void mInitAllMembersView(View mRootView) {
        mPersonalLogARegButton = (TextView) mRootView.findViewById(R.id.personal_nickname);

        mPersonalLogARegButton.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personal_nickname:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
        }
    }
}
