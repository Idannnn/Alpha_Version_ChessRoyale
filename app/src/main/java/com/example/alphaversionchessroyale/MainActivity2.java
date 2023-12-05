package com.example.alphaversionchessroyale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class MainActivity2 extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 3;
    private final int GALLERY_REQ_CODE = 1000;
    ImageView imgGallery;
    StorageReference storageRef;
    Uri image;
    String name;
    StorageReference storageReference;
    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imgGallery = findViewById(R.id.imgGallery);
        Button btnGallery = findViewById(R.id.galleryButton);
        FirebaseApp.initializeApp(MainActivity2.this);
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission to access external storage NOT granted", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQ_CODE) {
                if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, proceed with the upload
                    name = UUID.randomUUID().toString();
                    StorageReference reference = storageReference.child("images/" + name);
                    reference.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(MainActivity2.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity2.this, "Upload Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    // Permission not granted
                    Toast.makeText(MainActivity2.this, "Permission to access external storage not granted", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        String st = item.getTitle().toString();
        if (st.equals("Activity 1")) {
            startActivity(new Intent(MainActivity2.this, MainActivity.class));
        } else if (st.equals("Activity 3")) {
            startActivity(new Intent(MainActivity2.this, MainActivity3.class));


        } else if (st.equals("Activity 4")) {
            startActivity(new Intent(MainActivity2.this, MainActivity4.class));


        }
        return super.onOptionsItemSelected(item);
    }


    public void download(View view) {
        StorageReference imageRefi = storageReference.child("images/" + name);
        long MAXBYTES = 1024*1024;
        imageRefi.getBytes(MAXBYTES).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                imgGallery.setImageBitmap(bitmap);
                Toast.makeText(MainActivity2.this, "Downloaded", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity2.this, "Download Failed!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void upload(View view) {
        // Check permission before initiating the upload
        requestPermission();
        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            // Permission granted, proceed with image selection
            Intent iGallery = new Intent(Intent.ACTION_PICK);
            iGallery.setType("image/*");
            iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(iGallery, GALLERY_REQ_CODE);
        } else {
            // Permission not granted, do nothing or show a message
            Toast.makeText(this, "Permission to access external storage is required for image upload", Toast.LENGTH_LONG).show();
        }
    }
}