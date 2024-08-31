package com.example.bridgethegap;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

public class addpost extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    private static final int PICK_IMAGE_REQUEST = 1;

    private Button select_photo_button;

    private ImageButton post_button;
    private FirebaseAuth mAuth;

    private String caption;
    private StorageReference mStorageRef;
    private FirebaseFirestore mFirestore;

    private ImageView selected_photo;
    private Uri mImageUri;

    private String email;
    private String username;
    private String dateOfBirth;
    private String gender;
    private String location;
    private String bio;

    private StorageTask uploadTask;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost);

        selected_photo = findViewById(R.id.selected_photo);
        post_button = findViewById(R.id.post_button);
        post_button.setVisibility(View.GONE);
        caption = String.valueOf(findViewById(R.id.caption_edit_text));
        username = getIntent().getStringExtra("username");
        email = getIntent().getStringExtra("email");

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.menu_add_post);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.menu_home) {
                    Intent h = new Intent(addpost.this, community.class);
                    startActivity(h);
                    finish(); // Finish the current activity if needed
                } else if (id == R.id.menu_add_post) {
                    // Already in the addpost activity
                } else if (id == R.id.menu_calendar) {
                    Intent cal2 = new Intent(addpost.this, calender.class);
                    startActivity(cal2);
                    finish(); // Finish the current activity if needed
                } else if (id == R.id.menu_profile) {
                    Intent prof2 = new Intent(addpost.this, profilemain.class);
                    startActivity(prof2);
                    finish();
                }
                return true;
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");

        // Get user information from intent
        email = getIntent().getStringExtra("email");
        username = getIntent().getStringExtra("username");
        dateOfBirth = getIntent().getStringExtra("dateOfBirth");
        gender = getIntent().getStringExtra("gender");
        location = getIntent().getStringExtra("location");
        bio = getIntent().getStringExtra("bio");

        select_photo_button = findViewById(R.id.select_photo_button);
        select_photo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        EditText captionEditText = findViewById(R.id.caption_edit_text);
        captionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().isEmpty()) {

                    post_button.setVisibility(View.GONE);
                } else {
                    post_button.setVisibility(View.VISIBLE);
                }
            }
        });

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadPost();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            if (selectedImageUri != null) {
                // Load the selected image into the selectedPhoto ImageView
                Glide.with(this).load(selectedImageUri).into(selected_photo);
                uploadImageToFirebase(selectedImageUri);
            }
        }
    }

    private void uploadImageToFirebase(Uri imageUri) {
        if (imageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() + "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(addpost.this, "Upload successful", Toast.LENGTH_SHORT).show();


                            post_button.setVisibility(View.VISIBLE);

                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    mImageUri = uri;
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Handle failure to retrieve download URL
                                    Toast.makeText(addpost.this, "Failed to get download URL", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle unsuccessful upload
                            Toast.makeText(addpost.this, "Upload failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadPost() {
        caption = ((EditText) findViewById(R.id.caption_edit_text)).getText().toString().trim();

        if (caption.isEmpty()) {
            Toast.makeText(this, "Please enter a caption", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mImageUri != null) {
            addPostToDatabase(mImageUri.toString(), caption);
        } else {

            Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show();
        }
    }

    private void addPostToDatabase(String imageUrl, String caption) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {

            String userEmail = currentUser.getEmail();

            String postType = "friendly";
            if (caption.toLowerCase().contains("join")) {
                postType = "event";
            } else if (caption.toLowerCase().contains("donate")) {
                postType = "donation";
            }

            Post post = new Post(imageUrl, caption, userEmail, postType);

            db.collection("posts")
                    .add(post)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(addpost.this, "Post added to database", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(addpost.this, community.class);
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(addpost.this, "Failed to add post to database", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(addpost.this, "User not logged in", Toast.LENGTH_SHORT).show();
        }
    }
}

