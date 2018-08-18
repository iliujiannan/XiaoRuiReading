package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import android.os.Looper;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.PersonalModel;
import com.ljn.xiaoruireading.model.RegModel;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/14.
 */
public class PersonalPresenter extends BasePresenter {

    public void mGetInfromation(final String secretKey, final Integer userId){
        System.out.println("***************调用前2");
        if (!isViewAttached()) {
            return;
        }
        System.out.println("***************调用前3");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                System.out.println("***************调用前4");
                PersonalModel.mDoGetInformation(secretKey, userId, new ICallback<PersonalModel>() {
                    @Override
                    public void onSuccess(PersonalModel data) {
                        System.out.println("***************调用前5");
                        if(data.getStatus()==1){
                            getView().onActionSucc(data);
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(PersonalModel data) {
                        System.out.println("***************调用前6");
                        Looper.prepare();
                        getView().onActionFailed(data);
                        Looper.loop();
                    }
                });
            }
        });

    }

    public void mLogout(Map user){

    }
    public void mAlterNickName(Map user){

    }

    public void mAlterPsw(Map user){

    }

    public void mAlterPhoto(Map user){

    }

    public void mRecharge(Map user){

    }
}
