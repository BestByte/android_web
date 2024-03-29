package com.cool.andoroidall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.cool.andoroidall.web.gson.App;
import com.cool.andoroidall.web.xml.MyContentHandler;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

public  static final int UPDATE_TEXT=1;

private Handler handler=new Handler(){
    public  void  handleMessage(Message message){
        switch (message.what){
            case UPDATE_TEXT:
                text.setText("nice to meet you");break;
                default:break;
        }
    }
};
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button=(Button) findViewById(R.id.send_request);

        Button startService=(Button)findViewById(R.id.start_service);
        Button stopService=(Button)findViewById(R.id.stop_service);

        startService.setOnClickListener(this);
        stopService.setOnClickListener(this);
        //responseText=(TextView)findViewById(R.id.response_text);
        text=(TextView)findViewById(R.id.thread_text);
        Button changeText=(Button)findViewById(R.id.change_text);
        changeText.setOnClickListener(this);
        button.setOnClickListener(this);
    }

    @Override
    public  void  onClick(View v) {
        if (v.getId() == R.id.send_request) {
            //sendRequestWithHttpUrlConnection();
            sendRequestWithOkHttp();
        }
        if (v.getId() == R.id.change_text) {
            new Thread(new Runnable() {
                @Override
                public void run() {

                    Message message = new Message();
                    message.what = UPDATE_TEXT;
                    handler.sendMessage(message);
                }
            }).start();
        }

        if (v.getId() == R.id.start_service) {
            Intent startIntent = new Intent(this, MyService.class);
            startService(startIntent);
        }
        if (v.getId() == R.id.stop_service) {
            Intent stopIntent = new Intent(this, MyService.class);
            stopService(stopIntent);
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
                           .url("http://10.0.2.2/get_data.json")
                           .build();

                   Response response=okHttpClient.newCall(request).execute();
                   String responseData=response.body().string();
                    //parseXMLWithSAX(responseData);
                 //  parseJSONWithObject(responseData);
                   parseJsonWithGSON(responseData);
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
                //responseText.setText(response);
            }
        });
}

private void parseXMLWithPull(String xmlData){

}

private  void parseXMLWithSAX(String xmlData){
        try{
            SAXParserFactory saxParserFactory=SAXParserFactory.newInstance();
            XMLReader xmlReader=saxParserFactory.newSAXParser().getXMLReader();

            MyContentHandler contentHandler =new MyContentHandler() ;
            xmlReader.setContentHandler(contentHandler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        }
        catch (Exception e){
            e.printStackTrace();
        }
}
private void parseJSONWithObject(String jsonData){
        try{
            JSONArray jsonArray=new JSONArray(jsonData);
            for (int i=0;i<jsonArray.length();i++){
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                String id=jsonObject.getString("id");
                String name=jsonObject.getString("name");
                String version=jsonObject.getString("version");
                Log.d("Main", "parseJSONWithObject: name is"+name);
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }
}

private  void  parseJsonWithGSON(String jsonData){
    Gson gson=new Gson();
List<App> appList=gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());

for (App app:appList){
    Log.d("MainAvativity", "parseJsonWithGSON: name is :"+app.getName());
}
}
}
