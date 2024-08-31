package com.example.bridgethegap;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class oldgender extends AppCompatActivity {

    private Button saveButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String selectedGender = "gender";
    private String dateOfBirth;

    private String finalage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oldgender);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        saveButton = findViewById(R.id.saveButton);
        ImageView imageViewMale = findViewById(R.id.imageView);
        ImageView imageViewFemale = findViewById(R.id.imageView2);

        imageViewMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedGender = "Male";
                Toast.makeText(oldgender.this, "Male selected", Toast.LENGTH_SHORT).show();
            }
        });

        // Set OnClickListener for imageViewFemale
        imageViewFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedGender = "Female"; // Set selected gender to Female
                Toast.makeText(oldgender.this, "Female selected", Toast.LENGTH_SHORT).show();
            }
        });

        String email = mAuth.getCurrentUser().getEmail();
        dateOfBirth = getIntent().getStringExtra("dateOfBirth");

        // Set onClickListener for saveButton
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get username and email from intent
                //String username = getIntent().getStringExtra("username");
                //String email = mAuth.getCurrentUser().getEmail();

                // Save user info to Firestore
                saveUserDatatoFirestore(email,  selectedGender);
            }
        });
    }

    private void saveUserDatatoFirestore(String email, String gender) {
        // Create a reference to the 'users' collection in Firestore
        DocumentReference userRef = db.collection("users").document(email);

        // Create a HashMap to store user data
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("dateOfBirth", dateOfBirth);
        userData.put("gender", gender);

        // Set the data to the Firestore document
        userRef.set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully saved
                        Toast.makeText(oldgender.this, "User information saved successfully", Toast.LENGTH_SHORT).show();
                        // Redirect to the next activity
                        Intent nextIntent = new Intent(oldgender.this, location.class);
                        nextIntent.putExtra("dateOfBirth", dateOfBirth);
                        nextIntent.putExtra("email",email);
                        nextIntent.putExtra("gender",gender);
                        startActivity(nextIntent);
                        finish(); // Finish current activity
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Handle the error
                        Toast.makeText(oldgender.this, "Failed to save user information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}