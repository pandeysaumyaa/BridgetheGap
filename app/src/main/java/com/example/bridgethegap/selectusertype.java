package com.example.bridgethegap;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectusertype extends AppCompatActivity {

    Button b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectusertype);

        b1 = findViewById(R.id.buttonelder);
        b2 = findViewById(R.id.buttonyouth);
        b3 = findViewById(R.id.buttoncont);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = ContextCompat.getColor(selectusertype.this, R.color.bluepale_darker);
                b1.setBackgroundColor(color);
                b2.setBackgroundColor(ContextCompat.getColor(selectusertype.this, android.R.color.transparent));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = ContextCompat.getColor(selectusertype.this, R.color.bluepale_darker);
                b2.setBackgroundColor(color);
                b1.setBackgroundColor(ContextCompat.getColor(selectusertype.this, android.R.color.transparent));
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dob = new Intent(selectusertype.this, dateofbirth.class);
                startActivity(dob);
            }
        });
    }
}
