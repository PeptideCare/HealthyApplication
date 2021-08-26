package com.healthy.healthyapplication;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DiaryActivity extends AppCompatActivity {

    TextView field ;
    TextView content ;
    TextView hour ;
    ImageView back;

    private static Toast sToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary);

        // 멤버 아이디
        Intent intent = getIntent();
        String memberId = intent.getStringExtra("memberId");

        // 토스트 중복 방지
        sToast = Toast.makeText(this, "null", Toast.LENGTH_SHORT);

        back = (ImageView)findViewById(R.id.back);
        field = (TextView)findViewById(R.id.text_field);
        content = (TextView)findViewById(R.id.text_content);
        hour = (TextView)findViewById(R.id.text_hour);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                intent.putExtra("memberId", memberId);
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
                day = year + "-" + String.format("%02d",(month+1)) + "-" + dayofMonth;

                // 통신 처리
                new DiaryActivity.JSONTask().execute("http://192.168.35.53:8080/api/diary/"+memberId+"/"+day+"/find");

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
                    JSONArray jsonArray = jsonObject.getJSONArray("diary");

                    if (jsonArray.length() == 0) {
                        field.setText("");
                        content.setText("");
                        hour.setText("");
                    }

                    for (int i=0; i<jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);

                        Long findId = obj.getLong("id");
                        String findField = obj.getString("field");
                        String findContent = obj.getString("content");
                        int findHour = obj.getInt("hour");

                        field.setText(findField);
                        content.setText(findContent);
                        hour.setText(findHour + "시간");
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