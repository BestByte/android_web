package com.cool.andoroidall;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.cool.andoroidall.web.xml.MyContentHandler;

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

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.SAXParserFactory;

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
                           .url("http://10.0.2.2/get_data.json")
                           .build();

                   Response response=okHttpClient.newCall(request).execute();
                   String responseData=response.body().string();
                   // parseXMLWithSAX(responseData);
                   parseJSONWithObject(responseData);
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
}
