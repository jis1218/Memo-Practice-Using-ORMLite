package com.example.memousingorm;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.memousingorm.util.FileReadWrite;

import java.io.File;
import java.io.FileInputStream;

public class BitmapActivity extends AppCompatActivity {
    FrameLayout frameLayout = null;
    Button btnBack = null;
    String fileName = null;
    Bitmap bitmap = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitmap);
        fileName = getIntent().getStringExtra("fileName");
        initView();
        addBitmapView();

    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        bitmap.recycle();
//        bitmap = null;
//    }

    public void initView(){
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        btnBack = (Button) findViewById(R.id.btnBack);
    }

    public void addBitmapView(){
        ImageView imageView = new ImageView(this);

        imageView.setImageBitmap(getBitmap(fileName));

        imageView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        //imageView.setBackgroundColor(Color.RED);
        frameLayout.addView(imageView);
    }

    public Bitmap getBitmap(String fileName){

        try {

//           BitmapFactory.Options options = new BitmapFactory.Options();
//           options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//
//            bitmap = BitmapFactory.decodeFile(fileName, options);

            bitmap = FileReadWrite.read(this, fileName);

        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return bitmap;
    }
}
