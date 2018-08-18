package com.ljn.xiaoruireading.view.abstract_views;

import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.IBaseView;

/**
 * Created by 12390 on 2018/8/17.
 */
public interface IPersonalView extends IBaseView {
    void mLogoutSucc(BaseModel result);
    void mLogoutFailed(String msg);

    void mChangePswSucc(BaseModel result);
    void mChangePswFailed(String msg);

    void mChangeNicknameSucc(BaseModel result);
    void mChangeNicknameFailed(String msg);

    void mRechargeSucc(BaseModel result);
    void mRechargeFailed(String msg);

    void mGetMoneyInfoSucc(BaseModel result);
    void mGetMoneyInfoFailed(String msg);

}
