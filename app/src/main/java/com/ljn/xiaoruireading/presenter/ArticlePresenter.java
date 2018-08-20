package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import android.os.Looper;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.IBaseView;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.ArticleModel;
import com.ljn.xiaoruireading.model.BookCityModel;

/**
 * Created by 12390 on 2018/8/20.
 */
public class ArticlePresenter extends BasePresenter<IBaseView> {

    public void mGetArticles(){
        if (!isViewAttached()) {
            return;
        }

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ArticleModel.mDoGetArticles(new ICallback<ArticleModel>() {
                    @Override
                    public void onSuccess(ArticleModel data) {
                        Looper.prepare();
                        if(data.getStatus()==1) {
                            getView().onActionSucc(data);
                        }else{
                            onFailure(data);
                        }
                        Looper.loop();
                    }

                    @Override
                    public void onFailure(ArticleModel data) {
                        getView().onActionFailed(data.getMsg());
                    }

                });
            }
        });
    }
}
