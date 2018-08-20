package com.ljn.xiaoruireading.model;

/**
 * Created by 12390 on 2018/8/20.
 */
public class Article {
    private Integer articleId;
    private String articleTitle;
    private Integer articleAuthorId;
    private String articleImg;
    private String articleUrl;
    private Integer articleReadingAmount;
    private String articleDescription;

    public Integer getArticleId() {
        return articleId;
    }

    public Integer getArticleAuthorId() {
        return articleAuthorId;
    }

    public void setArticleAuthorId(Integer articleAuthorId) {
        this.articleAuthorId = articleAuthorId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }


    public String getArticleImg() {
        return articleImg;
    }

    public void setArticleImg(String articleImg) {
        this.articleImg = articleImg;
    }

    public String getArticleUrl() {
        return articleUrl;
    }

    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }

    public Integer getArticleReadingAmount() {
        return articleReadingAmount;
    }

    public void setArticleReadingAmount(Integer articleReadingAmount) {
        this.articleReadingAmount = articleReadingAmount;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }
}
