package com.example.okhttpdemo;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    private Button button;


    Handler handler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 111) {
                Toast.makeText(MainActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        init();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }

    private void init() {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        RequestBody body = new FormBody.Builder().add("wd", "123").build();
        Request request = new Request.Builder()
                .url("http://www.baidu.com")
                .post(body)
                .build();
        Call call = mOkHttpClient.newCall(request);
        //请求回调
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                button.setText("false");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg = Message.obtain();
                msg.obj = response.body().string();
                msg.what = 111;
                handler.sendMessage(msg);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        button.setText("true");
                    }
                });

            }
        });
    }




}
