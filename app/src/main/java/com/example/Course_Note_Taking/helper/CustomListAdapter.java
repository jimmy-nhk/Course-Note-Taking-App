package com.example.Course_Note_Taking.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.Course_Note_Taking.Model.Note;
import com.example.Course_Note_Taking.R;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

    private ArrayList<Note> noteLists;
    private LayoutInflater layoutInflater;

    public CustomListAdapter(Context aContext, ArrayList<Note> noteLists){
        this.noteLists = noteLists;
        layoutInflater = LayoutInflater.from(aContext);
    }

    @Override
    public int getCount() {
        return noteLists.size();
    }

    @Override
    public Object getItem(int position) {
        return noteLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView shortText;
        TextView date;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;


        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.shortText  = (TextView) convertView.findViewById(R.id.shortText);
            holder.date  = (TextView) convertView.findViewById(R.id.dateText);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.shortText.setText(noteLists.get(position).getNoteName());
        holder.date.setText(noteLists.get(position).getDateEdited().toString());

        return convertView;
    }


}
