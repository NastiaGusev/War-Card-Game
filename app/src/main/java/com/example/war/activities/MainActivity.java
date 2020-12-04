package com.example.war.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.war.R;

public class MainActivity extends Activity_Base {

    private Button main_BTN_start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        main_BTN_start = findViewById(R.id.main_BTN_start);

        main_BTN_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNewGame(MainActivity.this);
                Toast.makeText(getApplicationContext(), "Start Battle!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openNewGame(Activity activity) {
        Intent myIntent = new Intent(activity, Activity_Game.class);
        startActivity(myIntent);
    }

}


