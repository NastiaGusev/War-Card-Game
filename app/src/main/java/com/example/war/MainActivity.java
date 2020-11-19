package com.example.war;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button main_BTN_start;
    private TextView main_LBL_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        main_BTN_start = findViewById(R.id.main_BTN_start);

        main_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewGame(MainActivity.this);
            }
        });

    }

    private void openNewGame(Activity activity) {
        Intent myIntent = new Intent(activity, Activity_Game.class);
        startActivity(myIntent);
        //finish();
    }
}