package com.example.androidwear;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends WearableActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Enables Always-on
        setAmbientEnabled();
    }

    public void speechConverter(View view) {
        Intent intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent,0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==0 && resultCode==RESULT_OK)
        {
            List<String>  results= data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            for (String result : results)
                getNotify(result);
        }
    }

    private void getNotify(String result) {
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel notificationChannel=new NotificationChannel("default","test",NotificationManager.IMPORTANCE_DEFAULT);
        notificationManager.createNotificationChannel(notificationChannel);

        Intent intent=new Intent(this, MainActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);

        Notification.Builder builder=new Notification.Builder(this,"default")
                .setContentTitle("Result")
                .setContentText(result)
                .setSmallIcon(android.R.drawable.sym_def_app_icon)
                .setContentIntent(pendingIntent);
        notificationManager.notify(0,builder.build());

    }

}