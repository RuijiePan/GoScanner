package com.jb.goscanner.util;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by panruijie on 2017/9/3.
 * Email : zquprj@gmail.com
 */

public class Base64Util {

    public static String encorBase64File(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            //转换成输入流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = fis.read(buffer)) >= 0) {
                baos.write(buffer, 0, count);
            }
            fis.close();
            //进行Base64编码
            return android.util.Base64.encodeToString(baos.toByteArray(), android.util.Base64.DEFAULT);
        } catch (Exception e) {
            return "";
        }
    }

    public static void decoderBase64File(String base64Code, String savePath) throws Exception {
        byte[] buffer = android.util.Base64.decode(base64Code, android.util.Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
    }

    public static String encodeBitmap(Bitmap bitmap) {
        String result = "";
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
