package com.example.alphaversionchessroyale;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity4 extends AppCompatActivity {

    private TextView coordinatesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        coordinatesText = findViewById(R.id.coordinatesText);

        TableLayout chessboard = findViewById(R.id.chessboard);
        initializeChessboard(chessboard);
    }

    private void initializeChessboard(TableLayout chessboard) {
        for (int i = 7; i >= 0; i--) {
            TableRow row = new TableRow(this);
            for (int j = 0; j < 8; j++) {
                final int x = j;
                final int y = i;
                ChessboardCell cell = new ChessboardCell(this, j, i);
                cell.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCellClick(x, y);
                    }
                });
                row.addView(cell);
            }
            chessboard.addView(row);
        }
    }

    private void onCellClick(int x, int y) {
        String coordinates = "(" + (char) ('A' + x) + ", " + (y + 1) + ")";
        coordinatesText.setText("Selected Coordinates: " + coordinates);
    }

    private class ChessboardCell extends androidx.appcompat.widget.AppCompatTextView {

        public ChessboardCell(MainActivity4 context, int x, int y) {
            super(context);
            setLayoutParams(new TableRow.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    12.0f));
            setTextSize(30);
            int colorRes = (x + y) % 2 == 0 ? R.color.white : R.color.black;
            setBackgroundColor(getResources().getColor(colorRes)); // Set the color of the cell
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
            startActivity(new Intent(MainActivity4.this, MainActivity.class));
        } else if (st.equals("Activity 2")) {
            startActivity(new Intent(MainActivity4.this, MainActivity2.class));


        } else if (st.equals("Activity 3")) {
            startActivity(new Intent(MainActivity4.this, MainActivity3.class));


        }
        return super.onOptionsItemSelected(item);
    }
}