package com.example.war.utils;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MyLocation {
    public static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 44;
    private FusedLocationProviderClient fusedLocation;
    private LocationRequest locationRequest;
    private AppCompatActivity activity;
    private Location location;
    private double lat;
    private double lon;

    public MyLocation() { }

    public MyLocation(AppCompatActivity activity) {
        this.activity = activity;
        getLocation();
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            if (locationResult == null) {
                return;
            }
            location = locationResult.getLocations().get(0);
            lat = location.getLatitude();
            lon = location.getLongitude();
            //if we get location - we can stop getting updates
            if (lon !=0 && lat != 0 ){
                stopLocationUpdates();
            }
        }
    };

    public void getLocation(){
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

        private void getCurrentLocation() {
        fusedLocation = LocationServices.getFusedLocationProviderClient(activity);
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocation.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override

                public void onComplete(@NonNull Task<Location> task) {
                    location = task.getResult();
                    if (location != null) {
                        lat = location.getLatitude();
                        lon = location.getLongitude();
                    } else {
                        //if last location was not used - start getting current location
                        locationRequest = LocationRequest.create();
                        locationRequest.setInterval(1000);
                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
                        startLocationUpdate();
                    }
                }
            });
        }
    }

    private void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocation.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
    }

    public void stopLocationUpdates() {
        fusedLocation.removeLocationUpdates(locationCallback);
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

}