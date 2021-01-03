package com.example.kansimeannet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {
    private Double latitude,longitude;
    private static final int REQUEST_LOCATION_CODE = 1;
    TextView message,loc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        message = findViewById(R.id.msg);
        loc = findViewById(R.id.location);

        message.setText(message.getText().toString()+getIntent().getExtras().getString("message"));

startServiceNow();


    }

    public void startServiceNow(){
        try {
            Intent intent = new Intent(getApplicationContext(), TraceMovemenentService.class);
            startService(intent);
            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));
            loc.setText("You're at Latitude "+String.valueOf(latitude)+" and Longitude "+String.valueOf(longitude)+" Basing on your GPS Location Tracing");


        }catch (Exception p){
            Toast.makeText(this, ""+p, Toast.LENGTH_SHORT).show();
            loc.setText("You're at Latitude "+String.valueOf("0.6167")+" and Longitude "+String.valueOf("30.6568")+" Basing on your GPS Location Tracing");

        }
    }


    public boolean checkPermissionForGPS(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
                new AlertDialog.Builder(this)
                        .setTitle("Permission Request")
                        .setCancelable(false)
                        .setMessage("For proper functionality of this application, you must give permission to location and enable gps. Thanks")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(HomeActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
                            }
                        }).create().show();
            }
            else{
                ActivityCompat.requestPermissions(HomeActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION_CODE);
            }
            return false;
        }
        else {
            return true;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case REQUEST_LOCATION_CODE:{
                if (grantResults.length >0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                    if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED){
                        checkPermissionForGPS();
                    }
                }

            }
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            latitude = Double.valueOf(intent.getStringExtra("latutide"));
            longitude = Double.valueOf(intent.getStringExtra("longitude"));
            try {
                Toast.makeText(context, "Longitude "+longitude+" Lat: "+latitude, Toast.LENGTH_SHORT).show();
                loc.setText("You're at Latitude "+String.valueOf(latitude)+" and Longitude "+String.valueOf(longitude)+" Basing on your GPS Location Tracing");

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter(TraceMovemenentService.str_receiver));
    }
}