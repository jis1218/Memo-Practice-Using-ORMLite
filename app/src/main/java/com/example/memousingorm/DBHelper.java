package com.example.memousingorm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.memousingorm.model.DrawingNote;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by 정인섭 on 2017-09-22.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {
    private static final String DB_NAME = "ormlite24db";
    private static final int DB_VERSION = 1;
    Context context = null;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    public DBHelper(Context context, String databaseName, SQLiteDatabase.CursorFactory factory, int databaseVersion) {
        super(context, databaseName, factory, databaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, DrawingNote.class);
            Toast.makeText(context, "테이블이 생성되었습니다", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
    
}
