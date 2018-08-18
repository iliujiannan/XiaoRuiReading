package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import android.os.Looper;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.PersonalModel;
import com.ljn.xiaoruireading.model.RegModel;
import com.ljn.xiaoruireading.view.abstract_views.IPersonalView;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/14.
 */
public class PersonalPresenter extends BasePresenter<IPersonalView> {

    public void mGetInfromation(final String secretKey, final Integer userId){
//        System.out.println("***************调用前2");
        if (!isViewAttached()) {
            return;
        }
//        System.out.println("***************调用前3");
        new Handler().post(new Runnable() {
            @Override
            public void run() {
//                System.out.println("***************调用前4");
                PersonalModel.mDoGetInformation(secretKey, userId, new ICallback<PersonalModel>() {
                    @Override
                    public void onSuccess(PersonalModel data) {
//                        System.out.println("***************调用前5");
                        if(data.getStatus()==1){
                            getView().onActionSucc(data);
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(PersonalModel data) {
                        Looper.prepare();
                        getView().onActionFailed(data.getMsg());
                        Looper.loop();
                    }
                });
            }
        });

    }

    public void mLogout(final Integer userId, final String secretKey){
        if (!isViewAttached()) {
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                PersonalModel.mDoLogout(userId, secretKey, new ICallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
                        if(data.getStatus()==1){
                            getView().mLogoutSucc(data);
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BaseModel data) {
                        Looper.prepare();
                        getView().mLogoutFailed(data.getMsg());
                        Looper.loop();
                    }
                });
            }
        });
    }
    public void mAlterNickName(final Integer userId, final String secretKey, final String newNickname){
        if (!isViewAttached()) {
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                PersonalModel.mDoChangeNickname(userId, secretKey, newNickname, new ICallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
                        if(data.getStatus()==1){
                            getView().mChangeNicknameSucc(data);
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BaseModel data) {
                        Looper.prepare();
                        getView().mChangeNicknameFailed(data.getMsg());
                        Looper.loop();
                    }
                });
            }
        });
    }

    public void mAlterPsw(final Integer userId, final String oldPsw, final String newPsw){
        if (!isViewAttached()) {
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                PersonalModel.mDoChangePsw(userId, oldPsw, newPsw, new ICallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
                        if(data.getStatus()==1){
                            getView().mChangePswSucc(data);
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BaseModel data) {
                        Looper.prepare();
                        getView().mChangePswFailed(data.getMsg());
                        Looper.loop();
                    }
                });
            }
        });
    }


    public void mRecharge(final Integer userId, final String secretKey, final Integer money){
        if (!isViewAttached()) {
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                PersonalModel.mDoRecharge(userId, secretKey, money, new ICallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
                        if(data.getStatus()==1){
                            Looper.prepare();
                            getView().mRechargeSucc(data);
                            Looper.loop();
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BaseModel data) {
                        Looper.prepare();
                        getView().mRechargeFailed(data.getMsg());
                        Looper.loop();
                    }
                });
            }
        });
    }
    public void mGetMoneyInfo(final Integer userId, final String secretKey){
        if (!isViewAttached()) {
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                PersonalModel.mDoGetMoneyInfo(userId, secretKey, new ICallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
                        if(data.getStatus()==1){
                            getView().mGetMoneyInfoSucc(data);
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BaseModel data) {
                        Looper.prepare();
                        getView().mGetMoneyInfoFailed(data.getMsg());
                        Looper.loop();
                    }
                });
            }
        });
    }
}
