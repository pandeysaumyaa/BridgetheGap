package com.example.bridgethegap;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.io.IOException;

public class realtime extends AppCompatActivity {

    private static final int CAMERA_PERMISSION_CODE = 100;

    private Camera camera;
    private SurfaceView cameraPreview;
    private SurfaceHolder surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realtime);

        cameraPreview = findViewById(R.id.cameraPreview);
        surfaceHolder = cameraPreview.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(realtime.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(realtime.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
                    return;
                }
                startCameraPreview();
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
                if (camera != null) {
                    camera.stopPreview();
                    startCameraPreview();
                }
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                if (camera != null) {
                    camera.release();
                    camera = null;
                }
            }
        });

        Button captureButton = findViewById(R.id.captureButton);
        captureButton.setOnClickListener(v -> {
            if (camera != null) {
                camera.takePicture(null, null, (data, camera) -> {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                    // Pass this bitmap to the next step for facial verification
                    verifyFace(bitmap);
                });
            }
        });
    }

    private void startCameraPreview() {
        camera = Camera.open();
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (RuntimeException e) {
            e.printStackTrace();
            Log.e("CameraError", "Camera failed to open: " + e.getMessage(), e);
            Toast.makeText(this, "Failed to open camera. Make sure no other application is using the camera.", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyFace(Bitmap realtimePhoto) {
        // Implement the facial verification logic here
        // For now, let's just show a toast message
        Toast.makeText(this, "Photo captured. Implement verification next.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCameraPreview();
            } else {
                Toast.makeText(this, "Camera permission is required to use this feature", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
