package com.example.kansimeannet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import com.example.kansimeannet.TraceMovemenentService;

public class PhoneStartedReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {

       Intent service=new Intent(context, TraceMovemenentService.class);
       context.startActivity(intent);
            Toast.makeText(context, "Phone Started. Tracing Activated", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(service);
        }
        else{
            context.startService(service);
        }

    }

}
