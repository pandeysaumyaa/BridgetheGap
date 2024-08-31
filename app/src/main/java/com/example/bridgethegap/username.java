package com.example.bridgethegap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;

public class username extends AppCompatActivity {

    private Button nextButton ;
    private EditText usernameEditText;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private StorageReference mStorageRef;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String location;
    private String bio;

    private String profilePictureUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_username);

        nextButton = findViewById(R.id.button);
        usernameEditText = findViewById(R.id.usernameEditText);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Get user information from intent
        email = getIntent().getStringExtra("email");
        dateOfBirth = getIntent().getStringExtra("dateOfBirth");
        gender = getIntent().getStringExtra("gender");
        location = getIntent().getStringExtra("location");
        bio = getIntent().getStringExtra("bio");
        profilePictureUrl = getIntent().getStringExtra("profilePictureUrl");

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                saveUsernameToFirestore(username,email, dateOfBirth, gender, location,profilePictureUrl);
            }
        });
    }

    private void saveUsernameToFirestore(String username,String email, String dateOfBirth, String gender, String location,String imageUrl) {
        // Create a reference to the 'users' collection in Firestore
        DocumentReference userRef = db.collection("users").document(email);

        // Create a HashMap to store username data
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("username", username);
        userData.put("email", email);
        userData.put("dateOfBirth", dateOfBirth);
        userData.put("gender", gender);
        userData.put("location", location);
        userData.put("bio", bio);
        userData.put("profilePictureUrl", imageUrl);

        // Set the data to the Firestore document
        userRef.update(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully saved
                        Toast.makeText(username.this, "Username saved successfully", Toast.LENGTH_SHORT).show();
                        // Navigate to the next activity
                        startActivity(new Intent(username.this, community.class));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(username.this, "Failed to save username: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
