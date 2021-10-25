package com.example.Course_Note_Taking.Controller;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import com.example.Course_Note_Taking.Model.Note;
import com.example.Course_Note_Taking.R;
import com.example.Course_Note_Taking.helper.CustomListAdapter;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_home_screen);
    }


}