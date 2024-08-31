package com.example.bridgethegap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class profilemain extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseFirestore db;
    BottomNavigationView bottomNavigationView;
    TextView usernameTextView;
    TextView locationTextView;
    TextView biotextview;
    ImageView profileImageView;

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilemain);

        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.menu_home) {
                    Intent home = new Intent(profilemain.this, community.class);
                    startActivity(home);
                    finish();
                } else if (id == R.id.menu_add_post) {
                    Intent post = new Intent(profilemain.this, addpost.class);
                    startActivity(post);
                    finish();
                } else if (id == R.id.menu_calendar) {
                    Intent cal = new Intent(profilemain.this, calender.class);
                    startActivity(cal);

                    finish();
                } else if (id == R.id.menu_profile) {
                }
                return true;
            }
        });

        recyclerView = findViewById(R.id.postRecyclerView2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postList);
        recyclerView.setAdapter(postAdapter);

        // Accessing views inside onCreate
        usernameTextView = findViewById(R.id.textViewusername);
        locationTextView = findViewById(R.id.textViewlocation);
        biotextview = findViewById(R.id.textViewbio);
        profileImageView = findViewById(R.id.imageView11);

        if (user != null) {
            retrieveUserProfile();
        }
    }

    private void retrieveUserProfile() {
        String userEmail = user.getEmail();
        db.collection("users").document(userEmail).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String username = Objects.requireNonNull(document.getString("username"));
                            String location = Objects.requireNonNull(document.getString("location"));
                            String bio = Objects.requireNonNull(document.getString("bio"));
                            String imageUrl = Objects.requireNonNull(document.getString("profilePictureUrl"));

                            usernameTextView.setText(username);
                            locationTextView.setText(location);
                            biotextview.setText(bio);
                            Picasso.get().load(imageUrl).into(profileImageView);

                            retrieveUserPosts(userEmail);
                        } else {
                            Toast.makeText(profilemain.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(profilemain.this, "Failed with: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void retrieveUserPosts(String userEmail) {
        db.collection("posts")
                .whereEqualTo("userEmail", userEmail)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        postList.clear();
                        for (DocumentSnapshot document : task.getResult()) {
                            Post post = document.toObject(Post.class);
                            postList.add(post);
                        }
                        postAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(profilemain.this, "Failed to retrieve posts", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
