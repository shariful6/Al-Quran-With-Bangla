package com.shariful.alquran2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.ProgressBar;

public class SplashScreen extends AppCompatActivity {

    ProgressBar progressBar ;
    int progressValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        progressBar = findViewById(R.id.progreesbar_id);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                  doWork();
            }
        });
        thread.start();

    }

    private void doWork() {
         for (progressValue = 2;progressValue<= 100;progressValue=progressValue+2){

             try {
                 Thread.sleep(30);
                 progressBar.setProgress(progressValue);
             }
             catch (InterruptedException e) {
                 e.printStackTrace();
             }
         }
        Intent intent =new Intent(SplashScreen.this,MainActivity.class);
        startActivity(intent);
        finish();

    }
}