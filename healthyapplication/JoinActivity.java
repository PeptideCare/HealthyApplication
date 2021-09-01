package com.healthy.healthyapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class JoinActivity extends AppCompatActivity{

    EditText id;
    EditText pw;
    EditText nickname;
    Spinner sex;
    EditText height;

    EditText weight;
    Button button;
    String savedMemberId = "";

    private static Toast sToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        // 토스트 중복 방지
        sToast = Toast.makeText(this, "null", Toast.LENGTH_SHORT);

        id = (EditText) findViewById(R.id.id_join);
        pw = (EditText) findViewById(R.id.pw_join);
        nickname = (EditText) findViewById(R.id.nickname_join);
        sex = (Spinner) findViewById(R.id.sex_join);
        height = (EditText) findViewById(R.id.height_join);
        weight = (EditText) findViewById(R.id.weight_join);
        button = (Button) findViewById(R.id.btn_join);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.sex, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sex.setAdapter(adapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                // 유효성 검사
//                if (id.getText().toString().length() <= 1) {
//                    sToast.setText("2글자 이상의 아이디를 입력해주세요.");
//                    sToast.show();
//                } else if (pw.getText().toString().length() <= 2) {
//                    sToast.setText("2글자 이상의 비밀번호를 입력해주세요.");
//                    sToast.show();
//                } else if (nickname.getText().toString().length() <= 2) {
//                    sToast.setText("2글자 이상의 닉네임을 입력해주세요.");
//                    sToast.show();
//                } else if (sex.getText().toString().length() == 0) {
//                    sToast.setText("성별을 입력해주세요.");
//                    sToast.show();
//                } else if (height.getText().toString().length() <= 1) {
//                    sToast.setText("키를 입력해주세요.");
//                    sToast.show();
//                } else if (weight.getText().toString().length() <= 1) {
//                    sToast.setText("몸무게를 입력해주세요.");
//                    sToast.show();
//                }

                // 통신 처리
                new JSONTask().execute("http://192.168.35.53:8080/api/member/insert");

                // 회원 중복 검사
                if (!savedMemberId.equals("0")) {
                    sToast.setText("회원가입이 완료되었습니다.");
                    sToast.show();
                    Intent intent = new Intent(getApplicationContext(),
                            LoginActivity.class);
                    startActivity(intent);
                }
                else {
                    sToast.setText("이미 존재하는 회원입니다.");
                    sToast.show();
                }
            }
        });
    }
    public class JSONTask extends AsyncTask<String, String, String> {

        String member_id = id.getText().toString();
        String member_pw = pw.getText().toString();
        String member_nick = nickname.getText().toString();
        String member_sex = sex.getSelectedItem().toString();
        double member_height = Double.parseDouble(height.getText().toString());
        double member_weight = Double.parseDouble(weight.getText().toString());

        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("id", member_id);
                jsonObject.accumulate("pw", member_pw);
                jsonObject.accumulate("nickname", member_nick);
                jsonObject.accumulate("sex", member_sex);
                jsonObject.accumulate("height", member_height);
                jsonObject.accumulate("weight", member_weight);

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

//                    String data = buffer.toString();
                    savedMemberId = buffer.toString();

//                    JSONArray root = new JSONArray(data);
//
//                    for (int i=0; i<root.length(); i++) {
//                        JSONObject obj = root.getJSONObject(i);
//
//                        savedMemberId = obj.getString("id");
//                    }

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