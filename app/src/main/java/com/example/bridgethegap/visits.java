package com.example.bridgethegap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ProgressBar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class visits extends AppCompatActivity {

    private static final long TIMER_DURATION = 5000;
    private static final long TIMER_INTERVAL = 100;

    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits);

        ConstraintLayout mainLayout4 = findViewById(R.id.visits);

        mainLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent3 = new Intent(visits.this,phonecalls.class);
                startActivity(intent3);
            }
        });


        progressBar = findViewById(R.id.progressBar3);

        countDownTimer = new CountDownTimer(TIMER_DURATION, TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (100 * (TIMER_DURATION - millisUntilFinished) / TIMER_DURATION);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinish() {
                Intent i3 = new Intent(visits.this, phonecalls.class);
                startActivity(i3);
            }
        };
        countDownTimer.start();


    }
}
