package com.healthy.healthyapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {

    EditText id;
    EditText pw;
    Button joinbtn;
    Button loginbtn;
    String findMemberId = "";

    private static Toast sToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 토스트 중복 방지
        sToast = Toast.makeText(this, "null", Toast.LENGTH_SHORT);

        id = (EditText)findViewById(R.id.id_login);
        pw = (EditText)findViewById(R.id.pw_login);
        joinbtn = (Button)findViewById(R.id.joinbtn_login);
        loginbtn = (Button)findViewById(R.id.loginbtn_login);

        joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        JoinActivity.class);
                startActivity(intent);
            }
        });

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 통신 처리
                new LoginActivity.JSONTask().execute("http://192.168.35.53:8080/api/member/login");

                if (findMemberId.equals("0")) {
                    sToast.setText("회원정보가 일치하지 않습니다.");
                    sToast.show();
                } else {
                    sToast.setText("로그인이 완료되었습니다.");
                    sToast.show();

                    Intent intent = new Intent(getApplicationContext(),
                            MainActivity.class);
                    intent.putExtra("memberId", id.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        String member_id = id.getText().toString();
        String member_pw = pw.getText().toString();

        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", member_id);
                jsonObject.accumulate("pw", member_pw);

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
                    reader = new BufferedReader(new InputStreamReader(ip));
                    StringBuffer buffer = new StringBuffer();
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    findMemberId = buffer.toString();

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