package com.example.bridgethegap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bridgethegap.Post;
import com.example.bridgethegap.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bridgethegap.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class community extends AppCompatActivity {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postList;

    ImageView imageView;

    BottomNavigationView bottomNavigationView;

    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int currentPage = 1;
    private static final int PAGE_SIZE = 20;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        imageView = findViewById(R.id.imageView13);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mess = new Intent(community.this, messageuser.class);
                startActivity(mess);
            }
        });

        recyclerView = findViewById(R.id.postRecyclerView);

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int id = item.getItemId();

                if (id == R.id.menu_home) {
                    Intent homenav = new Intent(community.this, community.class);
                    startActivity(homenav);
                    finish();
                } else if (id == R.id.menu_add_post) {

                    Intent post = new Intent(community.this, addpost.class);
                    startActivity(post);
                    finish();
                } else if (id == R.id.menu_calendar) {
                    Intent calen = new Intent(community.this, calender.class);
                    startActivity(calen);
                    finish();
                } else if (id == R.id.menu_profile) {
                    Intent prof = new Intent(community.this, profilemain.class);
                    startActivity(prof);
                    finish();
                }
                return true;
            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        postList = new ArrayList<>();
        postAdapter = new PostAdapter(this, postList);
        recyclerView.setAdapter(postAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0
                            && totalItemCount >= PAGE_SIZE) {
                        loadMorePosts();
                    }
                }
            }
        });

        // Retrieve posts from Firestore
        retrievePosts();
    }

    private void retrievePosts() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Post post = document.toObject(Post.class);
                            postList.add(post);
                        }
                        postAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(this, "Failed to retrieve posts", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void loadMorePosts() {
        isLoading = true;
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts")
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .startAfter(postList.get(postList.size() - 1).getTimestamp())
                .limit(PAGE_SIZE)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        QuerySnapshot queryDocumentSnapshots = task.getResult();
                        if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                            // To ensure correct ordering, add new posts at the beginning of the list
                            int insertPosition = postList.size();
                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                Post post = document.toObject(Post.class);
                                postList.add(insertPosition, post);
                            }
                            postAdapter.notifyItemRangeInserted(insertPosition, queryDocumentSnapshots.size());
                        } else {
                            isLastPage = true;
                        }
                    } else {
                        Toast.makeText(this, "Failed to load more posts", Toast.LENGTH_SHORT).show();
                    }
                    isLoading = false;
                });
    }


}