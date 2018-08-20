package com.ljn.xiaoruireading.model;

/**
 * Created by 12390 on 2018/8/20.
 */
public class Book{
    private Integer bookId;
    private String bookName;
    private String bookAuthor;
    private String bookImg;
    private String bookDescription;
    private Integer bookPrice;
    private Integer bookLoadingAmount;
    private Integer bookChapterAmount;
    private String bookLocation;
    private String bookLabel;

    public Book(){}

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getBookImg() {
        return bookImg;
    }

    public void setBookImg(String bookImg) {
        this.bookImg = bookImg;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public Integer getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Integer bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Integer getBookLoadingAmount() {
        return bookLoadingAmount;
    }

    public void setBookLoadingAmount(Integer bookLoadingAmount) {
        this.bookLoadingAmount = bookLoadingAmount;
    }

    public Integer getBookChapterAmount() {
        return bookChapterAmount;
    }

    public void setBookChapterAmount(Integer bookChapterAmount) {
        this.bookChapterAmount = bookChapterAmount;
    }

    public String getBookLocation() {
        return bookLocation;
    }

    public void setBookLocation(String bookLocation) {
        this.bookLocation = bookLocation;
    }

    public String getBookLabel() {
        return bookLabel;
    }

    public void setBookLabel(String bookLabel) {
        this.bookLabel = bookLabel;
    }
}
