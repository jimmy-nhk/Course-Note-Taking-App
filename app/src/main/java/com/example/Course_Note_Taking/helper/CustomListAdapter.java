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
import android.widget.LinearLayout;
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

    // initialize the attributes
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

        // initialize view holder
        ViewHolder holder;

        // check if the convertView is null
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            holder = new ViewHolder();

            // Wire the components
            holder.shortText  = (TextView) convertView.findViewById(R.id.shortText);
            holder.date  = (TextView) convertView.findViewById(R.id.dateText);
            holder.isDeleted = (CheckBox) convertView.findViewById(R.id.deleteCheckbox);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //  set the needed information
        holder.isDeleted.setChecked(false);
        holder.shortText.setText(noteLists.get(position).getNoteName());
        holder.date.setText(filterDate(noteLists.get(position).getDateEdited().toString()));
        LinearLayout linearLayout = convertView.findViewById(R.id.linear);


        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity , NoteEditing.class);
                intent.putExtra("noteTitle" ,  holder.shortText.getText().toString());
                intent.putExtra("courseName" , courseName);
                activity.startActivityForResult(intent , 100);
            }
        });

        holder.isDeleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((CheckBox) v).isChecked();

                NoteListDisplay noteListDisplay = (NoteListDisplay) activity;

                // Check if the checked box is checked
                if (checked)
                    // set the delete to true
                    noteListDisplay.getCourse().getNoteBasedOnTitle(holder.shortText.getText().toString()).setDelete(true);
                else // set the delete to false
                    noteListDisplay.getCourse().getNoteBasedOnTitle(holder.shortText.getText().toString()).setDelete(false);

            }
        });

        return convertView;
    }

    // filter the string date
    public String filterDate (String rawString){

        // initialize the new string
        char [] filterString = new char[rawString.length()];

        // check the colon
        int countColon = 0;

        // iterate through each character in the string
        for (int i = 0 ; i < rawString.length(); i++){

            // check if the character is T then replace it with T
            if (rawString.charAt(i) == 'T'){
                filterString[i] = ' ';
                continue;
            }

            // check if the character is :
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
