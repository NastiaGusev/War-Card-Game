package com.example.war;

import android.app.Application;

import com.example.war.utils.MySP;
import com.example.war.utils.MySignal;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        MySP.init(this);
        MySignal.init(this);
    }
}
