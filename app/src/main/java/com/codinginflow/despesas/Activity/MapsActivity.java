package com.codinginflow.despesas.Activity;

import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.codinginflow.despesas.R;
import com.codinginflow.despesas.mapsdirectionhelpers.FetchURL;
import com.codinginflow.despesas.mapsdirectionhelpers.TaskLoadedCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, TaskLoadedCallback {
    public static final String EXTRA_MAPA_ENDERECO = "com.codinginflow.despesas.EXTRA_ENDERECO";

    GoogleMap  map;
    Button btnGetDirection;
    MarkerOptions place1, place2;
    Polyline currentPolyline;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mymaps_activity);

        btnGetDirection = findViewById(R.id.btnGetDirection);
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.mapFrag);
        mapFragment.getMapAsync(this);

        //27.658143,85.3199503public static final int OPEN_MAP = 1;
        //27.667491,85.3208583


        LatLng myHome = getLocationFromAddress(MapsActivity.this, "Rua rodrigues junior 1012, centro quixadá");
        place1 = new MarkerOptions().position(new LatLng(myHome.latitude, myHome.longitude)).title("My Home");

        Intent intent = getIntent();
        if(intent.hasExtra(EXTRA_MAPA_ENDERECO)){
            LatLng otherPlace = getLocationFromAddress(MapsActivity.this, EXTRA_MAPA_ENDERECO);
            place2 = new MarkerOptions().position(new LatLng(otherPlace.latitude, otherPlace.longitude)).title(EXTRA_MAPA_ENDERECO);
        } else {
            place2 = new MarkerOptions().position(new LatLng(-4.968538, -39.010039)).title("São Geraldo");
        }

        btnGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = getUrl(place1.getPosition(), place2.getPosition(), "driving");
                new FetchURL(MapsActivity.this).execute(url,"driving");
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.addMarker(place1);
        map.addMarker(place2);
    }

    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }

    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = map.addPolyline((PolylineOptions) values[0]);
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {

        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 1);
            if (address == null) {
                return null;
            }

            Address location = address.get(0);
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }
}
