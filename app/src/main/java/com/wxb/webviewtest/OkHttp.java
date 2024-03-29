package com.wxb.webviewtest;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp extends AppCompatActivity {
private TextView textView;
private Handler handler=new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what){
            case 1:
                ((TextView)findViewById(R.id.textView1)).setText("子线程操作");
            break;

        }
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http);
        textView=(TextView) findViewById(R.id.textView);
        ((Button)findViewById(R.id.send)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp();
            }
        });
    }
    private  void sendRequestWithOkHttp(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient=new OkHttpClient();
                    Request request=new Request
                        .Builder()
                        .url("https://www.baidu.com")
                        .build();
                    Response response = okHttpClient.newCall(request).execute();
                    String responseData=response.body().string();
                    showResponse(responseData);
                    Message message=new Message();
                    message.what=1;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
    private void showResponse(final  String  response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText(response);
            }
        });


    }

}
