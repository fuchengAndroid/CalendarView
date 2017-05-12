package com.sfc.calendarview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * author: sfc.
 * 类说明:
 * version:
 * 创建时间: 2017/5/11 17:05
 */

public class CalendarDayView extends TextView {
    public boolean isToday = false;
    private Paint paint =new Paint();
    public CalendarDayView(Context context) {
        super(context);
    }

    public CalendarDayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl();
    }

    public CalendarDayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl();
    }
    private void initControl(){
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
   paint.setStrokeWidth(2f);
        paint.setColor(Color.parseColor("#ff0000"));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isToday) {
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(0, 0, getWidth() / 2, paint);
        }

    }
}
