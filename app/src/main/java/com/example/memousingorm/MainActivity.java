package com.example.memousingorm;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.memousingorm.dao.DrawingNoteDAO;
import com.example.memousingorm.model.DrawingNote;
import com.example.memousingorm.util.PermissionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView를 사용한 목록 만들기
 * <p>
 * 1. 데이터를 정의하고
 * <p>
 * 2. Adapter를 재정의
 * <p>
 * 3. 재정의한 Adapter를 생성하면서 데이터를 담는다.
 * <p>
 * 4. Adapter와 RecyclerView 컨테이너를 연결
 * <p>
 * 5. RecyclerView에 레이아웃 매니저를 설정
 */
public class MainActivity extends AppCompatActivity {

    private static final int REQ_CODE = 999;
    private String permissions[] = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    PermissionUtil pUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pUtil = new PermissionUtil(REQ_CODE, permissions);
        if(pUtil.checkPermission(this)) {
            init();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(pUtil.afterPermissionResult(requestCode, grantResults)){
            init();
        }else{
            finish();
        }
    }

    public void openDraw(View view) {
        Intent intent = new Intent(this, DrawActivity.class);
        startActivity(intent);
    }

    private void init() {
        DrawingNoteDAO dao = new DrawingNoteDAO(this);
        // 화면 만들기
        // 데이터 정의
//
        ArrayList<DrawingNote> data = dao.readAll();

        CustomAdapter adapter = new CustomAdapter(data, this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}


