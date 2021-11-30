package com.healthy.healthyapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // 바로가기 메뉴
    ImageView list;
    ImageView start;
    ImageView calendar;

    // 이름, 칭호, 이미지
    TextView name;
    TextView title;
    ImageView img;

    // 로직 처리 변수
    Long imgId;
    String imgName;
    Long titleId;
    String titleName = "";
    String sex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 멤버 아이디
        Intent intent = getIntent();
        String memberId = intent.getStringExtra("memberId");

        list = (ImageView)findViewById(R.id.list_main);
        start = (ImageView)findViewById(R.id.start_main);
        calendar = (ImageView)findViewById(R.id.calendar_main);
        name = (TextView)findViewById(R.id.name);
        title = (TextView)findViewById(R.id.title);
        img = (ImageView)findViewById(R.id.image);

        // 통신 처리
        new MainActivity.JSONTask().execute("http://192.168.35.188:8080/api/member/"+memberId+"/find");

        // 게시판 이동
        list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        CommunityActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
            }
        });

        // 기록하기 이동
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        StartActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
            }
        });

        // 캘린더 이동
        calendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        DiaryActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
            }
        });

    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
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

                    // json 추출
                    for (int i = 0; i < jsonObject.length(); i++) {

                        // 이미지 객체 추출
                        JSONObject imgObj = jsonObject.getJSONObject("image");
                        if (imgObj.getLong("id") != 0) {
                            imgId = imgObj.getLong("id");
                            imgName = imgObj.getString("name");
                        } else {
                            imgId = Long.valueOf(0);
                            imgName = "0";
                        }

                        // 칭호 객체 추출

                        JSONArray titleArr = jsonObject.getJSONArray("title");
                        for (int j = 0; j < titleArr.length(); j++) {
                            JSONObject obj = titleArr.getJSONObject(j);
                            titleId = obj.getLong("id");
                            titleName = obj.getString("name");
                        }

                        // 회원 성별 추출
                        sex = jsonObject.getString("sex");

                        // UI 변경
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

//                                // 기본 세팅
//                                if (imgName.equals("0")) {
//                                    if (sex.equals("남")) {
//                                        name.setText("평범한 남자");
//                                        Glide.with(getApplicationContext()).load(R.raw.normal_man).into(img);
//                                    } else {
//                                        name.setText("평범한 여자");
//                                        Glide.with(getApplicationContext()).load(R.raw.normal_woman).into(img);
//                                    }
//                                }

                                // 필드값 별로 세팅
                                    name.setText(imgName);
                                    if (sex.equals("남")) {
                                        if (imgName.equals("보디빌더")) {
                                            Glide.with(getApplicationContext()).load(R.raw.body_man).into(img);
                                        } else if (imgName.equals("트레이너")){
                                            Glide.with(getApplicationContext()).load(R.raw.trainer_man).into(img);
                                        } else if (imgName.equals("모델")) {
                                            Glide.with(getApplicationContext()).load(R.raw.model_man).into(img);
                                        } else {
                                            name.setText("평범한 남자");
                                            Glide.with(getApplicationContext()).load(R.raw.normal_man).into(img);
                                        }
                                    } else {
                                        if (imgName.equals("보디빌더")) {
                                            Glide.with(getApplicationContext()).load(R.raw.body_woman).into(img);
                                        } else if (imgName.equals("트레이너")){
                                            Glide.with(getApplicationContext()).load(R.raw.trainer_woman).into(img);
                                        } else if (imgName.equals("모델")) {
                                            Glide.with(getApplicationContext()).load(R.raw.model_woman).into(img);
                                        } else {
                                            name.setText("평범한 여자");
                                            Glide.with(getApplicationContext()).load(R.raw.normal_woman).into(img);
                                        }
                                    }

                                // 칭호 세팅
                                if (titleName.length() == 0) {
                                    title.setText("평범함");
                                } else {
                                    title.setText(titleName);
                                }
                            }
                        });
                    }

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
    }
}