package com.example.bridgethegap;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class dateofbirth extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText editTextDOB;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dateofbirth);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        editTextDOB = findViewById(R.id.editTextDOB);
        Button saveButton = findViewById(R.id.saveButton);


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do nothing on button click, saving info moved to onDateSet
            }
        });
    }

    public void showDatePickerDialog(View v) {
        DatePickerDialog datePickerDialog;
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, this, year, month, day);
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Set selected date to the EditText
        Calendar selectedDate = Calendar.getInstance();
        selectedDate.set(year, month, dayOfMonth);

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd - MMM - yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(selectedDate.getTime());

        editTextDOB.setText(formattedDate);

        // Get username from intent
        String email = mAuth.getCurrentUser().getEmail();

        // Save user info to Firestore
        saveUserDataToFirestore(email, formattedDate);
    }

    private void saveUserDataToFirestore(String email, String dateOfBirth) {
        // Create a reference to the 'users' collection in Firestore
        DocumentReference userRef = db.collection("users").document(email);

        SimpleDateFormat sdf = new SimpleDateFormat("dd - MMM - yyyy", Locale.getDefault());
        try {
            Calendar dobCal = Calendar.getInstance();
            dobCal.setTime(sdf.parse(dateOfBirth));

            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_YEAR) < dobCal.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            // Create a HashMap to store user data
            HashMap<String, Object> userData = new HashMap<>();
            userData.put("email", email);
            userData.put("dateOfBirth", dateOfBirth);
            userData.put("age", age);

            // Set the data to the Firestore document
            int finalAge = age;
            userRef.set(userData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            // Data successfully saved
                            Toast.makeText(dateofbirth.this, "User information saved successfully", Toast.LENGTH_SHORT).show();
                            Intent genderIntent = new Intent(dateofbirth.this, oldgender.class);
                            genderIntent.putExtra("dateOfBirth", dateOfBirth);
                            genderIntent.putExtra("age", finalAge);
                            startActivity(genderIntent);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle the error
                            Toast.makeText(dateofbirth.this, "Failed to save user information: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to parse date of birth", Toast.LENGTH_SHORT).show();
        }
    }
}
