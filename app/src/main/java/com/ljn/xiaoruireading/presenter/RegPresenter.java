package com.ljn.xiaoruireading.presenter;

import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.RegModel;
import com.ljn.xiaoruireading.util.PhoneNumberCheckUtil;
import com.ljn.xiaoruireading.view.abstract_views.IRegView;

import java.util.List;

/**
 * Created by 12390 on 2018/8/10.
 */
public class RegPresenter extends BasePresenter<IRegView> {


    public void mRegiste(List<String> keys, final List<String> values){
        if (!isViewAttached()) {
            return;
        }
        if(values.get(1).equals(values.get(2))){
            RegModel.mDoReg(keys,values, new ICallback<BaseModel>() {
                @Override
                public void onSuccess(BaseModel data) {
                    if(data.getStatus()==1){
                        getView().mOnRegSuccess(data);
                    }else{
                        onFailure(data);
                    }
                }

                @Override
                public void onFailure(BaseModel data) {
                    getView().onActionFailed(data.getMsg());
                }

            });
        }else{

            getView().onActionFailed("密码不一致");
        }

    }

    public void mGetCheckCode(String phone){
        if (!isViewAttached()) {
            return;
        }
        if(PhoneNumberCheckUtil.isMobiPhoneNum(phone)){
            RegModel.mDoGetCheckCode(phone, new ICallback<BaseModel>() {
                @Override
                public void onSuccess(BaseModel data) {
                    if(data.getStatus()==1) {
//                        System.out.println("还没爆**********111");

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
        }else{

            getView().onActionFailed("请检查手机号");
        }

    }
}
