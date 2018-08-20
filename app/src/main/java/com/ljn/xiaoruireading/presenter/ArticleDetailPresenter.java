package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import android.os.Looper;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.IBaseView;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.ArticleDetailModel;

/**
 * Created by 12390 on 2018/8/20.
 */
public class ArticleDetailPresenter extends BasePresenter<IBaseView>{
    public void mGetArticleDetail(final Integer articleId){
        if(!isViewAttached()){
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ArticleDetailModel.mDoGetArticleDetail(articleId, new ICallback<ArticleDetailModel>() {
                    @Override
                    public void onSuccess(ArticleDetailModel data) {
                        getView().onActionSucc(data);
                    }

                    @Override
                    public void onFailure(ArticleDetailModel data) {
                        Looper.prepare();
                        getView().onActionFailed(data.getMsg());
                        Looper.loop();
                    }
                });
            }
        });
    }
}
