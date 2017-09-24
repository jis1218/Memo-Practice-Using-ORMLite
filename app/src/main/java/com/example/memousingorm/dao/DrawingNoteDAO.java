package com.example.memousingorm.dao;

import android.content.Context;

import com.example.memousingorm.DBHelper;
import com.example.memousingorm.model.DrawingNote;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.GenericRawResults;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 정인섭 on 2017-09-22.
 */

public class DrawingNoteDAO {

    private DBHelper helper;
    private Dao<DrawingNote, String> dao = null;

    public DrawingNoteDAO(Context context) {
        helper = new DBHelper(context);
        try {
            dao = helper.getDao(DrawingNote.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //데이터베이스에 추가함
    public void create(DrawingNote drawingNote) {
        try {
            dao.create(drawingNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<DrawingNote> readAll() {
        ArrayList<DrawingNote> list = null;
        try {
            list = (ArrayList) dao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public DrawingNote readOneById(String id) {
        DrawingNote result = null;
        try {
            result = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<DrawingNote> search(String word) {
        String query = String.format("select * from picnote where title like '%%%s%%'", word);
        List<DrawingNote> result = null;
        try {
            GenericRawResults<DrawingNote> temp = dao.queryRaw(query, dao.getRawRowMapper()); //raw라고 하면
            result = temp.getResults();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void update(DrawingNote drawingNote) {
        try {
            dao.update(drawingNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void delete(DrawingNote drawingNote) {
        try {
            dao.delete(drawingNote);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
