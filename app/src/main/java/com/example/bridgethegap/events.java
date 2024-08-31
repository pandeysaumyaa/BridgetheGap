package com.example.bridgethegap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class events extends AppCompatActivity {

    private static final long TIMER_DURATION = 5000;
    private static final long TIMER_INTERVAL = 100;

    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        ConstraintLayout mainLayout6 = findViewById(R.id.events);
        mainLayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent = new Intent(events.this,donation.class);
                startActivity(intent);
            }
        });



        progressBar = findViewById(R.id.progressBar5);

        countDownTimer = new CountDownTimer(TIMER_DURATION, TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (100 * (TIMER_DURATION - millisUntilFinished) / TIMER_DURATION);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {

                Intent i5 = new Intent(events.this,donation.class);
                startActivity(i5);

            }
        };
        countDownTimer.start();


    }


    }

