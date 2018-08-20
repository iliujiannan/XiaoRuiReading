package com.ljn.xiaoruireading.model;

import com.google.gson.Gson;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.BaseModelCallBack;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.util.HttpUtil;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by 12390 on 2018/8/20.
 */
public class BookDetailModel extends BaseModel {
    private Book book;

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public static void mDoGetBookDetail(Integer bookId, final ICallback<BookDetailModel> callback){
        HttpUtil httpUtil = new HttpUtil();

        FormBody.Builder form = new FormBody.Builder();
        form.add("bookId", bookId.toString());
        httpUtil.mDoPost(form, "get_one_book", new BaseModelCallBack(callback) {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String s = response.body().string();
                System.out.println(s);
                BookDetailModel bookDetailModel = gson.fromJson(s, BookDetailModel.class);
                callback.onSuccess(bookDetailModel);
            }
        });
    }
}
