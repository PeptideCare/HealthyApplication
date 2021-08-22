package com.healthy.healthyapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class CommunityInsertActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_insert);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        EditText content = (EditText)findViewById(R.id.content_community_insert);
        Button btn = (Button)findViewById(R.id.btn_community_insert);
        ImageView back = (ImageView) findViewById(R.id.back);

        // 완료 버튼
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        CommunityActivity.class);
                startActivity(intent);
            }
        });

        // 뒤로가기
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        CommunityActivity.class);
                startActivity(intent);
            }
        });
    }
}