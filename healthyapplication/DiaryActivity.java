package com.healthy.healthyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);

        ImageView back = (ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });

        CalendarView calendar = (CalendarView)findViewById(R.id.calendar_diary);
        DateFormat format = new SimpleDateFormat("yyyy년MM월dd일");
        Date date = new Date(calendar.getDate());

        // 초기 날짜 생성
        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayofMonth) {
                String day;
                day = year + "년" + (month+1) + "월" + dayofMonth + "일";
            }
        });

    }


}