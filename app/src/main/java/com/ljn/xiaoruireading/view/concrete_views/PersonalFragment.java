package com.ljn.xiaoruireading.view.concrete_views;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;

/**
 * Created by 12390 on 2018/8/9.
 */
public class PersonalFragment extends Fragment implements View.OnClickListener{

    private TextView mPersonalLogARegButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_information,container,false);
        mInitComponent(view);
        return view;
    }

    private void mInitComponent(View view){
        mPersonalLogARegButton = (TextView) view.findViewById(R.id.personal_nickname);

        mPersonalLogARegButton.setOnClickListener(this);
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
