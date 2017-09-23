package com.example.memousingorm.model;

import android.graphics.Bitmap;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 데이터 모델링 - 도메인 추출 - 개념 모델링
 */
@DatabaseTable(tableName = "drawingnote") //클래스는 대문자로 시작하므로 소문자로 시작하기 위해서는 Annotation으로 지정해준다.
public class DrawingNote {
    // 식별자
    @DatabaseField(generatedId = true)
    long id;
    // 제목
    String title;
    // 그림
    String bitmap_path;
    // 날짜
    long datetime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBitmap_path() {
        return bitmap_path;
    }

    public void setBitmap_path(String bitmap_path) {
        this.bitmap_path = bitmap_path;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
}
