package com.example.bridgethegap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class collaboration extends AppCompatActivity {

    private static final long TIMER_DURATION = 5000;
    private static final long TIMER_INTERVAL=100;

    private ProgressBar progressBar;
    private CountDownTimer countDownTimer;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collaboration);
        mAuth = FirebaseAuth.getInstance();
        ConstraintLayout mainLayout8= findViewById(R.id.collaboration);


        mainLayout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                Intent intent = new Intent(collaboration.this,home.class);
                startActivity(intent);
            }
        });



        progressBar = findViewById(R.id.progressBar7);

        countDownTimer = new CountDownTimer(TIMER_DURATION,TIMER_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (100 * (TIMER_DURATION - millisUntilFinished) / TIMER_DURATION);
                progressBar.setProgress(progress);

            }

            @Override
            public void onFinish() {

                Intent i7 = new Intent(collaboration.this, home.class);
                startActivity(i7);

            }
        };
        countDownTimer.start();


    }
}