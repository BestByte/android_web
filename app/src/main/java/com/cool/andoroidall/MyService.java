package com.cool.andoroidall;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        super.onCreate();
        Log.d(TAG, "onCreate: excuted!");
    }

    @Override
    public int onStartCommand(Intent intent,int falgs,int startId){
        Log.d(TAG, "onStartCommand: executed!");
        return super.onStartCommand(intent,falgs,startId);
    }
    
    @Override
    public  void  onDestroy(){
        super.onDestroy();
        Log.d(TAG, "onDestroy: executed!");
    }
}
