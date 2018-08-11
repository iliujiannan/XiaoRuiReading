package com.ljn.xiaoruireading.util;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuzw on 1/1/15.
 */
public class BookShelfViewUtil {

    public static Bitmap readCover(String fileName, Context context){

        Bitmap bitmap = null;
        AssetManager amg = context.getAssets();
        InputStream input = null;
        try {
            input = amg.open(fileName);
            bitmap = BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(null != input){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return bitmap;
    }
	
    public static String[] listAssets(Context context) {
        AssetManager amg = context.getAssets();
        String[] result = null;


        try {
            result = amg.list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
	
	
	
}
