package com.healthy.healthyapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class CommunityActivity extends AppCompatActivity {

    // 커뮤니티 제목
    ArrayList<String> title = new ArrayList<>();

    //커뮤니티 글쓴이
    ArrayList<String> author = new ArrayList<>();

    // 커뮤니티 게시판 id
    Long communityId;

    // 회원 아이디
    String memberId;

    FloatingActionButton insert;
    ListView list;
    ImageView back;

    // 리스트뷰 처리
    ArrayList<HashMap<String, String>> arr = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 통신 처리
        new CommunityActivity.JSONTask().execute("http://192.168.35.188:8080/api/community/find");

        // 멤버 아이디
        Intent intent = getIntent();
        memberId = intent.getStringExtra("memberId");

        insert = (FloatingActionButton)findViewById(R.id.insert);
        list = (ListView)findViewById(R.id.list_community);
        back = (ImageView)findViewById(R.id.back);


        // 글쓰기 버튼
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        CommunityInsertActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
            }
        });

        // 뒤로가기
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
            }
        });

    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            System.out.println("list back start");
            try {

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(strings[0]);

                    InputStream ip = url.openStream();
                    reader = new BufferedReader(new InputStreamReader(ip));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }

                    String data = buffer.toString();

                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray jsonArray = jsonObject.getJSONArray("community");

                    // json 추출
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        String findTitle = obj.getString("title");
                        communityId = obj.getLong("id");

                        JSONObject memberObj = obj.getJSONObject("member");
                        String findNick = memberObj.getString("nickname");

                        title.add(findTitle);
                        author.add(findNick);
                    }

                    // 리스트뷰 처리
                    for (int i=0; i<title.size(); i++) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("title", title.get(i));
                        map.put("author", author.get(i));

                        arr.add(map);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            // 리스트뷰 처리

                            System.out.println("list start");
                            String [] keys = {"title", "author"};
                            int [] ids = {android.R.id.text1, android.R.id.text2};
                            SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), arr, android.R.layout.simple_list_item_2, keys, ids) {
                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {

                                    View view = super.getView(position, convertView, parent);
                                    TextView author = (TextView)view.findViewById(android.R.id.text1);
                                    TextView text = (TextView)view.findViewById(android.R.id.text2);

                                    author.setTextColor(Color.WHITE);
                                    text.setTextColor(Color.WHITE);

                                    return view;
                                }
                            };

                            list.setAdapter(adapter);
                        }
                    });

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        if (reader != null) {
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onCancelled() {

        }
    }

    //리스트뷰 클릭 메서드
    class ListListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(getApplicationContext(),
                    CommunityItemActivity.class);
            intent.putExtra("memberId", memberId);
            intent.putExtra("communityId", communityId);
            startActivity(intent);
        }
    }
}