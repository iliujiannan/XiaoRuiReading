package com.ljn.xiaoruireading.presenter;

import android.os.Handler;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.BasePresenter;
import com.ljn.xiaoruireading.base.IBaseView;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.model.BookCityModel;

/**
 * Created by 12390 on 2018/8/18.
 */
public class BookCityPresenter extends BasePresenter<IBaseView> {
    public void mGetBooks(final String label){
        if (!isViewAttached()) {
            return;
        }

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                BookCityModel.mDoGetBooks(label, new ICallback<BaseModel>() {
                    @Override
                    public void onSuccess(BaseModel data) {

                        if(data.getStatus()==1) {
                            getView().onActionSucc(data);
                        }else{
                            onFailure(data);
                        }

                    }

                    @Override
                    public void onFailure(BaseModel data) {
                        getView().onActionFailed(data.getMsg());
                    }
                });
            }
        });
    }
}
