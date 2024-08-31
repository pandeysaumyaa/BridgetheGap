package com.example.bridgethegap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class volunteering extends AppCompatActivity {

    private static final long TIMER_DURATION = 5000;
    private static final long TIMER_INTERVAL = 100;

    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteering);

        ConstraintLayout mainLayout3 = findViewById(R.id.volunteering);

        mainLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent2 = new Intent(volunteering.this,visits.class);
                startActivity(intent2);
            }
        });


        progressBar = findViewById(R.id.progressBar2);

        countDownTimer = new CountDownTimer(TIMER_DURATION, TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (100 * (TIMER_DURATION - millisUntilFinished) / TIMER_DURATION);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                Intent i2 = new Intent(volunteering.this, visits.class);
                startActivity(i2);
            }
        };

        countDownTimer.start();


    }

}
