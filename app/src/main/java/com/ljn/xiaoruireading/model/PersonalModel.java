package com.ljn.xiaoruireading.model;

import com.google.gson.Gson;
import com.ljn.xiaoruireading.base.BaseModel;
import com.ljn.xiaoruireading.base.ICallback;
import com.ljn.xiaoruireading.util.HttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by 12390 on 2018/8/14.
 */
public class PersonalModel extends BaseModel{
    private User userData;

    public User getUserData() {
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

    public class User{
        private Integer userId;

        private String userPhone;

        private String userPhoto;

        private String userNickName;

        private String psw;

        private String secretKey;

        private Integer userType;

        private Integer userReadDailly;

        private Integer userReadTotally;

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }

        public String getUserNickName() {
            return userNickName;
        }

        public void setUserNickName(String userNickName) {
            this.userNickName = userNickName;
        }

        public String getPsw() {
            return psw;
        }

        public void setPsw(String psw) {
            this.psw = psw;
        }

        public String getSecretKey() {
            return secretKey;
        }

        public void setSecretKey(String secretKey) {
            this.secretKey = secretKey;
        }

        public Integer getUserType() {
            return userType;
        }

        public void setUserType(Integer userType) {
            this.userType = userType;
        }

        public Integer getUserReadDailly() {
            return userReadDailly;
        }

        public void setUserReadDailly(Integer userReadDailly) {
            this.userReadDailly = userReadDailly;
        }

        public Integer getUserReadTotally() {
            return userReadTotally;
        }

        public void setUserReadTotally(Integer userReadTotally) {
            this.userReadTotally = userReadTotally;
        }
    }


    public static void mDoGetInformation(String secretKey, Integer userId, final ICallback<PersonalModel> callback){
        HttpUtil httpUtil = new HttpUtil();

        FormBody.Builder form = new FormBody.Builder();
        form.add("userId", userId.toString());
        form.add("secretKey", secretKey);
        httpUtil.mDoPost(form, "personal_information", new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                PersonalModel model = new PersonalModel();
                model.setStatus(0);
                model.setMsg("server error");
                callback.onFailure(model);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                System.out.println("***************调用前7");
//                System.out.println(response.body().string());

                BaseModel baseModel = new BaseModel();
                String s = response.body().string();
                PersonalModel personalModel = new Gson().fromJson(s, PersonalModel.class);
                callback.onSuccess(personalModel);
            }
        });
    }
}
//{"status":1,"msg":"Ok","userData":{"userId":2,"userPhone":"15248113901","userPhoto":"http://cdn.duitang.com/uploads/item/201508/30/20150830105732_nZCLV.jpeg","userNickName":"user_15248113901","psw":"","secretKey":"21f0b84bde661cad2be79e431fbcc4c5","userType":0,"userReadDailly":0,"userReadTotally":0}}
