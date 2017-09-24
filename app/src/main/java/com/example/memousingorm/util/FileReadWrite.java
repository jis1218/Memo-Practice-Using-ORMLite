package com.example.memousingorm.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by 정인섭 on 2017-09-20.
 */

public class FileReadWrite {

    public static void write(Context context, String fileName, Bitmap bitmap) throws IOException {
        ByteArrayOutputStream bos = null;
        FileOutputStream fos = null;
        byte[] bytes;
        try {
            bos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bytes = bos.toByteArray();
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.write(bytes);

        } catch (Exception e) {
            throw e;
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (Exception e) {
                    throw e;
                }
            }
            if(fos !=null){
                try{
                    fos.close();
                }catch(Exception e){
                    throw e;
                }
            }
        }

    }

    public static String read(Context context, String fileName) throws IOException {

        FileInputStream fis = null;
        ByteArrayInputStream bais = null;
        BufferedInputStream bis = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fis = context.openFileInput(fileName);
            bis = new BufferedInputStream(fis);
            byte[] byteContainer = new byte[1000];
            int count = 0;
            while ((count = bis.read(byteContainer)) != -1) {
                String str = new String(byteContainer, 0, count);
                stringBuilder.append(str);
            }

        } catch (IOException e) {
            throw e;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (fis != null) {
                fis.close();
            }
        }

        return stringBuilder.toString();

    }

}
