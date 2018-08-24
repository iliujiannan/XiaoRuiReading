package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.PersonalModel;
import com.ljn.xiaoruireading.view.abstract_views.IPersonalView;

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
                PersonalModel.mDoGetInformation(secretKey, userId, new ICallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
//                        System.out.println("***************调用前5");
                        if(data.getStatus()==1){
                            getView().onActionSucc(data);
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BaseModel data) {
                        getView().onActionFailed(data.getMsg());
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
                        getView().mLogoutFailed(data.getMsg());
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
                        getView().mChangeNicknameFailed(data.getMsg());
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

                        getView().mChangePswFailed(data.getMsg());

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

                            getView().mRechargeSucc(data);

                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BaseModel data) {

                        getView().mRechargeFailed(data.getMsg());

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

                        getView().mGetMoneyInfoFailed(data.getMsg());
                    }
                });
            }
        });
    }
}
