package com.sfc.calendarview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.sfc.calendarview.R;
import com.sfc.calendarview.interf.CalendarListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * author: sfc.
 * 类说明:
 * version:
 * 创建时间: 2017/5/11 17:02
 */

public class CalendarView extends LinearLayout {


    private TextView btnPrev;
    private TextView btnNext;
    private TextView tvData;
    private GridView calendar_date;
    private Calendar calendarData = Calendar.getInstance();
    private String displayFormat;
    public CalendarListener calendarListener;
    private static final String TAG = "CalendarView";

    public CalendarView(Context context) {
        super(context);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context,attrs);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context,attrs);
    }

    private void initControl(Context context,AttributeSet attrs) {

        bindControl(context);
        bindControlEvent();

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,R.styleable.CalendarView);

        try {
            String format =typedArray.getString(R.styleable.CalendarView_dateFormat);
            displayFormat =format;
            if (displayFormat ==null){
                displayFormat = "MMM yyyy";
            }
        }finally {
            typedArray.recycle();
        }
        renderCalendar();
    }

    private void bindControl(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.calendar_view, this);
        btnPrev = (TextView) findViewById(R.id.btnPrev);
        btnNext = (TextView) findViewById(R.id.btnNext);
        tvData = (TextView) findViewById(R.id.tvData);
        calendar_date = (GridView) findViewById(R.id.calendar_date);
    }

    private void bindControlEvent() {
        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarData.add(Calendar.MONTH, -1);
                renderCalendar();
            }
        });
        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarData.add(Calendar.MONTH, 1);
                renderCalendar();
            }


        });
    }

    private void renderCalendar() {
        SimpleDateFormat sdf = new SimpleDateFormat(displayFormat);

        tvData.setText(sdf.format(calendarData.getTime()));
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar) calendarData.clone();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int prevSDays = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        calendar.add(Calendar.DAY_OF_MONTH, -prevSDays);
        int maxCellCount = 6 * 7;
        while (cells.size() < maxCellCount) {
            cells.add(calendar.getTime());
            Log.i(TAG, "renderCalendar: "+calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        calendar_date.setAdapter(new CalendarAdapter(getContext(), cells));
        calendar_date.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (calendarListener ==null){
                    return false;
                }else{
                    calendarListener.onItemLongPress((Date) adapterView.getItemAtPosition(position));
                    return true;
                }

            }
        });
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {
        LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days) {
            super(context, R.layout.calendar_text_day, days);
            inflater = LayoutInflater.from(context);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Date date = getItem(position);
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.calendar_text_day, parent, false);
            }

            int day = date.getDate();

            Log.i(TAG, "getView: " + day);
            ((TextView) convertView).setText(String.valueOf(day));

//            Calendar calendar = (Calendar) calendarData.clone();
//            calendar.set(Calendar.DAY_OF_MONTH,1);
//            int daysInMonth =calendar.getActualMaximum(Calendar.DATE);
            Date now = new Date();
            boolean isTheSanmeMonth =false;
            if (date.getMonth() == now.getMonth()){
                isTheSanmeMonth = true;
            }
            if (isTheSanmeMonth){
                //  if (day>=1&&day<=daysInMonth){
                ((TextView) convertView).setTextColor(Color.parseColor("#ffffff"));

            }else{
                ((TextView) convertView).setTextColor(Color.parseColor("#666666"));

            }
//            Date now =new Date();
            if (now.getDate() ==date.getDate()&&now.getMonth()==date.getMonth()
                    &&now.getYear()==date.getYear()){
                ((TextView) convertView).setTextColor(Color.parseColor("#ff0000"));
                ((CalendarDayView) convertView).isToday=true;
            }
            return convertView;
        }
    }

}


