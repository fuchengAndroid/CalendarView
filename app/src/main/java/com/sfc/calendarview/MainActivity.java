package com.sfc.calendarview;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.sfc.calendarview.interf.CalendarListener;
import com.sfc.calendarview.view.CalendarView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements CalendarListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      CalendarView myCalendarView = (CalendarView) findViewById(R.id.myCalendarView);
   myCalendarView.calendarListener =this;
    }

    @Override
    public void onItemLongPress(Date day) {
        DateFormat df = SimpleDateFormat.getDateInstance();
        Toast.makeText(this, df.format(day), Toast.LENGTH_SHORT).show();
    }
}
