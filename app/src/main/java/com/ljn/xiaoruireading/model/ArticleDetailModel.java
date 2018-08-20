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
public class ArticleDetailModel extends BaseModel {
    private Article article;

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public static void mDoGetArticleDetail(Integer articleId, final ICallback callback){
        HttpUtil httpUtil = new HttpUtil();

        FormBody.Builder form = new FormBody.Builder();
        form.add("articleId", articleId.toString());
        httpUtil.mDoPost(form, "get_one_article", new BaseModelCallBack(callback) {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String s = response.body().string();
                System.out.println(s);
                ArticleDetailModel articleModel = gson.fromJson(s, ArticleDetailModel.class);
                callback.onSuccess(articleModel);
            }
        });
    }
}
