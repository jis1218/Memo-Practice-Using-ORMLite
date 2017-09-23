package com.example.memousingorm;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.memousingorm.model.DrawingNote;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by 정인섭 on 2017-09-22.
 */

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.Holder> {
    // 1. 데이터 저장소
    ArrayList<DrawingNote> data;
    Holder holder = null;

    public void setData(ArrayList<DrawingNote> data) {
        this.data = data;
    }
    // 2. 생성자

    public CustomAdapter() {

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
        DrawingNote drawingNote = data.get(position);
        holder.setTitle(drawingNote.getTitle());
        holder.setDate(drawingNote.getDatetime());

    }


    public class Holder extends RecyclerView.ViewHolder {
        TextView textTitle, textDate;

        public Holder(View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.txtTitle);
            textDate = itemView.findViewById(R.id.txtDate);

        }

        public void setTitle(String title) {
            textTitle.setText(title);
        }

        public void setDate(long date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            textDate.setText(sdf.format(date));
        }
    }


}
