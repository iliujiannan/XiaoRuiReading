package com.ljn.xiaoruireading.base;

import android.content.Context;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/10.
 */
public interface IBaseView {
    /**
     * 显示正在加载view
     */
    void showLoading();
    /**
     * 关闭正在加载view
     */
    void hideLoading();
    /**
     * 显示请求错误提示
     */
    void showErr();
    /**
     * 获取上下文
     * @return 上下文
     */

    void onActionSucc(BaseModel result);
    void onActionFailed(String msg);
    
    Context getContext();


}
