package com.example.memousingorm;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-09-18.
 */

public class DrawView2 extends View {
    ArrayList<PathFinder> list;
    PathFinder pathFinder;
    Path currentPath;
    Paint currentPaint;
    PathFinder temp;


    public DrawView2(Context context) {
        super(context);
        list = new ArrayList<>();
        pathFinder = new PathFinder();
        currentPath = pathFinder.path;
        currentPaint = pathFinder.paint;
        currentPaint.setColor(Color.BLACK);
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setStrokeWidth(1);
        //list.add(pathFinder);
        temp = pathFinder;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(PathFinder path : list){
            canvas.drawPath(path.path, path.paint);
        }
        //canvas.drawPath(currentPath, currentPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN :
                list.add(temp);
                currentPath.moveTo(event.getX(), event.getY());
                break;

            case MotionEvent.ACTION_MOVE :
                currentPath.lineTo(event.getX(), event.getY());
                invalidate();
                break;
        }
        return true;
    }

    public PathFinder anyThingChanged(int color, int progress){
        PathFinder pathFinder = new PathFinder();
        currentPath = pathFinder.path;
        currentPaint = pathFinder.paint;
        currentPaint.setStyle(Paint.Style.STROKE);
        currentPaint.setColor(color);
        currentPaint.setStrokeWidth(progress);
        temp = pathFinder;
        //list.add(pathFinder);
        Log.d("list size", "List size is " + list.size());

        return pathFinder;
    }

//    public void input(PathFinder pathFinder){
//        currentPath = pathFinder.path;
//        currentPaint = pathFinder.paint;
//        list.add(pathFinder);
//    }
}

class PathFinder{
    public Path path = new Path();
    public Paint paint = new Paint();
}