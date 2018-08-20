package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import android.os.Looper;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.IBaseView;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.BookDetailModel;


/**
 * Created by 12390 on 2018/8/20.
 */
public class BookDetailPresenter extends BasePresenter<IBaseView> {

    public void mGetBookDetail(final Integer bookId){
        if(!isViewAttached()){
            return;
        }
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                BookDetailModel.mDoGetBookDetail(bookId, new ICallback<BookDetailModel>() {
                    @Override
                    public void onSuccess(BookDetailModel data) {
                        getView().onActionSucc(data);
                    }

                    @Override
                    public void onFailure(BookDetailModel data) {
                        Looper.prepare();
                        getView().onActionFailed(data.getMsg());
                        Looper.loop();
                    }
                });
            }
        });
    }
}
