package com.ljn.xiaoruireading.base;

/**
 * Created by 12390 on 2018/8/16.
 */
public class BaseModel {
    public static String baseUri = "http://10.25.42.19:8080/";

    private Integer mStatus;
    private String msg;

    public Integer getmStatus() {
        return mStatus;
    }

    public void setmStatus(Integer mStatus) {
        this.mStatus = mStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
