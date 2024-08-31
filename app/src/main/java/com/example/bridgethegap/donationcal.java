package com.example.bridgethegap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class donationcal extends AppCompatActivity {

    Button b1, b2, b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donationcal);

        BottomNavigationView bottomNavigationView;

        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent any = new Intent(donationcal.this, calender.class);
                startActivity(any);
                b1.setBackgroundColor(Color.parseColor("#5183CB"));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent don = new Intent(donationcal.this, anytime.class);
                startActivity(don);
                b2.setBackgroundColor(Color.parseColor("#5183CB"));

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent don = new Intent(donationcal.this, donationcal.class);
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
                    Intent h = new Intent(donationcal.this, community.class);
                    startActivity(h);
                    finish(); // Finish the current activity if needed
                } else if (id == R.id.menu_add_post) {
                    Intent post2 = new Intent(donationcal.this, addpost.class);
                    startActivity(post2);
                    finish();
                } else if (id == R.id.menu_calendar) {
                    Intent cal2 = new Intent(donationcal.this, calender.class);
                    startActivity(cal2);

                    finish(); // Finish the current activity if needed
                } else if (id == R.id.menu_profile) {
                    Intent prof2 = new Intent(donationcal.this, profilemain.class);
                    startActivity(prof2);
                    finish();

                }
                return true;
            }
        });

        // Retrieve only donation posts from Firestore
        retrieveDonationPosts();
    }

    private void retrieveDonationPosts() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                .whereEqualTo("postType", "donation") // Query only donation posts
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Post> donationPosts = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Post post = document.toObject(Post.class);
                            donationPosts.add(post);
                        }
                        // Display the donation posts in the UI
                        displayDonationPosts(donationPosts);
                    } else {
                        // Handle unsuccessful retrieval
                    }
                });
    }

    private void displayDonationPosts(List<Post> donationPosts) {

        RecyclerView recyclerView = findViewById(R.id.postRecyclerView);
        PostAdapter2 postAdapter = new PostAdapter2(this, donationPosts);
        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Display the donation posts in your UI (e.g., RecyclerView)
    }
}
