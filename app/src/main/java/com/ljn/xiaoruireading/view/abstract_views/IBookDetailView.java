package com.ljn.xiaoruireading.view.abstract_views;

import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.IBaseView;

/**
 * Created by 12390 on 2018/8/20.
 */
public interface IBookDetailView extends IBaseView {
    void mOnAddSucc(BaseModel result);

    void mOnAddFailed(String msg);
}
