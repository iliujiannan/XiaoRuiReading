package com.ljn.xiaoruireading.presenter;

import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.IBaseView;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.BookShelfModel;

import java.util.Map;

/**
 * Created by 12390 on 2018/8/14.
 */
public class BookShelfPresenter extends BasePresenter<IBaseView> {

    public void mGetBookShelfData(Map user){
        BookShelfModel.mDoGetBookShelf(user, new ICallback<Map>() {
            @Override
            public void onSuccess(Map data) {
                getView().onActionSucc(data);
            }

            @Override
            public void onFailure(Map data) {
                getView().onActionSucc(data);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
