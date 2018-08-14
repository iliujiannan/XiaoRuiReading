package com.ljn.xiaoruireading.view.abstract_views;

import com.ljn.xiaoruireading.base.IBaseView;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/10.
 */
public interface IRegView extends IBaseView{
    void mOnRegSuccess(Map result);
    void mOnRegFailure(Map result);

}
