package com.healthy.healthyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

public class CommunityActivity extends AppCompatActivity {

    // 커뮤니티 제목
    ArrayList<String> title = new ArrayList<>();

    //커뮤니티 글쓴이
    ArrayList<String> author = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        ListView list = (ListView)findViewById(R.id.list);
        ImageView back = (ImageView)findViewById(R.id.back);


        // 리스트뷰 처리
        ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();

        for (int i=0; i<title.size(); i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("title", title.get(i));
            map.put("author", author.get(i));

            data.add(map);
        }

        String [] keys = {"title", "author"};
        int [] ids = {android.R.id.text1, android.R.id.text2};

        SimpleAdapter adapter = new SimpleAdapter(this, data, android.R.layout.simple_list_item_2, keys, ids);
        list.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                startActivity(intent);
            }
        });
    }

    //리스트뷰 클릭 메서드
    class ListListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

        }
    }

}