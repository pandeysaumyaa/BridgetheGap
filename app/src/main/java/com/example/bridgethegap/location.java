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

public class location extends AppCompatActivity {

    private Button continueButton;
    private EditText locationEditText;

    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    private String email;
    private String username;
    private String dateOfBirth;
    private int age;
    private String gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        continueButton = findViewById(R.id.buttoncontlo);
        locationEditText = findViewById(R.id.loginEmailEditText);

        // Get user information from intent
        email = mAuth.getCurrentUser().getEmail();
        dateOfBirth = getIntent().getStringExtra("dateOfBirth");
        age = getIntent().getIntExtra("age", 0);
        gender = getIntent().getStringExtra("gender");

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocationAndBioToFirestore(email,  dateOfBirth, age, gender);
            }
        });
    }

    private void saveLocationAndBioToFirestore(String email,  String dateOfBirth, int age, String gender) {
        String location = locationEditText.getText().toString().trim();

        // Create a reference to the 'users' collection in Firestore
        DocumentReference userRef = db.collection("users").document(email);

        // Create a HashMap to store user data
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("dateOfBirth", dateOfBirth);
        userData.put("age", age);
        userData.put("gender", gender);
        userData.put("location", location);

        // Set the data to the Firestore document
        userRef.set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully saved
                        Toast.makeText(location.this, "User information saved successfully", Toast.LENGTH_SHORT).show();
                        // Redirect to the next activity
                        Intent nextIntent = new Intent(location.this, bio.class);
                        nextIntent.putExtra("dateOfBirth", dateOfBirth);
                        nextIntent.putExtra("email",email);
                        nextIntent.putExtra("gender",gender);
                        nextIntent.putExtra("location",location);
                        startActivity(nextIntent);
                        finish(); // Finish current activity
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(location.this, "Failed to save user information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
