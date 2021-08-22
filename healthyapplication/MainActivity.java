package com.healthy.healthyapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView list = (ImageView)findViewById(R.id.list_main);
        ImageView start = (ImageView)findViewById(R.id.start_main);
        ImageView calendar = (ImageView)findViewById(R.id.calendar_main);

        // 게시판 이동
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        CommunityActivity.class);
                startActivity(intent);
            }
        });

        // 기록하기 이동
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        StartActivity.class);
                startActivity(intent);
            }
        });

        // 캘린더 이동
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        DiaryActivity.class);
                startActivity(intent);
            }
        });

    }
}