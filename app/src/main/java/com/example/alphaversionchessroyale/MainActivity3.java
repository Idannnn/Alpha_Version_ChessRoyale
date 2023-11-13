package com.example.alphaversionchessroyale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity3 extends AppCompatActivity {

    TextView displayText;
    EditText editText;

    Button saveButton, loadButton;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        editText = findViewById(R.id.editText);
        saveButton = findViewById(R.id.saveButton);
        loadButton = findViewById(R.id.loadButton);
        displayText = findViewById(R.id.displayText);
        databaseReference = FirebaseDatabase.getInstance().getReference("textData");

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = editText.getText().toString();
                if (!text.isEmpty()) {
                    databaseReference.setValue(text);
                    editText.setText("");
                    displayText.setText("");
                }
            }
        });

        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String loadedText = dataSnapshot.getValue(String.class);
                        if (loadedText != null) {
                            displayText.setText(loadedText);
                        } else {
                            displayText.setText("No data found");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        displayText.setText("Error loading data");
                    }
                });
            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        String st = item.getTitle().toString();
        if (st.equals("Activity 1")) {
            startActivity(new Intent(MainActivity3.this, MainActivity.class));
        } else if (st.equals("Activity 2")) {
            startActivity(new Intent(MainActivity3.this, MainActivity2.class));


        } else if (st.equals("Activity 4")) {
            startActivity(new Intent(MainActivity3.this, MainActivity4.class));


        }
        return super.onOptionsItemSelected(item);
    }
}