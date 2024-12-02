package com.example.bikesharingg14;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;

import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapCapabilities;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    //Map Fragment
    private GoogleMap map;

    //Required for User location finding
    FusedLocationProviderClient fusedLocationProviderClient;

    //Permission Handling Variables
    private boolean locationPermissionGranted;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;


    // A default location (Kelowna,Canada) and default zoom to use when location permission is not provided
    private final LatLng defaultLocation = new LatLng( 49.882114, -119.477829);
    private static final int DEFAULT_ZOOM = 15;

    //Global Last Known Location Tracker
    private Location lastKnownLocation;

    //BikeList and db handling
    ArrayList<BikeModel> bikes = new ArrayList<>();
    private static final Type BIKE_TYPE = new TypeToken<List<BikeModel>>() {}.getType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Get Map Fragment from Main Activity
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize Bottom Sheet
        ConstraintLayout constraintLayout = findViewById(R.id.bottom_sheet);
        BottomSheetBehavior<View> sheetBehavior = BottomSheetBehavior.from(constraintLayout);

        //Set Bottom Sheet peek height
        sheetBehavior.setPeekHeight(200);
        //Set Bottom Sheet to default collapse
        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        //Test Write Bikes to File
        //testSaveBikes();

        //Test Read Bikes
        //testReadBikes();

        //load bikes array with bike data
        loadBikes();

        RecyclerView recyclerView = findViewById(R.id.bikerecycler);
        Bike_RecyclerViewAdapter adapter = new Bike_RecyclerViewAdapter(this,bikes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
    private void testMapCapabilities(){
        MapCapabilities mapCapabilities = map.getMapCapabilities();
        Log.d("MapIconCheck","is advanced marker enabled?" + mapCapabilities.isAdvancedMarkersAvailable());
    }

    private void testReadBikes() {
        String filename = "bikeFile.json";
        String data="";
        String line;

        Gson gson = new Gson();

        try {
            FileInputStream fis = openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            // It creates a way to convert the raw data from the file into human-readable text.
            BufferedReader br = new BufferedReader(isr);
            while((line=br.readLine())!= null){
                data += line;
            }
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        Log.d("ReadData",data);
        bikes = gson.fromJson(data,BIKE_TYPE);
        Log.d("ArrayData",bikes.toString());
    }

    private void loadBikes(){
        bikes.add(new BikeModel(new LatLng(49.88400789743151, -119.4910478606105),32,true,12));
        bikes.add(new BikeModel(new LatLng(49.88429273882145, -119.49109748073606),22,true,8));
        bikes.add(new BikeModel(new LatLng(49.88287723492295, -119.49027649320611),16,true,6));
        bikes.add(new BikeModel(new LatLng(49.88400789743151, -119.4910478606105),2,true,3));
        bikes.add(new BikeModel(new LatLng(49.88429273882145, -119.49109748073606),12,true,7));
        bikes.add(new BikeModel(new LatLng(49.88287723492295, -119.49027649320611),16,true,9));
    }
    private void testSaveBikes() {
        loadBikes();

        Gson gson = new Gson();

        String filename = "bikeFile.json";
        String fileContents = gson.toJson(bikes);
        FileOutputStream outputStream; //allow a file to be opened for writing
        try {
            outputStream = openFileOutput(filename, Context.MODE_APPEND);
            //This opens or creates a file with the given filename
            // MODE_APPEND-> add data to the end of the file (file already exists)
            // If the file doesn't exist, a new one will be created.
            outputStream.write(fileContents.getBytes());
            // Converts the file Contents from text (like words and sentences) into bytes.
            outputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getLocationPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            locationPermissionGranted=true;
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        //Test if Map can use CustomIcons
        testMapCapabilities();

        // Prompt the user for permission.
        getLocationPermission();

        UiSettings settings = map.getUiSettings();
        //settings.setZoomControlsEnabled(true);
        settings.setCompassEnabled(true);

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        locationPermissionGranted = false;
        if (requestCode
                == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {// If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationPermissionGranted = true;
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        new LatLng(lastKnownLocation.getLatitude(),
                                                lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                            }
                        } else {
                            Log.d("Device Locating Error", "Current location is null. Using defaults.");
                            Log.e("Device Locating Error", "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }
}