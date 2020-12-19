package com.example.war.activities;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.war.R;
import com.example.war.fragments.Fragment_List;
import com.example.war.fragments.Fragment_Map;
import com.example.war.utils.CallBack_List;

import java.util.Timer;
import java.util.TimerTask;

public class Activity_TopTen extends Activity_Base {

    private Timer carousalTimer;
    private FrameLayout topTen_LAY_list;
    private FrameLayout topTen_LAY_map;
    private Fragment_List fragment_list;
    private Fragment_Map fragment_map;

    private CallBack_List callBack_list = new CallBack_List() {
        @Override
        public void sendIdRecord(int id) {
            fragment_map.showMarker(id);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__topten);
        findViews();
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        startTimer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlaying();
        carousalTimer.cancel();
        finish();
    }

    private void findViews() {
       topTen_LAY_list = findViewById(R.id.topTen_LAY_list);
       topTen_LAY_map = findViewById(R.id.topTen_LAY_map);
    }

    private void initView(){
        fragment_list = new Fragment_List();
        fragment_list.setCallBack_List(callBack_list);
        fragment_map = new Fragment_Map();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.topTen_LAY_list,fragment_list)
                .commit();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.topTen_LAY_map,fragment_map)
                .commit();
    }

    private void startTimer() {
        carousalTimer = new Timer(); // At this line a new Thread will be created
        carousalTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playSound(Activity_TopTen.this,R.raw.snd_game_mystery );

                    }
                });
            }
        }, 0, 128000); // delay
    }

}
