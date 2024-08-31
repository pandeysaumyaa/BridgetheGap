package com.example.bridgethegap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class calender extends AppCompatActivity {

    Button b1,b2,b3;

    BottomNavigationView bottomNavigationView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calender);

        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent any = new Intent(calender.this, calender.class);
                startActivity(any);
                b1.setBackgroundColor(Color.parseColor("#5183CB"));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent don = new Intent(calender.this, anytime.class);
                startActivity(don);
                b2.setBackgroundColor(Color.parseColor("#5183CB"));

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent don = new Intent(calender.this, donationcal.class);
                startActivity(don);
                b3.setBackgroundColor(Color.parseColor("#5183CB"));

            }
        });



        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_calendar);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.menu_home) {
                    Intent h=new Intent(calender.this, community.class);
                    startActivity(h);
                    finish(); // Finish the current activity if needed
                } else if (id == R.id.menu_add_post) {
                    Intent post2 = new Intent(calender.this, addpost.class);
                    startActivity(post2);
                    finish();
                } else if (id == R.id.menu_calendar) {
                    Intent cal2 = new Intent(calender.this, calender.class);
                    startActivity(cal2);

                    finish(); // Finish the current activity if needed
                } else if (id == R.id.menu_profile) {
                    Intent prof2 = new Intent(calender.this, profilemain.class);
                    startActivity(prof2);
                    finish();

                }
                return true;
            }
        });

    }
}