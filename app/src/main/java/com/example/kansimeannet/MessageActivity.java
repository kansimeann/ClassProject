package com.example.kansimeannet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MessageActivity extends AppCompatActivity {
    EditText message;
    Button sendMessage;
    SqliteHelperClass sqliteHelperClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        message=findViewById(R.id.message);
        sendMessage=findViewById(R.id.sendMessageButton);
        sqliteHelperClass=new SqliteHelperClass(this);

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(message.getText().toString().trim())){
                    message.setError("Message cannot be empty");
                    Toast.makeText(MessageActivity.this, "Cannot start new activity. Reason Greeting message is empty", Toast.LENGTH_LONG).show();
                }
                else if (message.getText().toString().trim().length()<5){
                    message.setError("Message cannot of that length");
                    Toast.makeText(MessageActivity.this, "Cannot start new activity. Reason Greeting message is too short. At least 5 characters", Toast.LENGTH_LONG).show();
                }
                else{

                    boolean send=sqliteHelperClass.sendData(message.getText().toString().trim());
                    if (send==true){
                        Toast.makeText(getApplicationContext(),"Message Captured",Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"Failed to capture your message",Toast.LENGTH_LONG).show();
                    }

                    Intent intent = new Intent(MessageActivity.this,HomeActivity.class);
                    intent.putExtra("message",message.getText().toString());
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}