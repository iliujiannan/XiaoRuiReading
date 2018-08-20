package com.ljn.xiaoruireading.model;

/**
 * Created by 12390 on 2018/8/20.
 */
public class Article {
    private Integer articleId;
    private String articleTitle;
    private Integer articleAuthor;
    private String articleImg;
    private String articleUrl;
    private String articleReadingAmount;
    private String articleDescription;

    public Integer getArticleId() {
        return articleId;
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

    public Integer getArticleAuthor() {
        return articleAuthor;
    }

    public void setArticleAuthor(Integer articleAuthor) {
        this.articleAuthor = articleAuthor;
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

    public String getArticleReadingAmount() {
        return articleReadingAmount;
    }

    public void setArticleReadingAmount(String articleReadingAmount) {
        this.articleReadingAmount = articleReadingAmount;
    }

    public String getArticleDescription() {
        return articleDescription;
    }

    public void setArticleDescription(String articleDescription) {
        this.articleDescription = articleDescription;
    }
}
