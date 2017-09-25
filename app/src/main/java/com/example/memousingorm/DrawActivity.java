package com.example.memousingorm;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.example.memousingorm.dao.DrawingNoteDAO;
import com.example.memousingorm.model.DrawingNote;
import com.example.memousingorm.util.FileReadWrite;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DrawActivity extends AppCompatActivity {

    FrameLayout stage;
    RadioGroup radioGroup;
    DrawView2 drawView;
    Button btnDlt;
    SeekBar seekBar;
    int colorcolor = Color.BLACK;
    int seek = 1;
    ArrayList<DrawingNote> list = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        stage = (FrameLayout) findViewById(R.id.stage);
        list = new ArrayList<>();
        drawView = new DrawView2(DrawActivity.this);
        stage.setBackgroundColor(Color.WHITE);
        stage.addView(drawView);
        btnDlt = (Button) findViewById(R.id.btnDlt);
        dbInit();

        seekBar = (SeekBar) findViewById(R.id.seekBar);

//        btnDlt = (Button) findViewById(R.id.btnDlt);
//        btnDlt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                drawView.delete();
//            }
//        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                //drawView.setWidth(i);
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioBlack:
                        Log.d("change", "change");
//                        drawView = new DrawView(DrawActivity.this);
//                        stage.addView(drawView);
                        //drawView.setColor(Color.BLACK);
                        drawView.anyThingChanged(Color.BLACK, seekBar.getProgress());
                        break;

                    case R.id.radioCyan:
//                        drawView = new DrawView(DrawActivity.this);
//                        stage.addView(drawView);
                        //drawView.setColor(Color.CYAN);
                        drawView.anyThingChanged(Color.CYAN, seekBar.getProgress());
                        break;

                    case R.id.radioMagneta:
//                        drawView = new DrawView(DrawActivity.this);
//                        stage.addView(drawView);
                        //drawView.setColor(Color.MAGENTA);
                        drawView.anyThingChanged(Color.MAGENTA, seekBar.getProgress());
                        break;

                    case R.id.radioYellow:
//                        drawView = new DrawView(DrawActivity.this);
//                        stage.addView(drawView);
                        //drawView.setColor(Color.YELLOW);
                        drawView.anyThingChanged(Color.YELLOW, seekBar.getProgress());
                        break;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        radioGroup = (RadioGroup) findViewById(R.id.radioColor);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int id) {
                switch (id) {
                    case R.id.radioBlack:
//                        drawView = new DrawView(DrawActivity.this);
//                        stage.addView(drawView);
                        //drawView.setColor(Color.BLACK);
                        drawView.anyThingChanged(Color.BLACK, seekBar.getProgress());

                        break;

                    case R.id.radioCyan:
//                        drawView = new DrawView(DrawActivity.this);
//                        stage.addView(drawView);
                        //drawView.setColor(Color.CYAN);
                        drawView.anyThingChanged(Color.CYAN, seekBar.getProgress());
                        break;

                    case R.id.radioMagneta:
//                        drawView = new DrawView(DrawActivity.this);
//                        stage.addView(drawView);
                        //drawView.setColor(Color.MAGENTA);
                        drawView.anyThingChanged(Color.MAGENTA, seekBar.getProgress());
                        break;

                    case R.id.radioYellow:
//                        drawView = new DrawView(DrawActivity.this);
//                        stage.addView(drawView);
                        //drawView.setColor(Color.YELLOW);
                        drawView.anyThingChanged(Color.YELLOW, seekBar.getProgress());
                        break;
                }
            }
        });

        btnDlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureCanvas();
            }
        });
    }

    public int getColor() {
        return colorcolor;
    }

    public int getSeek() {
        return seek;
    }


    public void captureCanvas(){
        // 드로잉 캐쉬를 먼저 지워주ㅝ야 한다.
        stage.destroyDrawingCache();
        // 드로잉 캐쉬를 다시 만들어준다.
        stage.buildDrawingCache();
        //레이아웃에서 그려진 내용을 bitmap 형태로 가져온다.
        Bitmap bitmap = stage.getDrawingCache();

        //이미지 파일을 저장하고 파일 이름 저장
        String filename = String.valueOf(System.currentTimeMillis());

        //파일명 중복검사
        // 1. 현재 파일명을 풀 경로로 File 객체로 변환
        String dir = getFilesDir().getAbsolutePath();
        String fileFullPath = dir + "/" +filename;
        File file = new File(fileFullPath);
        int count = 0;
        if(file.exists()){
            count++;
            filename = System.currentTimeMillis() + "("+count+")";
            file = new File(dir+"/"+filename);
        }

        try {
            // /data/data/패키지/files 밑에 쌓이게 됨
            FileReadWrite.write(this, filename, bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //데이터 베이스에 경로도 저장
        DrawingNote drawingNote = new DrawingNote();
        drawingNote.setBitmap_path(filename); //데이터 베이스에 경로도 저장
        drawingNote.setId(System.currentTimeMillis());
        drawingNote.setTitle(filename);
        drawingNote.setDatetime(System.currentTimeMillis());
        //list.add(drawingNote);
        dao.create(drawingNote);

        bitmap.recycle(); // Native에 다 썼다고 알려준다. 가비지 컬렉터 대상이 아님
    }
    private void init(){

    }
    DrawingNoteDAO dao;

    private void dbInit(){
        dao = new DrawingNoteDAO(this);
    }

}
