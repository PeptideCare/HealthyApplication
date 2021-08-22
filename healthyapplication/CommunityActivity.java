package com.healthy.healthyapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        FloatingActionButton insert = (FloatingActionButton)findViewById(R.id.insert);
        ListView list = (ListView)findViewById(R.id.list_community);
        ImageView back = (ImageView)findViewById(R.id.back);

        title.add("아아");
        author.add("홍길동");

        // 글쓰기 버튼
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        CommunityInsertActivity.class);
                startActivity(intent);
            }
        });


        // 리스트뷰 처리
        ArrayList<HashMap<String, String>> data = new ArrayList<>();

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

        // 뒤로가기
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