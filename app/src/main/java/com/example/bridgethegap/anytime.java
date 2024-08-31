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

import java.util.ArrayList;
import java.util.List;

public class anytime extends AppCompatActivity {

    Button b1,b2,b3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anytime);

        BottomNavigationView bottomNavigationView;

        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button2);
        b3 = findViewById(R.id.button3);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent any = new Intent(anytime.this, calender.class);
                startActivity(any);
                b1.setBackgroundColor(Color.parseColor("#5183CB"));
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent don = new Intent(anytime.this, anytime.class);
                startActivity(don);
                b2.setBackgroundColor(Color.parseColor("#5183CB"));

            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent don = new Intent(anytime.this, donationcal.class);
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
                    Intent h=new Intent(anytime.this, community.class);
                    startActivity(h);
                    finish(); // Finish the current activity if needed
                } else if (id == R.id.menu_add_post) {
                    Intent post2 = new Intent(anytime.this, addpost.class);
                    startActivity(post2);
                    finish();
                } else if (id == R.id.menu_calendar) {
                    Intent cal2 = new Intent(anytime.this, calender.class);
                    startActivity(cal2);

                    finish(); // Finish the current activity if needed
                } else if (id == R.id.menu_profile) {
                    Intent prof2 = new Intent(anytime.this, profilemain.class);
                    startActivity(prof2);
                    finish();

                }
                return true;
            }
        });
        retrieveEventPosts();
    }
    private void retrieveEventPosts() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                .whereEqualTo("postType", "event") // Query only donation posts
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Post> EventPosts = new ArrayList<>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Post post = document.toObject(Post.class);
                            EventPosts.add(post);
                        }
                        // Display the donation posts in the UI
                        displayEventPosts(EventPosts);
                    } else {
                        // Handle unsuccessful retrieval
                    }
                });
    }

    private void displayEventPosts(List<Post> EventPosts) {

        RecyclerView recyclerView = findViewById(R.id.postRecyclerView2);
        PostAdapter3 postAdapter = new PostAdapter3(this, EventPosts);
        recyclerView.setAdapter(postAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Display the donation posts in your UI (e.g., RecyclerView)
    }
}