package com.healthy.healthyapplication;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

public class StartActivity extends AppCompatActivity {

    ImageView back ;
    EditText content ;
    EditText hour ;
    Spinner spinner ;
    Button button ;
    private static Toast sToast;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        // 토스트 중복 방지
        sToast = Toast.makeText(this, "null", Toast.LENGTH_SHORT);

        // 멤버 아이디
        Intent intent = getIntent();
        String memberId = intent.getStringExtra("memberId");

        back = (ImageView)findViewById(R.id.back);
        content = (EditText)findViewById(R.id.content_start);
        hour = (EditText)findViewById(R.id.time);
        spinner = (Spinner)findViewById(R.id.item);
        button = (Button)findViewById(R.id.btn_start);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.item, R.layout.spinner);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        // 뒤로가기 버튼
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
            }
        });

        // 완료 버튼
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // 통신 처리
                new StartActivity.JSONTask().execute("http://192.168.35.53:8080/api/diary/"+memberId+"/insert");

                sToast.setText("작성이 완료되었습니다.");
                sToast.show();
                Intent intent = new Intent(getApplicationContext(),
                        MainActivity.class);
                intent.putExtra("memberId", memberId);
                startActivity(intent);
            }
        });

    }

    public class JSONTask extends AsyncTask<String, String, String> {

        String diaryContent = content.getText().toString();
        String diaryField = spinner.getSelectedItem().toString();
        int diaryHour = Integer.parseInt(hour.getText().toString());

        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.accumulate("content", diaryContent);
                jsonObject.accumulate("field", diaryField);
                jsonObject.accumulate("hour", diaryHour);

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