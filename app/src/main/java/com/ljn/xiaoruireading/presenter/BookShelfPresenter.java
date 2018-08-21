package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import android.os.Looper;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.BookShelfModel;
import com.ljn.xiaoruireading.view.abstract_views.IBookShelfView;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/14.
 */
public class BookShelfPresenter extends BasePresenter<IBookShelfView> {

    public void mGetBookShelfData(final Integer userId, final String secretKey){
        if(!isViewAttached()){
            return;
        }

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                BookShelfModel.mDoGetShelfData(userId, secretKey, new ICallback<BookShelfModel>() {
                    @Override
                    public void onSuccess(BookShelfModel data) {
                        if(data.getStatus()==1){
                            getView().onActionSucc(data);
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BookShelfModel data) {
                        Looper.prepare();
                        getView().onActionFailed(data.getMsg());
                        Looper.loop();
                    }
                });
            }
        });
    }

    public void mSetReadTime(final Integer userId, final String secretKey, final Integer readTime, final Integer bookId){
        if(!isViewAttached()){
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                BookShelfModel.mDoSetReadTime(userId, secretKey, readTime, bookId, new ICallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {

                        if(data.getStatus()==1) {
                            Looper.prepare();
                            getView().mOnSetSucc(data);
                            Looper.loop();
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BaseModel data) {
                        Looper.prepare();
                        getView().onActionFailed(data.getMsg());
                        Looper.loop();
                    }
                });
            }
        });
    }

    public void mDelBook(final Integer userId, final String secretKey, final Integer bookId){
        if(!isViewAttached()){
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                BookShelfModel.mDoDelBook(userId, secretKey, bookId, new ICallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {
                        if(data.getStatus()==1) {
                            getView().mOnDelSucc(data);
                        }else{
                            onFailure(data);
                        }
                    }

                    @Override
                    public void onFailure(BaseModel data) {
                        Looper.prepare();
                        getView().onActionFailed(data.getMsg());
                        Looper.loop();
                    }
                });
            }
        });
    }

}
