package com.example.Course_Note_Taking.helper;
// Ref: https://www.tutlane.com/tutorial/android/android-listview-with-examples
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.Course_Note_Taking.Controller.HomeScreen;
import com.example.Course_Note_Taking.Controller.NoteEditing;
import com.example.Course_Note_Taking.Controller.NoteListDisplay;
import com.example.Course_Note_Taking.Model.Course;
import com.example.Course_Note_Taking.Model.Note;
import com.example.Course_Note_Taking.R;

import java.util.ArrayList;
import java.util.EventListener;

public class CustomListAdapter extends BaseAdapter {

    private ArrayList<Note> noteLists;
    private LayoutInflater layoutInflater;
    private Activity activity;
    private String courseName;

    public CustomListAdapter(Context aContext, ArrayList<Note> noteLists , Activity activity , String courseName){
        this.noteLists = noteLists;
        layoutInflater = LayoutInflater.from(aContext);
        this.activity = activity;
        this.courseName = courseName;
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
        CheckBox isDeleted;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;


        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();
            holder.shortText  = (TextView) convertView.findViewById(R.id.shortText);
            holder.date  = (TextView) convertView.findViewById(R.id.dateText);
            holder.isDeleted = (CheckBox) convertView.findViewById(R.id.deleteCheckbox);


            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();

        }

        holder.isDeleted.setChecked(false);
        holder.shortText.setText(noteLists.get(position).getNoteName());
        holder.date.setText(filterDate(noteLists.get(position).getDateEdited().toString()));

        holder.isDeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();

                NoteListDisplay noteListDisplay = (NoteListDisplay) activity;


                if (checked)
                    noteListDisplay.getCourse().getNoteBasedOnTitle(holder.shortText.getText().toString()).setDelete(true);
//                    noteLists.get(position).setDelete(true);
                else
//                    noteLists.get(position).setDelete(false);
                    noteListDisplay.getCourse().getNoteBasedOnTitle(holder.shortText.getText().toString()).setDelete(true);

            }
        });

        holder.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(activity , NoteEditing.class);
                intent.putExtra("noteTitle" ,  holder.shortText.getText().toString());
                intent.putExtra("courseName" , courseName);
                activity.startActivityForResult(intent , 100);

            }
        });
        return convertView;
    }

    public String filterDate (String rawString){

        char [] filterString = new char[rawString.length()];

        int countColon = 0;

        for (int i = 0 ; i < rawString.length(); i++){

            if (rawString.charAt(i) == 'T'){
                filterString[i] = ' ';
                continue;
            }

            if(rawString.charAt(i) == ':'){
                countColon++;
                if (countColon == 2)
                    return String.valueOf(filterString);
            }

            filterString[i] = rawString.charAt(i);
        }

        return null;
    }


}
