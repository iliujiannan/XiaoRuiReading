package com.ljn.xiaoruireading.view.concrete_views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ljn.xiaoruireading.R;
import com.ljn.xiaoruireading.base.BaseActivity;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.model.Article;
import com.ljn.xiaoruireading.model.ArticleDetailModel;
import com.ljn.xiaoruireading.presenter.ArticleDetailPresenter;
import com.ljn.xiaoruireading.util.FileUtil;
import com.ljn.xiaoruireading.util.HttpUtil;
import com.ljn.xiaoruireading.util.ImageUtil;

/**
 * Created by 12390 on 2018/8/20.
 */
public class ArticleDetailActivity extends BaseActivity{
    private ArticleDetailPresenter articleDetailPresenter;

    private ImageView mBackBt;
    private TextView mTitle;
    private TextView mRa;
    private TextView mText1;
    private TextView mText2;
    private ImageView mImg;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);
        articleDetailPresenter = new ArticleDetailPresenter();
        articleDetailPresenter.attachView(this);
        mInitComponent();
    }

    @Override
    protected void mInitComponent() {
        mBackBt = (ImageView) findViewById(R.id.article_back_button);
        mBackBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mTitle = (TextView) findViewById(R.id.article_detail_title);
        mRa = (TextView) findViewById(R.id.article_detail_ra);
        mText1 = (TextView) findViewById(R.id.article_detail_text1);
        mText2 = (TextView) findViewById(R.id.article_detail_text2);
        mImg = (ImageView) findViewById(R.id.article_detail_img);

        Integer articleId = getIntent().getIntExtra("articleId",0);
        System.out.println("id****" + articleId);
        articleDetailPresenter.mGetArticleDetail(articleId);
    }

    @Override
    public void onActionSucc(BaseModel result) {
        final Article article = ((ArticleDetailModel)result).getArticle();
        final Bitmap bitmap = ImageUtil.getHttpBitmap(HttpUtil.baseUri + article.getArticleImg());
        String content = FileUtil.getFileContentUTF(HttpUtil.baseUri + article.getArticleUrl());
        final String content1,content2;
        content1 = content.substring(0,content.length()/2);
        content2 = content.substring(content.length()/2+1, content.length()-1);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTitle.setText(article.getArticleTitle());
                mRa.setText(article.getArticleReadingAmount().toString() + "人阅读");
                mImg.setImageBitmap(bitmap);
                mText1.setText(content1);
                mText2.setText(content2);
            }
        });
    }
}
