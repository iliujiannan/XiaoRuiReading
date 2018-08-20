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
 * Created by 12390 on 2018/8/15.
 */
public class ArticleModel extends BaseModel {
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public static void mDoGetArticles(final ICallback<ArticleModel> callback){
        HttpUtil httpUtil = new HttpUtil();

        FormBody.Builder form = new FormBody.Builder();
        httpUtil.mDoPost(form, "get_articles", new BaseModelCallBack(callback) {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String s = response.body().string();
                System.out.println(s);
                ArticleModel articleModel = gson.fromJson(s, ArticleModel.class);
                callback.onSuccess(articleModel);
            }
        });
    }
}
