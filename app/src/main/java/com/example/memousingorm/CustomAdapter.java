package com.example.memousingorm;


import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memousingorm.model.DrawingNote;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-09-22.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {

    private static final String ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String DIR = ROOT + "/temp/picture";
    // 1. 데이터 저장소
    ArrayList<DrawingNote> data = null;

    Context context;


    // 2. 생성자

    public CustomAdapter(ArrayList<DrawingNote> data, Context context) {
        this.data = data;
        this.context = context;
        Toast.makeText(context, DIR, Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    // 목록에서 아이템이 최초 요청되면 View Holder를 생성해준다.
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new Holder(view);
    }

    //위의 함수가 null인 경우 아래가 호출
    // 생성된 View Holder를 Recycler뷰에 넘겨서 그리게 한다. (홀더 사용)
    // 1. 데이터 저장소에 객체단위로 꺼내둔다.
    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final DrawingNote drawingNote = data.get(position);
        holder.setTitle(drawingNote.getTitle());
        holder.setDate(drawingNote.getDatetime());
        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, ROOT, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, BitmapActivity.class);
                intent.putExtra("fileName", filePathNamer(drawingNote));
                context.startActivity(intent);

            }
        });

    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView textTitle, textDate;
        LinearLayout linearLayout;

        public Holder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.txtTitle);
            textDate = itemView.findViewById(R.id.txtDate);
            linearLayout = itemView.findViewById(R.id.linearLayout);

        }

        public void setTitle(String title) {
            textTitle.setText(title);
        }

        public void setDate(long date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            textDate.setText(sdf.format(date));
        }
    }

    public String filePathNamer(DrawingNote drawingNote) {
        return DIR + "/" + drawingNote.getBitmap_path() + ".jpg";

    }


}
