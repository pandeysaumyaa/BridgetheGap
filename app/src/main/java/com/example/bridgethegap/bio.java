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

import java.util.HashMap;

public class bio extends AppCompatActivity {

    private Button continueButton;
    private EditText bioEditText;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String email;

    private String dateOfBirth;
    private int age;
    private String gender;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bio);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        continueButton = findViewById(R.id.button);
        bioEditText = findViewById(R.id.bioEditText);

        // Get user information from intent
        email = mAuth.getCurrentUser().getEmail();
        dateOfBirth = getIntent().getStringExtra("dateOfBirth");
        age = getIntent().getIntExtra("age", 0);
        gender = getIntent().getStringExtra("gender");
        location = getIntent().getStringExtra("location");

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveBioToFirestore(email, dateOfBirth, age, gender, location);
            }
        });
    }

    private void saveBioToFirestore(String email, String dateOfBirth, int age, String gender, String location) {
        String bio = bioEditText.getText().toString().trim();

        // Create a reference to the 'users' collection in Firestore
        DocumentReference userRef = db.collection("users").document(email);

        // Create a HashMap to store user data
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("dateOfBirth", dateOfBirth);
        userData.put("age", age);
        userData.put("gender", gender);
        userData.put("location", location);
        userData.put("bio", bio);

        // Set the data to the Firestore document
        userRef.set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully saved
                        Toast.makeText(bio.this, "User information saved successfully", Toast.LENGTH_SHORT).show();
                        Intent profile = new Intent(bio.this, profilepicture.class);
                        profile.putExtra("dateOfBirth", dateOfBirth);
                        profile.putExtra("email",email);
                        profile.putExtra("gender",gender);
                        profile.putExtra("location",location);
                        profile.putExtra("bio",bio);
                        startActivity(profile);
                        // Redirect to the next activity or perform any other action
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(bio.this, "Failed to save user information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
