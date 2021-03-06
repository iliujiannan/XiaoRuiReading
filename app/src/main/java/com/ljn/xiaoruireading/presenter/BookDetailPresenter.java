package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.ArticleDetailModel;
import com.ljn.xiaoruireading.model.BookDetailModel;
import com.ljn.xiaoruireading.view.abstract_views.IBookDetailView;


/**
 * Created by 12390 on 2018/8/20.
 */
public class BookDetailPresenter extends BasePresenter<IBookDetailView> {

    public void mGetBookDetail(final Integer bookId){
        if(!isViewAttached()){
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                BookDetailModel.mDoGetBookDetail(bookId, new ICallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
                        getView().onActionSucc(data);
                    }

                    @Override
                    public void onFailure(BaseModel data) {

                        getView().onActionFailed(data.getMsg());

                    }
                });
            }
        });
    }

    public void mAddBook(final Integer userId, final String secretKey, final Integer bookId, final int flag){
        if(!isViewAttached()){
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                ArticleDetailModel.mDoAddBook(userId, secretKey, bookId, flag, new ICallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
                        if(data.getStatus()==1) {
                            getView().mOnAddSucc(data);
                        }else {
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BaseModel data) {
                        getView().mOnAddFailed(data.getMsg());
                    }
                });
            }
        });
    }

}
