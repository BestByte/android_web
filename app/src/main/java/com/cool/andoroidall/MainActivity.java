package com.cool.andoroidall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=(Button) findViewById(R.id.send_request);

        responseText=(TextView)findViewById(R.id.response_text);

        button.setOnClickListener(this);
    }

    @Override
    public  void  onClick(View v) {
        if (v.getId() == R.id.send_request) {
            //sendRequestWithHttpUrlConnection();
            sendRequestWithOkHttp();
        }
    }
    private void sendRequestWithHttpUrlConnection(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
            }
        });

    }

    private  void sendRequestWithOkHttp(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               try {
                 OkHttpClient okHttpClient=new OkHttpClient();
                   Request request=new Request.Builder()
                           .url("http://www.baidu.com")
                           .build();

                   Response response=okHttpClient.newCall(request).execute();
                   String responseData=response.body().toString();
                    showResponse(responseData);
               }
               catch (IOException exception) {
                   exception.printStackTrace();
               }
           }
       }).start();
    }
private  void  showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
}

}
