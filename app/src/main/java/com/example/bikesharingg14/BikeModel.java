package com.example.bikesharingg14;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

public class BikeModel {
    private LatLng position;
    private int range;
    private boolean isFunctional = true;
    private int distance = 0;
    private int imageResource;
    private Marker marker;

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    public int getMapImgResource() {
        return mapImgResource;
    }

    public void setMapImgResource(int mapImgResource) {
        this.mapImgResource = mapImgResource;
    }

    private int mapImgResource;

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }


    public static final int BIKE_MAX_RANGE = 32;

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public BikeModel() {

    }

    public BikeModel(LatLng position, int range, boolean isFunctional, int distance) {
        this.position = position;
        this.range = range;
        this.isFunctional = isFunctional;
        this.distance = distance;
    }

    public BikeModel(LatLng position, int range, int distance) {
        this.position = position;
        this.range = range;
        this.distance = distance;
    }

    public BikeModel(LatLng position, int range) {
        this.position = position;
        this.range = range;
    }

    public BikeModel(LatLng position, int range, boolean isFunctional) {
        this.position = position;
        this.range = range;
        this.isFunctional = isFunctional;
    }

    public LatLng getPosition() {
        return position;
    }

    public int getRange() {
        return range;
    }

    public boolean isFunctional() {
        return isFunctional;
    }

    @Override
    public String toString() {
        return "BikeModel{" +
                "position=" + position +
                ", range=" + range +
                ", isFunctional=" + isFunctional +
                ", distance=" + distance +
                ", imageResource=" + imageResource +
                ", marker=" + marker +
                ", mapImgResource=" + mapImgResource +
                '}';
    }
}
