package com.example.alphaversionchessroyale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null)
            startActivity(new Intent(MainActivity.this, acitivity_welcome.class));

    }

    public void Register(View view) {
        EditText emailEditText = findViewById(R.id.editTextTextEmailAddress);
        EditText passwordEditText = findViewById(R.id.editTextTextPassword);
        mAuth.createUserWithEmailAndPassword(
                emailEditText.getText().toString(), passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(MainActivity.this, acitivity_welcome.class));
                        } else{
                            Toast.makeText(MainActivity.this, "Register Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void Login(View view) {
        EditText emailEditText = findViewById(R.id.editTextTextEmailAddress);
        EditText passwordEditText = findViewById(R.id.editTextTextPassword);
        mAuth.signInWithEmailAndPassword(emailEditText.getText().toString() , passwordEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            startActivity(new Intent(MainActivity.this, acitivity_welcome.class));
                        } else {
                            Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        String st = item.getTitle().toString();
        if (st.equals("Activity 2")) {
            startActivity(new Intent(MainActivity.this, MainActivity2.class));
        } else if (st.equals("Activity 3")) {

        } else if (st.equals("Activity 4")) {

        }
        return super.onOptionsItemSelected(item);
    }

}