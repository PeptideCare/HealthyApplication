package com.healthy.healthyapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class CommunityInsertActivity extends AppCompatActivity {

    EditText title;
    EditText content;
    Button btn;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.community_insert);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 멤버 아이디
        Intent intent = getIntent();
        String memberId = intent.getStringExtra("memberId");

        title = (EditText)findViewById(R.id.title_community_insert);
        content = (EditText)findViewById(R.id.content_community_insert);
        btn = (Button)findViewById(R.id.btn_community_insert);
        back = (ImageView) findViewById(R.id.back);

        // 완료 버튼
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new CommunityInsertActivity.JSONTask().execute("http://192.168.35.53:8080/api/community/"+memberId+"/insert");

                Intent intent = new Intent(getApplicationContext(),
                        CommunityActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
            }
        });

        // 뒤로가기
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        CommunityActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        String comContent = content.getText().toString();
        String comTitle = title.getText().toString();

        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("title", comTitle);
                jsonObject.accumulate("content", comContent);

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    URL url = new URL(strings[0]);
                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("POST");
                    con.setRequestProperty("Cache-Control", "con-cache");
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "text/html");
                    con.setDoOutput(true);
                    con.setDoInput(true);
                    con.connect();

                    OutputStream op = con.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(op));
                    writer.write(jsonObject.toString());
                    writer.flush();
                    writer.close();

                    InputStream ip = con.getInputStream();

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