package com.ljn.xiaoruireading.view.abstract_views;

import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.IBaseView;

/**
 * Created by 12390 on 2018/8/21.
 */
public interface IBookShelfView extends IBaseView {

    void mOnDelSucc(BaseModel result);

    void mOnDelFailed(String msg);
}
