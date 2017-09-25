package com.example.memousingorm.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by 정인섭 on 2017-09-20.
 */

public class FileReadWrite {

    private static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String DIR = ROOT + "/temp/picture";

    public static void write(Context context, String fileName, Bitmap bitmap) throws IOException {
        ByteArrayOutputStream bos = null;
        FileOutputStream fos = null;
        byte[] bytes;
        try {
            // 1. 파일 저장을 위한 디렉토리를 정한다.
            // private static final String DIR = "/temp/drawingnote";
            // 2. 체크해서 없으면 생성
            File file = new File(DIR);

            if(!file.exists()){
                file.mkdirs();
                Toast.makeText(context, file.mkdir() + "", Toast.LENGTH_SHORT).show();
            }
            // 3. 해당 디렉토리에 파일 쓰기
            //    파일이 있는지 검사
            File file2 = new File( DIR+ "/" + fileName);
            if(!file2.exists()){
                file2.createNewFile();
            }
            fos = new FileOutputStream(file2);
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

    // 일반적으로는 파일을 저장할 경로는 설정 파일에 입력해둔다.

    public static Bitmap read(Context context, String fileName) throws IOException {

        FileInputStream fis = null;
        ByteArrayInputStream bais = null;
        BufferedInputStream bis = null;
        Bitmap bitmap = null;

        try {
            File file = new File(fileName);
            Toast.makeText(context, file.exists()+"", Toast.LENGTH_SHORT).show();
            fis = new FileInputStream(fileName);

            //fis = context.openFileInput(fileName);
            bitmap = BitmapFactory.decodeStream(fis);

//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//            bitmap = BitmapFactory.decodeFile(fileName, options);
        } catch (IOException e) {
            Toast.makeText(context, "파일을 찾을 수 없습니다", Toast.LENGTH_SHORT).show();
            throw e;
        } finally {
            if (bis != null) {
                bis.close();
            }
            if (fis != null) {
                fis.close();
            }
        }

        return bitmap;

    }

}
