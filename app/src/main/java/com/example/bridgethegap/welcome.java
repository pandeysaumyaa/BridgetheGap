package com.example.bridgethegap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class welcome extends AppCompatActivity {

    private static final long TIMER_DURATION = 5000;
    private static final long TIMER_INTERVAL=100;

    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ConstraintLayout mainLayout2= findViewById(R.id.welcome);

        mainLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent = new Intent(welcome.this,volunteering.class);
                startActivity(intent);
            }
        });


        progressBar = findViewById(R.id.progressBar);

        countDownTimer = new CountDownTimer(TIMER_DURATION,TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (100 * (TIMER_DURATION - millisUntilFinished) / TIMER_DURATION);
                progressBar.setProgress(progress);

            }

            @Override
            public void onFinish() {
                Intent i = new Intent(welcome.this, volunteering.class);
                startActivity(i);

            }
        };

        countDownTimer.start();







    }
}