package com.example.memousingorm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.memousingorm.dao.DrawingNoteDAO;
import com.example.memousingorm.model.DrawingNote;

import java.util.ArrayList;

/**
 * RecyclerView를 사용한 목록 만들기
 *
 * 1. 데이터를 정의하고
 *
 * 2. Adapter를 재정의
 *
 * 3. 재정의한 Adapter를 생성하면서 데이터를 담는다.
 *
 * 4. Adapter와 RecyclerView 컨테이너를 연결
 *
 * 5. RecyclerView에 레이아웃 매니저를 설정
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
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

        CustomAdapter adapter = new CustomAdapter();

        adapter.setData(data);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}


