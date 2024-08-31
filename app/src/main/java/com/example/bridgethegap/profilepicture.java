package com.example.bridgethegap;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class profilepicture extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private Button buttonsave;
    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private FirebaseFirestore mFirestore;
    private ImageView profileImageView;
    private Uri mImageUri;
    private String email;
    private String dateOfBirth;
    private String gender;
    private String location;
    private String bio;
    private StorageTask uploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepicture);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference("profile_pictures");
        mFirestore = FirebaseFirestore.getInstance();
        email = getIntent().getStringExtra("email");
        dateOfBirth = getIntent().getStringExtra("dateOfBirth");
        gender = getIntent().getStringExtra("gender");
        location = getIntent().getStringExtra("location");
        bio = getIntent().getStringExtra("bio");
        profileImageView = findViewById(R.id.profileImageView);
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            profileImageView.setImageURI(mImageUri);
            uploadImage();
        }
    }
    private void uploadImage() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            uploadTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(profilepicture.this, "Upload successful", Toast.LENGTH_SHORT).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    saveUserData(downloadUri.toString());
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(profilepicture.this, "Upload failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUserData(String imageUrl) {
        DocumentReference userRef = mFirestore.collection("users").document(email);

        Map<String, Object> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("dateOfBirth", dateOfBirth);
        userData.put("gender", gender);
        userData.put("location", location);
        userData.put("bio", bio);
        userData.put("profilePictureUrl", imageUrl);

        userRef.set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(profilepicture.this, "User data saved successfully", Toast.LENGTH_SHORT).show();
                        // Redirect to the next activity or perform any other action
                        Intent nextIntent = new Intent(profilepicture.this, username.class);
                        nextIntent.putExtra("dateOfBirth", dateOfBirth);
                        nextIntent.putExtra("email",email);
                        nextIntent.putExtra("gender",gender);
                        nextIntent.putExtra("location",location);
                        nextIntent.putExtra("bio",bio);
                        nextIntent.putExtra("profilePictureUrl",imageUrl);
                        startActivity(nextIntent);
                        finish(); // Finish current activity
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(profilepicture.this, "Failed to save user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



    private String getFileExtension(Uri uri) {
        String extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(getContentResolver().getType(uri));
        Log.d("FileExtension", "File extension: " + extension); // Add this line for logging
        return extension;
    }
}
