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
 * Created by 12390 on 2018/8/16.
 */
public class BookCityModel extends BaseModel{
    private List<Book> books;


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

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

    public static void mDoGetBooks(String lable, final ICallback<BookCityModel> callback){
        HttpUtil httpUtil = new HttpUtil();

        FormBody.Builder form = new FormBody.Builder();
        System.out.println("****lable:" + lable);
        form.add("bookLabel", lable);
        httpUtil.mDoPost(form, "get_books", new BaseModelCallBack(callback) {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                String s = response.body().string();
                System.out.println(s);
                BookCityModel bookCityModel = gson.fromJson(s, BookCityModel.class);
                callback.onSuccess(bookCityModel);
            }
        });
    }
}
