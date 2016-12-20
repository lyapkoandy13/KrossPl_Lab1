package com.example.lyapk.krosspl_lab1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init(){
        final SqliteController sqliteController = SqliteController.getInstance(getApplicationContext());

        final TextView mainTextView = (TextView) findViewById(R.id.mainTextView);
        Button addButton = (Button) findViewById(R.id.mainAddButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqliteController.insertSometext(String.valueOf(mainTextView.getText()));
                Toast.makeText(MainActivity.this.getApplicationContext(),"Added to Database", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
