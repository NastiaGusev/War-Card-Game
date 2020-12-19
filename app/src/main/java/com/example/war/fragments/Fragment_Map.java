package com.example.war.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.war.R;
import com.example.war.objects.Record;
import com.example.war.objects.TopTen;
import com.example.war.utils.MySP;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Fragment_Map extends Fragment {

    private TopTen topScores;
    private ArrayList<Marker> markers;
    private GoogleMap gMap;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getRecords();
        markers = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        initView();
        return view;
    }

    private void initView(){
        //Initialize map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_googleMap);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                gMap = googleMap;
                getMarkers(googleMap);
            }
        });
    }

    private void getRecords(){
        String stringTopScores = MySP.getInstance().getString(MySP.KEYS.KEY_TOP_TEN_SCORES, null);
        if (stringTopScores == null) {
            topScores = new TopTen();
        } else {
            topScores = new Gson().fromJson(stringTopScores, TopTen.class);
        }
    }

    private void getMarkers(GoogleMap googleMap){
        if (topScores.getSize() < 1){
            return;
        }
        Marker marker;
        for (Record record : topScores.getRecords()) {
            if (record.getLat() != 0 || record.getLon() !=0 ){
                marker = googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(record.getLat(), record.getLon()))
                        .title(record.getName()));
                markers.add(marker);
            }
        }
    }

    public void showMarker(int id){
        Marker marker = markers.get(id);
        marker.showInfoWindow();
        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15f));
    }

}
