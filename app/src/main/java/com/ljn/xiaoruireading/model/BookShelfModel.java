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
import java.util.List;

/**
 * Created by 12390 on 2018/8/14.
 */
public class BookShelfModel extends BaseModel{
    private List<Book> books;

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public static void mDoGetShelfData(Integer userId, String secretKey, final ICallback<BookShelfModel> callback){
        HttpUtil httpUtil = new HttpUtil();

        FormBody.Builder form = new FormBody.Builder();
        form.add("userId", userId.toString());
        form.add("secretKey", secretKey);
        httpUtil.mDoPost(form, "get_bookshelf", new BaseModelCallBack(callback) {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String s = response.body().string();
                System.out.println(s);
                BookShelfModel bookShelfModel = gson.fromJson(s, BookShelfModel.class);
                callback.onSuccess(bookShelfModel);
            }
        });

    }

    public static void mDoSetReadTime(Integer userId, String secretKey,Integer readTime, Integer bookId, final ICallback<BaseModel> callback){
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体

        formBody.add("userId", userId.toString());
        formBody.add("secretKey", secretKey);
        formBody.add("readTime", readTime.toString());
        formBody.add("bookId", bookId.toString());

        HttpUtil httpUtil = new HttpUtil();
        httpUtil.mDoPost(formBody, "set_total_readTime", new BaseModelCallBack(callback));
    }

    public static void mDoDelBook(Integer userId, String secretKey, Integer bookId, final ICallback<BaseModel> callback){
        FormBody.Builder formBody = new FormBody.Builder();//创建表单请求体

        formBody.add("userId", userId.toString());
        formBody.add("secretKey", secretKey);
        formBody.add("bookId", bookId.toString());

        HttpUtil httpUtil = new HttpUtil();
        httpUtil.mDoPost(formBody, "delete_bookshelf", new BaseModelCallBack(callback));
    }
}
