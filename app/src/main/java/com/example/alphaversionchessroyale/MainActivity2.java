package com.example.alphaversionchessroyale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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

    private final int GALLERY_REQ_CODE = 1000;
    ImageView imgGallery;
    StorageReference storageRef;
    Uri image;
    String name;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imgGallery = findViewById(R.id.imgGallery);
        Button btnGallery = findViewById(R.id.galleryButton);
        FirebaseApp.initializeApp(MainActivity2.this);
        storageReference = FirebaseStorage.getInstance().getReference();


        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent iGallery = new Intent(Intent.ACTION_PICK);
                iGallery.setType("image/*");
                iGallery.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(iGallery, GALLERY_REQ_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==GALLERY_REQ_CODE){
                name =UUID.randomUUID().toString();
                StorageReference refrence = storageReference.child("images/" + name);
                refrence.putFile(data.getData()).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(MainActivity2.this, "Uploaded!", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(MainActivity2.this, "Uplaod Failed!", Toast.LENGTH_SHORT).show();
                    }
                });


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

        } else if (st.equals("Activity 4")) {

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
}