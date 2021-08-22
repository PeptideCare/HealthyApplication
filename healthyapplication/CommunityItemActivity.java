package com.healthy.healthyapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class CommunityItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_item);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ImageView back = (ImageView)findViewById(R.id.back);
        TextView id = (TextView)findViewById(R.id.author_community_item);
        TextView content = (TextView)findViewById(R.id.content_community_item);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }
}