package com.ljn.xiaoruireading.util;

import android.os.Environment;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtil {

    public final static String mCachePath = "/XRR_CACHE/";

    public final static String mFileType = ".txt";
    public final static String mFileMid = "/noval_";

    public static String getFileContent(String path) {
        OutputStreamWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            // 发送POST请求必须设置为true
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "GBK"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result.toString();
    }

    public static String mGetRootPath() {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    public static boolean mCreateCacheDir(String path) {

        System.out.println(path);

        File file = new File(path);
//判断文件夹是否存在，如果不存在就创建，否则不创建
        if (!file.exists()) {
            //通过file的mkdirs()方法创建目录中包含却不存在的文件夹
            return file.mkdirs();

        }
        return true;
    }

    public static boolean mSaveFile(String content, String path){
        File file = new File(path);
        if(file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(content);
            writer.close();
            out.close();
            return true;
        } catch (IOException e) {
            return false;
        }


    }

    public static String readFileByChars(String fileName) {
        File file = new File(fileName);
        String result = "";

        Reader reader = null;
        try {
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                result+=(char) tempchar;
//                if (((char) tempchar)) {
//                    //System.out.print((char) tempchar);
//
//                }
            }
            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
